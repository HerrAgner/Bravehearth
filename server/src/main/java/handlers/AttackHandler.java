package handlers;


import game.GameServer;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.HealthChange;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class AttackHandler {
    private LinkedBlockingQueue<HashMap<Integer, UUID>> attackList = new LinkedBlockingQueue<>();
    public static LinkedBlockingQueue<HealthChange> validatedAttacks = new LinkedBlockingQueue<>();

    public void addAttackerToList(int attacker, UUID target) {
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

    private void calculateAttackRange(HashMap<Integer, UUID> attack) {
        Map.Entry<Integer, UUID> entry = attack.entrySet().iterator().next();
        Avatar attacker = GameServer.getInstance().aa.get(entry.getKey());
        Avatar target = GameServer.getInstance().aa.get(entry.getValue());

        if ((target.getX() < attacker.getX() +1 + attacker.getAttackRange()
                && target.getX() > attacker.getX() -1 - attacker.getAttackRange())
                && (target.getY() < attacker.getY() +1 + attacker.getAttackRange()
                && target.getY() > attacker.getY() -1 - attacker.getAttackRange())) {
            calculateDamageDealt(attacker, target);
        }
    }

    private void calculateDamageDealt(Avatar attacker, Avatar target){
        // Check attacker damage vs attacker defence
        // Need maybe hit chance in % and make a roll if the attack hits or misses

        int newHealth = target.getHealth() - attacker.getAttackDamage();
        GameServer.getInstance().aa.get(target.getId()).setHealth(newHealth);

        HealthChange healthChange = new HealthChange(GameServer.getInstance().aa.get(target.getId()).getHealth(), target.getId(), attacker.getId());

        try {
            validatedAttacks.put(healthChange);
        } catch (InterruptedException e) {
            System.out.println("Could not add attack to list.");
        }
    }
}
