package com.mygdx.game.network.networkMessages;

public class AttackEnemyTarget {
    private int attacker;
    private int target;
    private String attackerUnit;
    private String targetUnit;
    private String attackType;
    private HealthChange hc;

    public AttackEnemyTarget(int attacker, int target) {
        this.attacker = attacker;
        this.target = target;
    }

    public AttackEnemyTarget(){ }

    public int getAttacker() {
        return attacker;
    }

    public int getTarget() {
        return target;
    }

    public String getAttackType() {
        return attackType;
    }

    public HealthChange getHc() {
        return hc;
    }
}
