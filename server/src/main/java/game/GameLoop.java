package game;

import com.esotericsoftware.kryonet.Server;
import enums.Movement;
import handlers.AttackHandler;
import handlers.MovementHandler;
import network.networkMessages.*;
import network.networkMessages.avatar.Avatar;


public class GameLoop implements Runnable {
    private boolean running;
    private AttackHandler ah;
    GameServer server;

    public GameLoop() {
        this.running = true;
        this.ah = new AttackHandler();
    }

    @Override
    public void run() {
        long prevtime = System.currentTimeMillis();
        server = GameServer.getInstance();

        while (running) {
            long time = System.currentTimeMillis();
            float delta = (float) ((time - prevtime) / 1000.0);
            float oneSecond = 0;

            MovementHandler.movementLoopList.forEach((key, value) ->
                    value.forEach(movement -> {
                                if (server.au.get(key.getID()) != null) {
                                    Position pos = updatePosition(server.aa.get(server.au.get(key.getID()).getAvatar().getId()), movement, delta);
                                    if (pos != null) {
                                        server.aa.get(server.au.get(key.getID()).getAvatar().getId()).setX(pos.getX());
                                        server.aa.get(server.au.get(key.getID()).getAvatar().getId()).setY(pos.getY());
                                        server.aa.get(server.au.get(key.getID()).getAvatar().getId()).setDirection(pos.getDirection());
                                        server.getServer().sendToAllUDP(pos);
                                    }
                                }
                            }
                    ));

            if (AttackHandler.validatedAttacks.size() > 0) {
                try {
                    AttackEnemyTarget aet = AttackHandler.validatedAttacks.take();
                    server.getServer().sendToAllTCP(aet);
                    if (aet.getTargetUnit().equals("monster") && server.getMh().monsterList.get(aet.getTarget()).getHp() <= 0) {
                        monsterDeath(aet);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Could not send attack. Trying again.");
                }
            }
            server.getMh().updateCounter();
            server.getMh().monsterTargetAvatar();
            server.getMh().monsterList.values().forEach(monster -> {
                if (monster.getMarkedUnit() != -1) {
                    monster.setAttackTimer(monster.getAttackTimer() + delta);
                    if (monster.getAttackTimer() > monster.getAttackSpeed()) {
                        server.getMh().monsterAttack(monster);
                        monster.setAttackTimer(delta);
                    }
                }
            });

            server.getAUH().getActiveAvatars().forEach((k, v) -> {
                if (v.getMarkedUnit() != -1) {
                    v.setAttackTimer(v.getAttackTimer() + delta);
                    if (v.attackIsReady()) {
                        ah.addAttackerToList(v.getId(), v.getMarkedUnit(), 1);
                        v.setAttackTimer(delta);
                    }
                }
                if (v.getHealth() < v.getMaxHealth() && v.getHealth() > 0) {
                    if (v.startHpRegen()) {
                        server.getServer().sendToAllTCP(new HealthChange(v.getHealth() + 1, v.getId(), v.getId(), 3));
                        v.setHealth(v.getHealth() + 1);
                    }
                }
                if (v.getExperiencePoints() >= 25 * v.getLevel() * (1 + v.getLevel())) {
                    levelUp(v.getId());
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

    private void levelUp(int avatarId) {
        Avatar av = server.aa.get(avatarId);
        av.setLevel(av.getLevel() + 1);
        av.setExperiencePoints(0);
        switch (av.getCharacterClass()) {
            case SORCERER:
                av.setIntelligence(av.getIntelligence() + 3);
                av.setStrength(av.getStrength() + 1);
                av.setDexterity(av.getDexterity() + 1);
                break;
            case WARRIOR:
                av.setIntelligence(av.getIntelligence() + 1);
                av.setStrength(av.getStrength() + 3);
                av.setDexterity(av.getDexterity() + 1);
                break;
            case MARKSMAN:
                av.setIntelligence(av.getIntelligence() + 1);
                av.setStrength(av.getStrength() + 1);
                av.setDexterity(av.getDexterity() + 3);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + av.getCharacterClass());
        }
        av.setMaxHealth(av.getMaxHealth() + av.getStrength());
        av.setMaxMana(av.getMaxMana() + av.getIntelligence());
        av.setHealth(av.getMaxHealth());
        av.setMana(av.getMaxMana());
    }

    private void monsterDeath(AttackEnemyTarget aet) {
        Monster mon = server.getMh().monsterList.get(aet.getTarget());
        server.aa.get(aet.getAttacker()).setMarkedUnit(-1);
        if (mon.getLoot().size() > 0) {
            mon.getLoot().forEach(item -> {
                server.getMh().itemsOnGround.put(new Float[]{mon.getX(), mon.getY()}, item);
                server.getServer().sendToAllTCP(new ItemDrop(mon.getX(), mon.getY(), item));
            });
        }
        server.getMh().getActiveMonsterSpawners().get(mon.getSpawnerId()).decreaseActiveMonstersByOne();
        server.getServer().sendToAllTCP(new UnitDeath(aet.getAttacker(), aet.getTarget(), "monster", server.getMh().monsterList.get(aet.getTarget()).getExperiencePoints()));
        server.getMh().monsterList.remove(mon.getId());
        server.aa.get(aet.getAttacker()).addExperiencePoints(mon.getExperiencePoints());
        server.aa.get(aet.getAttacker()).getBackpack().addGold(mon.getGold());
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

        if (server.getMapReader().getMapCollision()
                .get((int) Math.ceil(position.getX()))
                .contains((int) Math.ceil(position.getY()))
                ||
                server.getMapReader().getMapCollision()
                        .get((int) Math.ceil(position.getX() - 1))
                        .contains((int) Math.ceil(position.getY()))
                ||
                server.getMapReader().getMapCollision()
                        .get((int) Math.ceil(position.getX() - 1))
                        .contains((int) Math.ceil(position.getY() - 1))
                ||
                server.getMapReader().getMapCollision()
                        .get((int) Math.ceil(position.getX()))
                        .contains((int) Math.ceil(position.getY() - 1))
        ) {
            return null;
        } else {
            var ref = new Object() {
                boolean isValidMove = true;
            };
            server.aa.forEach((key, value) -> {
                if (key != avatar.getId()) {
                    if (Math.max(value.getX(), position.getX()) - Math.min(value.getX(), position.getX()) < 1 &&
                            Math.max(value.getY(), position.getY()) - Math.min(value.getY(), position.getY()) < 1) {
                        ref.isValidMove = false;
                    }
                }
            });
//            server.getMh().monsterList.forEach((integer, monster) -> {
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
