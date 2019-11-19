package com.mygdx.game.network.networkMessages;

import com.mygdx.game.entities.Items.Item;

public class ItemPickup {
    int avatarId;
    Item item;
    float x;
    float y;

    public ItemPickup() {
    }

    public ItemPickup(int avatarId, Item item, float x, float y) {
        this.avatarId = avatarId;
        this.item = item;
        this.x = x;
        this.y = y;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
