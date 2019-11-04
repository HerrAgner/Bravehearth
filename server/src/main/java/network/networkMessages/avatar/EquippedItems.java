package network.networkMessages.avatar;


import network.networkMessages.items.Item;
import network.networkMessages.items.WearableType;

import java.util.HashMap;

public class EquippedItems {
    private HashMap<WearableType, Item> equippedItems;


    public EquippedItems() {
        this.equippedItems = new HashMap<>();
    }
}
