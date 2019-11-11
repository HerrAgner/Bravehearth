package handlers;


import game.GameServer;
import network.networkMessages.Monster;
import network.networkMessages.Position;
import network.networkMessages.avatar.Avatar;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MonsterHandler {
    public HashMap<Integer, Monster> monsterList;
    public AtomicInteger counter;
    private AttackHandler attackHandler;


    public MonsterHandler() {
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
        float[] dxdy = calcDxDy(mon.getX(), mon.getY(), av.getX(), av.getY());
        return (float) Math.hypot(dxdy[0], dxdy[1]);
    }

    private float[] calculateNewPosition(int monster, boolean isBlocked) {
        Monster mon = GameServer.getInstance().getMh().monsterList.get(monster);
        Avatar av = GameServer.getInstance().aa.get(mon.getMarkedUnit());
        Random r = new Random();
        float randomX = -mon.getMaxXspeed() + r.nextFloat() * (mon.getMaxXspeed() - -mon.getMaxXspeed());
        float randomY = -mon.getMaxYspeed() + r.nextFloat() * (mon.getMaxYspeed() - -mon.getMaxYspeed());
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

        if (validateNewPosition(mon.getId(), newX, newY)){
            return new float[]{newX, newY};
        } else {
           return calculateNewPosition(monster, true);
        }
    }

    private boolean validateNewPosition(int monsterId, float x, float y) {
        AtomicBoolean isBlocked = new AtomicBoolean(false);
        GameServer.getInstance().getMh().monsterList.forEach((integer, monster) -> {
            if (monster.getId() != monsterId) {
                float[] dxdy = calcDxDy(monster.getX(), monster.getY(), x, y);
                if (Math.hypot(dxdy[0], dxdy[1]) < 1) {
                    isBlocked.set(false);
                } else {
                    isBlocked.set(true);
                }
            }
        });
        return isBlocked.get();
    }

    public void moveMonster(Monster monster) {
        float newX = calculateNewPosition(monster.getId(), false)[0];
        float newY = calculateNewPosition(monster.getId(), false)[1];

        monsterList.get(monster.getId()).setX(newX);
        monsterList.get(monster.getId()).setY(newY);

        GameServer.getInstance().getServer().sendToAllUDP(new Position(newX, newY, monster.getId(), 2));
    }

    public void updateCounter() {
        if (counter.get() == 63) {
            counter.set(0);
        } else {
            counter.addAndGet(1);
        }
    }


}
