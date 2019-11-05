package com.mygdx.game.entities.Items;

import java.util.HashMap;

public class Consumable extends Item {
    private HashMap<String, Float> statChange;


    public Consumable(Item item, HashMap<String, Float> statChange) {
        super(item);
        this.statChange = statChange;
    }

    public Consumable() {

    }
}
