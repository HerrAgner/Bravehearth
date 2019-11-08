package handlers;


import game.GameServer;
import network.networkMessages.Monster;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.HealthChange;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class AttackHandler {
    private LinkedBlockingQueue<HashMap<Integer, Integer>> attackList = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<ArrayList<Integer>> attackListt = new LinkedBlockingQueue<>();
    public static LinkedBlockingQueue<HealthChange> validatedAttacks = new LinkedBlockingQueue<>();

    public void addAttackerToList(int attacker, int target, int targetType) {
        attackListt.offer(new ArrayList<>(){{
            add(targetType);
            add(attacker);
            add(target);
        }});
//        attackList.offer(new HashMap<>() {{
//            put(attacker, target);
//        }});
        doAttack();
    }

    private void doAttack() {
        if (attackListt.size() > 0) {
            calculateAttackRangee(attackListt.poll());
        }
    }
    private void calculateAttackRangee(ArrayList<Integer> attack) {
        Monster monster;
        Avatar avatar;
        float attackerX = 0;
        float attackerY = 0;
        float attackerRange = 0;
        float targetX = 0;
        float targetY = 0;

        if (attack.get(0) == 1) {
            avatar = GameServer.getInstance().aa.get(attack.get(1));
            monster = MonsterHandler.monsterList.get(attack.get(2));
            attackerX = avatar.getX();
            attackerY = avatar.getY();
            attackerRange = avatar.getAttackRange();
            targetX = monster.getX();
            targetY = monster.getY();
        } else if (attack.get(0) == 2) {
            avatar = GameServer.getInstance().aa.get(attack.get(2));
            monster = MonsterHandler.monsterList.get(attack.get(1));
            attackerX = monster.getX();
            attackerY = monster.getY();
            attackerRange = monster.getAttackRange();
            targetX = avatar.getX();
            targetY = avatar.getY();
        }


        if ((targetX < attackerX +1 + attackerRange
                && targetX > attackerX -1 - attackerRange)
                && (targetY < attackerY +1 + attackerRange
                && targetY > attackerY -1 - attackerRange)) {
            calculateDamageDealtt(attack.get(0), attack.get(1), attack.get(2));
        }
    }

    private void calculateDamageDealtt(int type, int attackerId, int targetId){
        // Check attacker damage vs attacker defence
        // Need maybe hit chance in % and make a roll if the attack hits or misses

        Avatar avatar;
        Monster monster;
        int newHealth;

        if (type == 1) {
            avatar = GameServer.getInstance().aa.get(attackerId);
            monster = MonsterHandler.monsterList.get(targetId);
            newHealth = monster.getHp() - avatar.getAttackDamage();
            MonsterHandler.monsterList.get(targetId).setHp(newHealth);

        } else {
            avatar = GameServer.getInstance().aa.get(targetId);
            monster = MonsterHandler.monsterList.get(attackerId);
            newHealth = avatar.getHealth() - monster.getAttackDamage();
            GameServer.getInstance().aa.get(targetId).setHealth(newHealth);
        }

        HealthChange healthChange = new HealthChange(newHealth, targetId, attackerId, type);

        try {
            validatedAttacks.put(healthChange);
        } catch (InterruptedException e) {
            System.out.println("Could not add attack to list.");
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

        HealthChange healthChange = new HealthChange(MonsterHandler.monsterList.get(targetId).getHp(), target.getId(), attacker.getId(), 1);

        try {
            validatedAttacks.put(healthChange);
        } catch (InterruptedException e) {
            System.out.println("Could not add attack to list.");
        }
    }
}
