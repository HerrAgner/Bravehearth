package handlers;


import game.GameServer;
import network.networkMessages.Monster;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.HealthChange;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class AttackHandler {
    private LinkedBlockingQueue<HashMap<Integer, Integer>> attackList = new LinkedBlockingQueue<>();
    public static LinkedBlockingQueue<HealthChange> validatedAttacks = new LinkedBlockingQueue<>();

    public void addAttackerToList(int attacker, int target, int targetType) {
        attackList.offer(new HashMap<>() {{
            put(attacker, target);
        }});
        doAttack();
    }

    private void doAttack() {
        if (attackList.size() > 0) {
            calculateAttackRange(attackList.poll());
        }
    }

    private void calculateAttackRange(HashMap<Integer, Integer> attack) {
        Map.Entry<Integer, Integer> entry = attack.entrySet().iterator().next();
        Avatar attacker = GameServer.getInstance().aa.get(entry.getKey());
        Monster target = MonsterHandler.monsterList.get(entry.getValue());

        if ((target.getX() < attacker.getX() +1 + attacker.getAttackRange()
                && target.getX() > attacker.getX() -1 - attacker.getAttackRange())
                && (target.getY() < attacker.getY() +1 + attacker.getAttackRange()
                && target.getY() > attacker.getY() -1 - attacker.getAttackRange())) {
            calculateDamageDealt(attacker.getId(), target.getId());
        }
    }

    private void calculateDamageDealt(int attackerId, int targetId){
        // Check attacker damage vs attacker defence
        // Need maybe hit chance in % and make a roll if the attack hits or misses

        Avatar attacker = GameServer.getInstance().aa.get(attackerId);
        Monster target = MonsterHandler.monsterList.get(targetId);

        int newHealth = target.getHp() - attacker.getAttackDamage();
        MonsterHandler.monsterList.get(target.getId()).setHp(newHealth);

//        GameServer.getInstance().aa.get(target.getId()).setHealth(newHealth);

        HealthChange healthChange = new HealthChange(MonsterHandler.monsterList.get(targetId).getHp(), target.getId(), attacker.getId());

        try {
            validatedAttacks.put(healthChange);
        } catch (InterruptedException e) {
            System.out.println("Could not add attack to list.");
        }
    }
}
