package network.networkMessages.avatar;


import network.networkMessages.items.Item;
import network.networkMessages.items.WearableType;

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

    }
}
