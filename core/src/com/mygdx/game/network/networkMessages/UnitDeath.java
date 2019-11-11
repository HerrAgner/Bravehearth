package com.mygdx.game.network.networkMessages;

public class UnitDeath {
    int id;
    String unit;
    int exp;

    public UnitDeath(){
    }

    public UnitDeath(int id, String unit, int exp) {
        this.id = id;
        this.unit = unit;
        this.exp = exp;
    }

    public int getId() {
        return id;
    }

    public String getUnit() {
        return unit;
    }

    public int getExp() {
        return exp;
    }
}
