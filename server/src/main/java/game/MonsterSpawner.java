package game;

import network.networkMessages.Monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class MonsterSpawner {
    private int monsterId;
    private HashMap<Integer, ArrayList<String>> spawnPoints;
    private float spawnRadius;
    private float spawnTimer;
    private int monsterLimit;
    private int activeMonsters;

    public MonsterSpawner(int monsterId, ArrayList<String> spawnPoints) {
        spawnPoints.forEach(s -> System.out.println(s));
        this.spawnPoints = new HashMap<>();
        this.monsterId = monsterId;
//            int x = Integer.parseInt(s.split(",")[0]);
//            int y = Integer.parseInt(s.split(",")[1]);
            this.spawnPoints.computeIfAbsent(monsterId, k -> spawnPoints);
            this.spawnPoints.forEach((integer, strings) -> {
//                this.spawnPoints.get(integer).forEach(System.out::println);
            });
    }

    public void spawnMonster() {
        if (activeMonsters < monsterLimit) {

        }
    }

}
