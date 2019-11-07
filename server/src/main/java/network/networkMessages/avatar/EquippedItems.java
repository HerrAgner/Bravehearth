package network.networkMessages.avatar;

import network.networkMessages.items.Item;
import network.networkMessages.items.WearableType;
import java.util.HashMap;

public class EquippedItems {
    private HashMap<WearableType, Item> equippedItems;
    private int avatarId;


    public EquippedItems(int avatarId, HashMap<WearableType, Item> equippedItems) {
        this.avatarId = avatarId;
        this.equippedItems = equippedItems;
    }

    public HashMap getEquippedItems() { return equippedItems; }

    public EquippedItems() { }
}
