package handlers;


import game.GameServer;
import network.networkMessages.AttackEnemyTarget;
import network.networkMessages.Monster;
import network.networkMessages.UnitDeath;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.HealthChange;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class AttackHandler {
//    private LinkedBlockingQueue<HashMap<Integer, Integer>> attackList = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<ArrayList<Integer>> attackList = new LinkedBlockingQueue<>();
    public static LinkedBlockingQueue<HealthChange> validatedAttack = new LinkedBlockingQueue<>();
    public static LinkedBlockingQueue<AttackEnemyTarget> validatedAttacks = new LinkedBlockingQueue<>();

    public void addAttackerToList(int attacker, int target, int targetType) {
        attackList.offer(new ArrayList<>(){{
            add(targetType);
            add(attacker);
            add(target);
        }});

        if (targetType == 2 && GameServer.getInstance().aa.get(target) != null) {
            doAttack();
        } else if (targetType == 1 && GameServer.getInstance().getMh().monsterList.get(target) != null){
            doAttack();
        }
    }

    private void doAttack() {
        if (attackList.size() > 0) {
            calculateAttackRange(attackList.poll());
        }
    }
    private void calculateAttackRange(ArrayList<Integer> attack) {
        Monster monster;
        Avatar avatar;
        float attackerX = 0;
        float attackerY = 0;
        float attackerRange = 0;
        float targetX = 0;
        float targetY = 0;

        if (attack.get(0) == 1) {
            avatar = GameServer.getInstance().aa.get(attack.get(1));
            monster = GameServer.getInstance().getMh().monsterList.get(attack.get(2));
            attackerX = avatar.getX();
            attackerY = avatar.getY();
            attackerRange = avatar.getAttackRange();
            targetX = monster.getX();
            targetY = monster.getY();
        } else if (attack.get(0) == 2) {
            avatar = GameServer.getInstance().aa.get(attack.get(2));
            monster = GameServer.getInstance().getMh().monsterList.get(attack.get(1));
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
            calculateDamageDealt(attack.get(0), attack.get(1), attack.get(2));
        }
    }

    private void calculateDamageDealt(int type, int attackerId, int targetId){
        // Check attacker damage vs attacker defence
        // Need maybe hit chance in % and make a roll if the attack hits or misses

        Avatar avatar;
        Monster monster;
        int newHealth;
        String attackDistance;
        String attackerUnit = "";
        String targetUnit = "";

        if (type == 1) {
            avatar = GameServer.getInstance().aa.get(attackerId);
            monster = GameServer.getInstance().getMh().monsterList.get(targetId);
            attackerUnit = "avatar";
            targetUnit = "monster";

            newHealth = monster.getHp() - avatar.getAttackDamage();
            GameServer.getInstance().getMh().monsterList.get(targetId).setHp(newHealth);
            attackDistance = getAttackDistance(avatar.getAttackRange());
            if (GameServer.getInstance().getMh().monsterList.get(targetId).getHp() <= 0) {
                GameServer.getInstance().aa.get(attackerId).setMarkedUnit(-1);
                GameServer.getInstance().getServer().sendToAllTCP(new UnitDeath(avatar.getId(), monster.getId(), "monster", monster.getExperiencePoints()));
                GameServer.getInstance().getMh().monsterList.remove(targetId);
            }

        } else {
            attackerUnit = "monster";
            targetUnit = "avatar";
            avatar = GameServer.getInstance().aa.get(targetId);
            monster = GameServer.getInstance().getMh().monsterList.get(attackerId);
            newHealth = avatar.getHealth() - monster.getAttackDamage();
            GameServer.getInstance().aa.get(targetId).setHealth(newHealth);
            attackDistance = getAttackDistance(monster.getAttackRange());
        }

        HealthChange healthChange = new HealthChange(newHealth, targetId, attackerId, type);
        AttackEnemyTarget aet = new AttackEnemyTarget(attackerId, targetId, attackerUnit, targetUnit, attackDistance, healthChange);

        try {
            validatedAttacks.put(aet);
//            validatedAttacks.put(healthChange);
        } catch (InterruptedException e) {
            System.out.println("Could not add attack to list.");
        }
    }

    private String getAttackDistance(float range) {
        if (range > 2) {
            return "ranged";
        } else {
            return "melee";
        }
    }
}
