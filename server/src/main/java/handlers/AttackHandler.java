package handlers;


import game.GameServer;
import network.networkMessages.AttackEnemyTarget;
import network.networkMessages.HealthChange;
import network.networkMessages.Monster;
import network.networkMessages.avatar.Avatar;

import java.util.ArrayList;
import java.util.Random;
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
            if (GameServer.getInstance().getMh().monsterList.get(attack.get(2)) != null && GameServer.getInstance().aa.get(attack.get(1)) != null) {
                avatar = GameServer.getInstance().aa.get(attack.get(1));
                monster = GameServer.getInstance().getMh().monsterList.get(attack.get(2));
                attackerX = avatar.getX();
                attackerY = avatar.getY();
                attackerRange = avatar.getAttackRange();
                targetX = monster.getX();
                targetY = monster.getY();
            }
        } else if (attack.get(0) == 2) {
            if (GameServer.getInstance().getMh().monsterList.get(attack.get(1)) != null && GameServer.getInstance().aa.get(attack.get(2)) != null) {
                avatar = GameServer.getInstance().aa.get(attack.get(2));
                monster = GameServer.getInstance().getMh().monsterList.get(attack.get(1));
                attackerX = monster.getX();
                attackerY = monster.getY();
                attackerRange = monster.getAttackRange();
                targetX = avatar.getX();
                targetY = avatar.getY();
            }
        }


        if ((targetX < attackerX +1 + attackerRange
                && targetX > attackerX -1 - attackerRange)
                && (targetY < attackerY +1 + attackerRange
                && targetY > attackerY -1 - attackerRange)) {
            try {
                calculateDamageDealt(attack.get(0), attack.get(1), attack.get(2));
            } catch (NullPointerException ignored) {
            }
        }
    }

    private void calculateDamageDealt(int type, int attackerId, int targetId){
        // Check attacker damage vs attacker defence
        // Need maybe hit chance in % and make a roll if the attack hits or misses
        Random r = new Random();
        Avatar avatar;
        Monster monster;
        int newHealth;
        String attackDistance;
        String attackerUnit = "";
        String targetUnit = "";
        int damageDone = 0;

        if (type == 1) {
            avatar = GameServer.getInstance().aa.get(attackerId);
            monster = GameServer.getInstance().getMh().monsterList.get(targetId);
            attackerUnit = "avatar";
            targetUnit = "monster";
            attackDistance = getAttackDistance(avatar.getAttackRange());

            if (attackDistance.equals("ranged")) {
                damageDone = avatar.getAttackDamage() + (avatar.getDexterity() % 6);
            } else if (attackDistance.equals("melee")){
                damageDone = avatar.getAttackDamage() + (avatar.getStrength() % 6);
            }
            damageDone = (int) ((damageDone*0.9f) + r.nextFloat() * ((damageDone*1.1f) - (damageDone*0.9f)));
            newHealth = monster.getHp() - damageDone;
            GameServer.getInstance().getMh().monsterList.get(targetId).setHp(newHealth);


        } else {
            attackerUnit = "monster";
            targetUnit = "avatar";
            avatar = GameServer.getInstance().aa.get(targetId);
            monster = GameServer.getInstance().getMh().monsterList.get(attackerId);

            damageDone = (int) ((monster.getAttackDamage() * monster.getAttackDamage()) / (monster.getAttackDamage() +(avatar.getDefense()/2)));
            if (damageDone> 1){
                damageDone = (int) ((damageDone*0.9f) + r.nextFloat() * ((damageDone*1.1f) - (damageDone*0.9f)));
            } else {
                damageDone = (int) (0 + r.nextFloat() * 2);

            }

            newHealth = avatar.getHealth() - (int) Math.ceil(damageDone);
            GameServer.getInstance().aa.get(targetId).setHealth(newHealth);
            attackDistance = getAttackDistance(monster.getAttackRange());
        }

        HealthChange healthChange = new HealthChange(newHealth, targetId, attackerId, type);
        AttackEnemyTarget aet = new AttackEnemyTarget(attackerId, targetId, attackerUnit, targetUnit, attackDistance, healthChange);

        try {
            validatedAttacks.put(aet);
//
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
