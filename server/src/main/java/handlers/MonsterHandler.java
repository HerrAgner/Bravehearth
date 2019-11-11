package handlers;


import game.GameServer;
import network.networkMessages.Monster;
import network.networkMessages.Position;
import network.networkMessages.avatar.Avatar;

import java.util.HashMap;
import java.util.UUID;
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
                    } else if (calculateShortestPath(monster.getId(), avatar.getId())
                            < calculateShortestPath(monster.getId(), monster.getMarkedUnit())) {
                        monster.setMarkedUnit(id);
                        ref.changed = true;
                    }
                }

            });
            if (!ref.changed) {
                if (monster.getMarkedUnit() != -1 && calculateShortestPath(monster.getId(), monster.getMarkedUnit()) > 8) {
                    monster.setMarkedUnit(-1);
                }
            }
            if (monster.getMarkedUnit() != -1) {
                moveMonster(monster);
            }
        });
    }

    private float calculateShortestPath(int monster, int avatar) {
        Monster mon = GameServer.getInstance().getMh().monsterList.get(monster);
        Avatar av = GameServer.getInstance().aa.get(avatar);

        float dx = mon.getX() - av.getX();
        float dy = mon.getY() - av.getY();
        return (float) Math.hypot(dx, dy);
    }

    private float[] calculateNewPosition(int monster) {
        Monster mon = GameServer.getInstance().getMh().monsterList.get(monster);
        Avatar av = GameServer.getInstance().aa.get(mon.getMarkedUnit());

        float dx = mon.getX() - av.getX();
        float dy = mon.getY() - av.getY();
        float shortestPath = (float) (mon.getMaxXspeed() / Math.hypot(dx, dy));
        float newX = mon.getX() - dx * shortestPath;
        float newY = mon.getY() - dy * shortestPath;

        return new float[]{newX, newY};
    }

    public void moveMonster(Monster monster) {

        float newX = calculateNewPosition(monster.getId())[0];
        float newY = calculateNewPosition(monster.getId())[1];

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
