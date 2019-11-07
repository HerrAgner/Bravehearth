package handlers;


import game.GameServer;
import network.networkMessages.Monster;

import java.util.HashMap;
import java.util.UUID;

public class MonsterHandler {
    public static HashMap<UUID, Monster> monsterList;

    public MonsterHandler() {
        monsterList = new HashMap<>();

    }

    public void addMonster(UUID id, Monster monster){
        monsterList.put(id, monster);
    }

    private void moveMonster(){
        monsterList.values().forEach(monster -> {

        });
    }

}
