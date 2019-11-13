package game;


import database.DBQueries;
import handlers.CollisionHandler;
import network.networkMessages.Monster;

import java.util.Random;

public class MonsterSpawner {
    private int spawnerId;
    private int monsterId;
    private Integer[] spawnPoint;
    private float spawnRadius;
    private float timeCounter;
    private float spawnTimer;
    private int monsterLimit;
    private int activeMonsters;

    public MonsterSpawner(int monsterId, Integer[] importSpawnPoints, int spawnerId) {
        this.spawnPoint = new Integer[]{importSpawnPoints[0], importSpawnPoints[1]};
        this.monsterId = monsterId;
        this.monsterLimit = 5;
        this.spawnRadius = 10;
        this.spawnTimer = 5f;
        this.spawnerId = spawnerId;
    }

    public Monster spawnMonster() {
        Random r = new Random();
        if (this.activeMonsters < this.monsterLimit) {
            float newX = (spawnPoint[0] - spawnRadius) + r.nextFloat() * ((spawnPoint[0] + spawnRadius) - (spawnPoint[0] - spawnRadius));
            float newY = (spawnPoint[1] - spawnRadius) + r.nextFloat() * ((spawnPoint[1] + spawnRadius) - (spawnPoint[1] - spawnRadius));
            if (newX > 1 && newX < 199 && newY > 1 && newY < 199) {
                if (CollisionHandler.isAnyCollision(newX, newY)){
                    return spawnMonster();
                }

                Monster monster = DBQueries.getMonsterById(monsterId);
                monster.setMaxHp(monster.getHp());

                monster.setX((float) Math.ceil(newX));
                monster.setY((float) Math.ceil(newY));
                monster.setMaxXspeed(monster.getMaxSpeed());
                monster.setMaxYspeed(monster.getMaxSpeed());
                monster.setId(GameServer.getInstance().getMh().getNewMonsterId());
                monster.setMarkedUnit(-1);
                monster.setSpawnerId(this.spawnerId);
                GameServer.getInstance().getMh().monsterList.put(monster.getId(), monster);
                this.activeMonsters += 1;
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

    public void decreaseActiveMonstersByOne(){
        this.activeMonsters -= 1;
    }
    public int getActiveMonsters(){
        return this.activeMonsters;
    }

    public int getId(){
        return this.spawnerId;
    }
}
