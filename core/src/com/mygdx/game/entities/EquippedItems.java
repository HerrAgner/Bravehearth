package com.mygdx.game.entities;

import com.mygdx.game.entities.Items.Item;
import com.mygdx.game.entities.Items.WearableType;

import java.util.HashMap;

public class EquippedItems {
    private HashMap<WearableType, Item> equippedItems;
    private int avatarId;

    public EquippedItems(int avatarId) {
        this.avatarId = avatarId;
        this.equippedItems = new HashMap<>();
    }

    public EquippedItems() {
        this.equippedItems = new HashMap<>();
    }

    public HashMap<WearableType, Item> getEquippedItems() {
        return equippedItems;
    }
}
