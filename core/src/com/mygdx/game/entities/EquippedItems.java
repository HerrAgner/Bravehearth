package com.mygdx.game.entities;

import com.mygdx.game.entities.Items.Item;
import com.mygdx.game.entities.Items.WearableType;

import java.util.HashMap;

public class EquippedItems {
    private HashMap<WearableType, Item> equippedItems;


    public EquippedItems() {
        this.equippedItems = new HashMap<>();
    }
}
