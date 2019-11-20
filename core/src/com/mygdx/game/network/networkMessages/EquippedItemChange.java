package com.mygdx.game.network.networkMessages;

import com.mygdx.game.entities.Items.Item;
import com.mygdx.game.entities.Items.WearableType;

public class EquippedItemChange {

    private WearableType type;
    private Item item;
    private int avatarId;
    private boolean isUnequipping;

    public EquippedItemChange(){

    }

    public EquippedItemChange(WearableType wearableType, Item item, int id, boolean isUnequipping){
        this.type=wearableType;
        this.item=item;
        this.avatarId=id;
        this.isUnequipping = isUnequipping;
    }

    public WearableType getType() {
        return type;
    }

    public Item getItem() {
        return item;
    }

    public boolean isUnequipping() {
        return isUnequipping;
    }
}
