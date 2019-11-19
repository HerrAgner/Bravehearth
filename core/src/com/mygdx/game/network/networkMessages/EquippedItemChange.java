package com.mygdx.game.network.networkMessages;

import com.mygdx.game.entities.Items.Item;
import com.mygdx.game.entities.Items.WearableType;

public class EquippedItemChange {

    private WearableType type;
    private Item item;
    private int avatarId;

    public EquippedItemChange(){

    }

    public EquippedItemChange(WearableType wearableType, Item item, int id){
        this.type=wearableType;
        this.item=item;
        this.avatarId=id;
    }

    public WearableType getType() {
        return type;
    }

    public Item getItem() {
        return item;
    }
}
