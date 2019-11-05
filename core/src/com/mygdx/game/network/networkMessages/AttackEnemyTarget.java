package com.mygdx.game.network.networkMessages;

import java.util.UUID;

public class AttackEnemyTarget {
    private UUID attacker;
    private UUID target;

    public AttackEnemyTarget(UUID attacker, UUID target) {
        this.attacker = attacker;
        this.target = target;
    }
    public AttackEnemyTarget(){

    }
}
