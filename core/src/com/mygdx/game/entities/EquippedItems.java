package com.mygdx.game.entities;

import com.mygdx.game.entities.Items.Item;
import com.mygdx.game.entities.Items.WearableType;

import java.util.HashMap;
import java.util.UUID;

public class EquippedItems {
    private HashMap<WearableType, Item> equippedItems;
    private UUID avatarId;



    public EquippedItems(UUID avatarId) {
        this.avatarId = avatarId;
        this.equippedItems = new HashMap<>();
    }

    public EquippedItems() {
        this.equippedItems = new HashMap<>();
    }
}
