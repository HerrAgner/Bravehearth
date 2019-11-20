package network.networkMessages;

import network.networkMessages.items.Item;
import network.networkMessages.items.WearableType;

public class EquippedItemChange {

    private WearableType type;
    private Item item;
    private int avatarId;
    private boolean isUnequipping;

    public EquippedItemChange(){ }

    public EquippedItemChange(WearableType wearableType, Item item, int id, boolean isUnequipping){
        this.type=wearableType;
        this.item=item;
        this.avatarId= id;
        this.isUnequipping = isUnequipping;
    }

    public WearableType getType() {
        return type;
    }

    public Item getItem() {
        return item;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public boolean isUnequipping() {
        return isUnequipping;
    }
}

