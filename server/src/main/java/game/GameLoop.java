package game;

import database.DBQueries;
import enums.Movement;
import handlers.AttackHandler;
import handlers.MovementHandler;
import network.networkMessages.*;
import network.networkMessages.avatar.Avatar;


public class GameLoop implements Runnable {
    private boolean running;
    private AttackHandler ah;

    public GameLoop() {
        this.running = true;
        this.ah = new AttackHandler();
    }

    @Override
    public void run() {
        long prevtime = System.currentTimeMillis();

        while (running) {
            long time = System.currentTimeMillis();
            float delta = (float) ((time - prevtime) / 1000.0);
            float oneSecond = 0;

            MovementHandler.movementLoopList.forEach((key, value) ->
                    value.forEach(movement -> {
                        if (GameServer.getInstance().aa.get(GameServer.getInstance().au.get(key.getID()).getAvatar().getId()) != null) {
                            Position pos = updatePosition(GameServer.getInstance().aa.get(GameServer.getInstance().au.get(key.getID()).getAvatar().getId()), movement, delta);
                            if (pos != null) {
                                GameServer.getInstance().aa.get(GameServer.getInstance().au.get(key.getID()).getAvatar().getId()).setX(pos.getX());
                                GameServer.getInstance().aa.get(GameServer.getInstance().au.get(key.getID()).getAvatar().getId()).setY(pos.getY());
                                GameServer.getInstance().aa.get(GameServer.getInstance().au.get(key.getID()).getAvatar().getId()).setDirection(pos.getDirection());
                                GameServer.getInstance().getServer().sendToAllUDP(pos);
                            }
                        }
                    }));

            if (AttackHandler.validatedAttacks.size() > 0) {

                try {
                    AttackEnemyTarget aet = AttackHandler.validatedAttacks.take();
                    GameServer.getInstance().getServer().sendToAllTCP(aet);
                    if (aet.getTargetUnit().equals("monster") && GameServer.getInstance().getMh().monsterList.get(aet.getTarget()).getHp() <= 0) {
                        Monster mon = GameServer.getInstance().getMh().monsterList.get(aet.getTarget());
                        GameServer.getInstance().aa.get(aet.getAttacker()).setMarkedUnit(-1);
                        GameServer.getInstance().getMh().getActiveMonsterSpawners().get(mon.getSpawnerId()).decreaseActiveMonstersByOne();
                        GameServer.getInstance().getServer().sendToAllTCP(new UnitDeath(aet.getAttacker(), aet.getTarget(), "monster", GameServer.getInstance().getMh().monsterList.get(aet.getTarget()).getExperiencePoints()));
                        GameServer.getInstance().getMh().monsterList.remove(mon.getId());
                        GameServer.getInstance().aa.get(aet.getAttacker()).addExperiencePoints(mon.getExperiencePoints());
                        GameServer.getInstance().aa.get(aet.getAttacker()).getBackpack().addGold(mon.getGold());
                    }
                    if (GameServer.getInstance().aa.get(aet.getTarget()) != null) {
                        if (aet.getTargetUnit().equals("avatar") && GameServer.getInstance().aa.get(aet.getTarget()).getHealth() <= 0) {
                            Avatar av = GameServer.getInstance().aa.get(aet.getTarget());
                            av.setMarkedUnit(-1);
                            GameServer.getInstance().getServer().sendToAllTCP(new Position(50f, 50f, aet.getTarget(), 1, "front"));
                            av.setX(50);
                            av.setY(50);
                            av.setHealth(av.getMaxHealth());
                            av.setExperiencePoints(0);
                            av.getBackpack().setWallet(av.getBackpack().getWallet() /2);
                            av.getBackpack().getItems().clear();
                            GameServer.getInstance().getServer().sendToAllTCP(new UnitDeath(aet.getAttacker(), aet.getTarget(), "avatar", 0));
                            DBQueries.saveAvatarWhenDead(GameServer.getInstance().aa.get(aet.getTarget()));

                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Could not send attack. Trying again.");
                }
            }
            GameServer.getInstance().getMh().updateCounter();
            GameServer.getInstance().getMh().monsterTargetAvatar();
            GameServer.getInstance().getMh().monsterList.values().forEach(monster -> {
                if (monster.getMarkedUnit() != -1) {
                    monster.setAttackTimer(monster.getAttackTimer() + delta);
                    if (monster.getAttackTimer() > monster.getAttackSpeed()) {
                        GameServer.getInstance().getMh().monsterAttack(monster);
                        monster.setAttackTimer(delta);
                    }
                }
            });

            GameServer.getInstance().getMh().getActiveMonsterSpawners().forEach(monsterSpawner -> {
                monsterSpawner.setTimeCounter(monsterSpawner.getTimeCounter() + delta);
                if (monsterSpawner.getTimeCounter() > monsterSpawner.getSpawnTimer()) {
                    monsterSpawner.spawnMonster();
                    monsterSpawner.setTimeCounter(delta);
                }
            });

            GameServer.getInstance().getAUH().getActiveAvatars().forEach((k, v) -> {
                if (v.getMarkedUnit() != -1) {
                    v.setAttackTimer(v.getAttackTimer() + delta);
                    if (v.attackIsReady()) {
                        ah.addAttackerToList(v.getId(), v.getMarkedUnit(), 1);
                        v.setAttackTimer(delta);
                    }
                }
                if (v.getHealth() < v.getMaxHealth() && v.getHealth() > 0) {
                    if (v.startHpRegen()) {
                        GameServer.getInstance().getServer().sendToAllTCP(new HealthChange(v.getHealth() + 1, v.getId(), v.getId(), 3));
                        v.setHealth(v.getHealth() + 1);
                    }
                }
                if (v.getExperiencePoints() >= 25 * v.getLevel() * (1 + v.getLevel())) {
                    v.setLevel(v.getLevel() + 1);
                    v.setExperiencePoints(0);
                }
            });


            try {
                prevtime = time;
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public Position updatePosition(Avatar avatar, Movement movement, float delta) {
        Position position;
        boolean moved = false;
        switch (movement) {
            case FORWARD:
                position = new Position(avatar.getX(), avatar.getY() + avatar.getMaxYspeed() * (delta * 30), avatar.getId(), 1, "back");
                break;
            case BACKWARD:
                position = new Position(avatar.getX(), avatar.getY() - avatar.getMaxYspeed() * (delta * 30), avatar.getId(), 1, "front");
                break;
            case LEFT:
                position = new Position(avatar.getX() - avatar.getMaxXspeed() * (delta * 30), avatar.getY(), avatar.getId(), 1, "left_side");
                break;
            case RIGHT:
                position = new Position(avatar.getX() + avatar.getMaxXspeed() * (delta * 30), avatar.getY(), avatar.getId(), 1, "right_side");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + movement);
        }

        if (GameServer.getInstance().getMapReader().getMapCollision()
                .get((int) Math.ceil(position.getX()))
                .contains((int) Math.ceil(position.getY()))
                ||
                GameServer.getInstance().getMapReader().getMapCollision()
                        .get((int) Math.ceil(position.getX() - 1))
                        .contains((int) Math.ceil(position.getY()))
                ||
                GameServer.getInstance().getMapReader().getMapCollision()
                        .get((int) Math.ceil(position.getX() - 1))
                        .contains((int) Math.ceil(position.getY() - 1))
                ||
                GameServer.getInstance().getMapReader().getMapCollision()
                        .get((int) Math.ceil(position.getX()))
                        .contains((int) Math.ceil(position.getY() - 1))
        ) {
            return null;
        } else {
            var ref = new Object() {
                boolean isValidMove = true;
            };
            GameServer.getInstance().aa.forEach((key, value) -> {
                if (key != avatar.getId()) {
                    if (Math.max(value.getX(), position.getX()) - Math.min(value.getX(), position.getX()) < 1 &&
                            Math.max(value.getY(), position.getY()) - Math.min(value.getY(), position.getY()) < 1) {
                        ref.isValidMove = false;
                    }
                }
            });
//            GameServer.getInstance().getMh().monsterList.forEach((integer, monster) -> {
//
//            });
            if (ref.isValidMove) {
                return position;
            } else {
                return null;
            }

        }
    }
}
