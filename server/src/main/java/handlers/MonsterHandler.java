package handlers;


import game.GameServer;
import network.networkMessages.Monster;
import network.networkMessages.Position;

import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MonsterHandler {
    public static HashMap<UUID, Monster> monsterList;
    public AtomicInteger counter;
    Vector df = new Vector();


    // Counter that increases each render of the gameloop. Reset counter on 30 to 0.
    // if (counter == 15) do movement?

    public MonsterHandler() {
        monsterList = new HashMap<>();
        counter = new AtomicInteger();
        counter.set(0);

    }

    public void addMonster(UUID id, Monster monster){
        monsterList.put(id, monster);
    }

    public void monsterTargetAvatar(){
        var ref = new Object() {
            float dx;
            float dy;
        };
        AtomicBoolean changed = new AtomicBoolean(false);
        monsterList.values().forEach(monster -> {
            GameServer.getInstance().aa.forEach((uuid, avatar) -> {
                if (Math.max(monster.getX(), avatar.getX()) -  Math.min(monster.getX(), avatar.getX()) < 5 &&
                        Math.max(monster.getY(), avatar.getY()) -  Math.min(monster.getY(), avatar.getY()) < 5) {
                    monster.setMarkedUnit(uuid);
                    changed.set(true);
                    ref.dx = monster.getX() - avatar.getX();
                    ref.dy = monster.getY() - avatar.getY();
//                    ref.angle = (float) Math.atan2(monster.getY() - avatar.getY(), monster.getY() -avatar.getX());
                }

            });
            if (!changed.get()) {
                monster.setMarkedUnit(null);
            }
            if (monster.getMarkedUnit() != null) {
                moveMonster(monster, ref.dx, ref.dy);
            }
        });
    }

    public void moveMonster(Monster monster, float dx, float dy) {
        float len = (float) Math.hypot(dx, dy);
        double s = monster.getMaxXspeed() / len;
        float newX = (float) (monster.getX()-dx*s);
        float newY = (float) (monster.getY()-dy*s);

        MonsterHandler.monsterList.get(monster.getId()).setX(newX);
        MonsterHandler.monsterList.get(monster.getId()).setY(newY);

        GameServer.getInstance().getServer().sendToAllUDP(new Position(newX, newY, monster.getId(), 2));
    }

    public void updateCounter(){
        if (counter.get() == 10000) {
            counter.set(0);
        } else {
            counter.addAndGet(1);
        }
    }



}
