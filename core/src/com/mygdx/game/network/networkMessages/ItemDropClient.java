package com.mygdx.game.network.networkMessages;

import com.mygdx.game.entities.Items.Item;

public class ItemDropClient {
     float x;
     float y;
     Item item;
     private int avatarId;
     private int id;

     public ItemDropClient() {
         avatarId = -1;
         id = 0;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
