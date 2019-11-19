package network.networkMessages;

import network.networkMessages.items.Item;
import network.networkMessages.items.WearableType;

public class EquippedItemChange {

    private WearableType type;
    private Item item;
    private int avatarId;

    public EquippedItemChange(){

    }

    public EquippedItemChange(WearableType wearableType, Item item, int id){
        this.type=wearableType;
        this.item=item;
        this.avatarId= id;
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
}

