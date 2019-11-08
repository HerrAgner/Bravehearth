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
    public static HashMap<Integer, Monster> monsterList;
    public AtomicInteger counter;


    public MonsterHandler() {
        monsterList = new HashMap<>();
        counter = new AtomicInteger();
        counter.set(0);

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
                    monster.setMarkedUnit(id);
                    ref.changed =true;
                }

            });
            if (!ref.changed) {
                if (ref.len > 8) {
                    monster.setMarkedUnit(0);
                }
            }
            if (monster.getMarkedUnit() != 0) {
                moveMonster(monster, ref.dx, ref.dy, ref.len);
            }
        });
    }

    public void moveMonster(Monster monster, float dx, float dy, float len) {
        double s = monster.getMaxXspeed() / len;
        float newX = (float) (monster.getX() - dx * s);
        float newY = (float) (monster.getY() - dy * s);

        MonsterHandler.monsterList.get(monster.getId()).setX(newX);
        MonsterHandler.monsterList.get(monster.getId()).setY(newY);

        GameServer.getInstance().getServer().sendToAllUDP(new Position(newX, newY, monster.getId(), 2));
    }

    public void updateCounter() {
        if (counter.get() == 10000) {
            counter.set(0);
        } else {
            counter.addAndGet(1);
        }
    }


}
