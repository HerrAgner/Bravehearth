package handlers;


import game.GameServer;
import game.MonsterSpawner;
import network.networkMessages.Monster;
import network.networkMessages.Position;
import network.networkMessages.avatar.Avatar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MonsterHandler {
    public HashMap<Integer, Monster> monsterList;
    public AtomicInteger counter;
    private AttackHandler attackHandler;
    private ArrayList<MonsterSpawner> activeMonsterSpawners;


    public MonsterHandler() {
        activeMonsterSpawners = new ArrayList<>();
        monsterList = new HashMap<>();
        counter = new AtomicInteger();
        counter.set(0);
        attackHandler = new AttackHandler();
    }

    public void monsterAttack(Monster monster) {
        if (counter.get() == Math.floor(monster.getAttackSpeed() * 16)) {
            attackHandler.addAttackerToList(monster.getId(), monster.getMarkedUnit(), 2);
        }
    }

    public void addMonster(int id, Monster monster) {
        monsterList.put(id, monster);
    }

    public void monsterTargetAvatar() {
        var ref = new Object() {
            float dx;
            float dy;
            float len;
            boolean changed;
        };

        monsterList.values().forEach(monster -> {
            GameServer.getInstance().aa.forEach((id, avatar) -> {
                ref.dx = monster.getX() - avatar.getX();
                ref.dy = monster.getY() - avatar.getY();
                ref.len = (float) Math.hypot(ref.dx, ref.dy);
                if (ref.len < 5) {
                    if (monster.getMarkedUnit() == -1) {
                        monster.setMarkedUnit(id);
                        ref.changed = true;
                    } else if (calculateShortestPath(monster, avatar)
                            < calculateShortestPath(monster, GameServer.getInstance().aa.get(monster.getMarkedUnit()))) {
                        monster.setMarkedUnit(id);
                        ref.changed = true;
                    }
                }

            });
            if (!ref.changed) {
                if (monster.getMarkedUnit() != -1 && calculateShortestPath(monster, GameServer.getInstance().aa.get(monster.getMarkedUnit())) > 8) {
                    monster.setMarkedUnit(-1);
                }
            }
            if (monster.getMarkedUnit() != -1) {
                moveMonster(monster);
            }
        });
    }

    private float[] calcDxDy(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return new float[]{dx, dy};
    }

    private float calculateShortestPath(Monster mon, Avatar av) {
        if (mon != null && av != null) {
            float[] dxdy = calcDxDy(mon.getX(), mon.getY(), av.getX(), av.getY());
            return (float) Math.hypot(dxdy[0], dxdy[1]);
        }
        return 0;
    }

    private float[] calculateNewPosition(int monster, boolean isBlocked, float pathSize) {
        Monster mon = GameServer.getInstance().getMh().monsterList.get(monster);
        Avatar av = GameServer.getInstance().aa.get(mon.getMarkedUnit());
        if (av == null){
            return null;
        }

        Random r = new Random();
        float randomX = -(mon.getMaxXspeed()+pathSize) + r.nextFloat() * ((mon.getMaxXspeed()+pathSize) - -(mon.getMaxXspeed()+pathSize));
        float randomY = -(mon.getMaxYspeed()+pathSize) + r.nextFloat() * ((mon.getMaxYspeed()+pathSize) - -(mon.getMaxYspeed()+pathSize));
        float[] dxdy;
        float newX;
        float newY;
        float shortestPath;

        if (isBlocked) {
            dxdy = calcDxDy(mon.getX(), mon.getY(), av.getX(), av.getY());
            shortestPath = (float) (mon.getMaxXspeed() / Math.hypot(dxdy[0], dxdy[1]));
            newX = (mon.getX() - dxdy[0] * shortestPath) + randomX;
            newY = (mon.getY() - dxdy[1] * shortestPath) + randomY;
        } else {
            dxdy = calcDxDy(mon.getX(), mon.getY(), av.getX(), av.getY());
            shortestPath = (float) (mon.getMaxXspeed() / Math.hypot(dxdy[0], dxdy[1]));
            newX = mon.getX() - dxdy[0] * shortestPath;
            newY = mon.getY() - dxdy[1] * shortestPath;
        }


//        float shortestPath = (float) (mon.getMaxXspeed() / Math.hypot(dxdy[0], dxdy[1]));
//        float newX = mon.getX() - dxdy[0] * shortestPath;
//        float newY = mon.getY() - dxdy[1] * shortestPath;

        if (calculateShortestPath(mon, av) <= 1) {
            return null;
        }

        if (validateNewPosition(mon.getId(), newX, newY)) {
            return new float[]{newX, newY};
        } else {
            return calculateNewPosition(monster, true, pathSize += mon.getMaxXspeed());
        }
    }

    private boolean validateNewPosition(int monsterId, float x, float y) {
        AtomicInteger isBlocked = new AtomicInteger(0);
        if (GameServer.getInstance().getMh().monsterList.size() <= 1){
            return true;
        }
        GameServer.getInstance().getMh().monsterList.forEach((integer, monster) -> {
            if (monster.getId() != monsterId) {
                float[] dxdy = calcDxDy(monster.getX(), monster.getY(), x, y);
                if (Math.hypot(dxdy[0], dxdy[1]) < 1) {
                    isBlocked.set(isBlocked.get()+1);
                }
            } else {
                if (GameServer.getInstance().getMapReader().getMapCollision()
                        .get((int) Math.ceil(x))
                        .contains((int) Math.ceil(y))
                        ||
                        GameServer.getInstance().getMapReader().getMapCollision()
                                .get((int) Math.ceil(x - 1))
                                .contains((int) Math.ceil(y))
                        ||
                        GameServer.getInstance().getMapReader().getMapCollision()
                                .get((int) Math.ceil(x - 1))
                                .contains((int) Math.ceil(y - 1))
                        ||
                        GameServer.getInstance().getMapReader().getMapCollision()
                                .get((int) Math.ceil(x))
                                .contains((int) Math.ceil(y - 1))) {
                    isBlocked.set(isBlocked.get() +1);
                }
            }
        });
        return isBlocked.get() == 0;
    }

    public void moveMonster(Monster monster) {
        float newX = 0;
        float newY = 0;
        if (calculateNewPosition(monster.getId(), false,0) != null) {
            newX = calculateNewPosition(monster.getId(), false,0)[0];
            newY = calculateNewPosition(monster.getId(), false,0)[1];
        } else{
            return;
        }

        monsterList.get(monster.getId()).setX(newX);
        monsterList.get(monster.getId()).setY(newY);

        GameServer.getInstance().getServer().sendToAllUDP(new Position(newX, newY, monster.getId(), 2, "front"));
    }

    public void updateCounter() {
        if (counter.get() == 63) {
            counter.set(0);
        } else {
            counter.addAndGet(1);
        }
    }

    public ArrayList<MonsterSpawner> getActiveMonsterSpawners() {
        return activeMonsterSpawners;
    }

    public void addMonsterSpawner(MonsterSpawner ms){
        this.activeMonsterSpawners.add(ms);
    }

    public void setActiveMonsterSpawners(ArrayList<MonsterSpawner> activeMonsterSpawners) {
        this.activeMonsterSpawners = activeMonsterSpawners;
    }
}
