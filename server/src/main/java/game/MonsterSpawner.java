package game;


import java.util.ArrayList;

public class MonsterSpawner {
    private int monsterId;
    private ArrayList<Integer[]> spawnPoints;
    private float spawnRadius;
    private float spawnTimer;
    private int monsterLimit;
    private int activeMonsters;

    public MonsterSpawner(int monsterId, ArrayList<Integer[]> importSpawnPoints) {
        this.spawnPoints = importSpawnPoints;
        this.monsterId = monsterId;
        this.monsterLimit = 5;
        this.spawnRadius = 10;
        this.spawnTimer = 60f;
        spawnPoints.forEach(integers -> System.out.println(integers[0] + " " + integers[1]));
    }

    public void spawnMonster() {
        if (activeMonsters < monsterLimit) {

        }
    }

}
