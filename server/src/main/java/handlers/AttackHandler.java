package handlers;


import game.GameServer;
import network.networkMessages.Avatar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class AttackHandler {
    private LinkedBlockingQueue<HashMap<UUID, UUID>> attackList = new LinkedBlockingQueue<>();
    public static LinkedBlockingQueue<HashMap<UUID, UUID>> validatedAttacks = new LinkedBlockingQueue<>();

    public void addAttackerToList(UUID attacker, UUID target) {
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

    private void calculateAttackRange(HashMap<UUID, UUID> attack) {
        Map.Entry<UUID, UUID> entry = attack.entrySet().iterator().next();
        Avatar attacker = GameServer.getInstance().aa.get(entry.getKey());
        Avatar target = GameServer.getInstance().aa.get(entry.getValue());

        if ((target.getX() < attacker.getX() +1 + attacker.getAttackRange()
                && target.getX() > attacker.getX() -1 - attacker.getAttackRange())
                && (target.getY() < attacker.getY() +1 + attacker.getAttackRange()
                && target.getY() > attacker.getY() -1 - attacker.getAttackRange())) {
            calculateDamageDealt(attacker, target);
            System.out.println(target.getX());
            System.out.println(attacker.getX());
            System.out.println(attacker.getX() + attacker.getAttackRange());
        }
    }

    private void calculateDamageDealt(Avatar attacker, Avatar target){
        // Check attacker damage vs attacker defence
        // Need maybe hit chance in % and make a roll if the attack hits or misses

        System.out.println("IN RANGE!");
        validatedAttacks.offer(new HashMap<>() {{
            put(attacker.getId(), target.getId());
        }});
    }
}
