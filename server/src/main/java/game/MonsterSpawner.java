package game;


import handlers.CollisionHandler;
import network.networkMessages.Monster;
import network.networkMessages.MonsterType;

import java.util.ArrayList;
import java.util.Random;

public class MonsterSpawner {
    private int monsterId;
    private Integer[] spawnPoint;
    private float spawnRadius;
    private float timeCounter;
    private float spawnTimer = 0;
    private int monsterLimit;
    private int activeMonsters;

    public MonsterSpawner(int monsterId, Integer[] importSpawnPoints) {
        this.spawnPoint = new Integer[]{importSpawnPoints[0], importSpawnPoints[1]};
        this.monsterId = monsterId;
        this.monsterLimit = 5;
        this.spawnRadius = 10;
        this.spawnTimer = 10f;
    }

    public Monster spawnMonster() {
        Random r = new Random();
        if (activeMonsters < monsterLimit) {
            float newX = (spawnPoint[0] - spawnRadius) + r.nextFloat() * ((spawnPoint[0] + spawnRadius) - (spawnPoint[0] - spawnRadius));
            float newY = (spawnPoint[1] - spawnRadius) + r.nextFloat() * ((spawnPoint[1] + spawnRadius) - (spawnPoint[1] - spawnRadius));
            if (newX > 1 && newX < 199 && newY > 1 && newY < 199) {
                if (CollisionHandler.isAnyCollision(newX, newY)){
                    return spawnMonster();
                }

                Monster monster = new Monster(5, 5, "ANTONMONSTRET");
                monster.setX((float) Math.ceil(newX));
                monster.setY((float) Math.ceil(newY));
                monster.setMaxXspeed(0.01f);
                monster.setMaxYspeed(0.01f);
                monster.setBoundsRadius(4f);
                monster.setMaxHp(5);
                monster.setType(MonsterType.DUMMYMONSTER);
                monster.setId(GameServer.getInstance().getMh().getNewMonsterId());
                monster.setMarkedUnit(-1);
                GameServer.getInstance().getMh().monsterList.put(monster.getId(), monster);
                this.activeMonsters += 1;
                System.out.println(GameServer.getInstance().getMh().monsterList.size());
                GameServer.getInstance().getServer().sendToAllTCP(monster);


            } else {
                return spawnMonster();

            }
        }
        return null;
    }

    public float getTimeCounter() {
        return timeCounter;
    }

    public void setTimeCounter(float timeCounter) {
        this.timeCounter = timeCounter;
    }

    public float getSpawnTimer() {
        return spawnTimer;
    }

    public void setSpawnTimer(float spawnTimer) {
        this.spawnTimer = spawnTimer;
    }
}
