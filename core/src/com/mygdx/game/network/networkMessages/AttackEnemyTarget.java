
package com.mygdx.game.network.networkMessages;


public class AttackEnemyTarget {
    private int attacker;
    private int target;
    private int attackerType;

    public AttackEnemyTarget(int attacker, int target) {
        this.attacker = attacker;
        this.target = target;
    }

    public AttackEnemyTarget(){ }
}
