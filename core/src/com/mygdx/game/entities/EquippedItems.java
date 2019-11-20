package com.mygdx.game.entities;

import com.mygdx.game.entities.Items.Item;
import com.mygdx.game.entities.Items.WearableType;

import java.util.HashMap;

public class EquippedItems {
    private HashMap<WearableType, Item> equippedItems;
    private int avatarId;
    private boolean isChanged;

    public EquippedItems(int avatarId) {
        this.avatarId = avatarId;
        this.equippedItems = new HashMap<>();
    }

    public EquippedItems() {
        this.equippedItems = new HashMap<>();
    }

    public HashMap<WearableType, Item> getItems() {
        return equippedItems;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public boolean isChanged() {
        return isChanged;
    }
}
