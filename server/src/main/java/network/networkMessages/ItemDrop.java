package network.networkMessages;

import network.networkMessages.items.Item;

public class ItemDrop {
     float x;
     float y;
     Item item;

     public ItemDrop() {
     }
     public ItemDrop(float x, float y, Item item) {
         this.x = x;
         this.y = y;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
