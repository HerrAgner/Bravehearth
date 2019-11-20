package com.mygdx.game.network.networkMessages;

public class UnitDeath {
    int attackerId;
    int targetId;
    String unit;
    int exp;

    public UnitDeath(){ }

    public UnitDeath(int attackerId, int targetId, String unit, int exp) {
        this.attackerId = attackerId;
        this.targetId = targetId;
        this.unit = unit;
        this.exp = exp;
    }

    public int getAttackerId() {
        return attackerId;
    }

    public int getTargetId() {
        return targetId;
    }

    public String getUnit() {
        return unit;
    }

    public int getExp() {
        return exp;
    }
}
