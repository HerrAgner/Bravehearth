package com.mygdx.game.entities.Items;

import java.util.HashMap;

public class Wearable extends Item{
    private HashMap<String, Float> statChange;
    private int defence;
    private WeaponType weaponType;


    public Wearable(Item item, HashMap<String, Float> statChange, int defence, WeaponType weaponType) {
        super(item);
        this.statChange = statChange;
        this.defence = defence;
        this.weaponType = weaponType;
    }

    public Wearable() {

    }
}
