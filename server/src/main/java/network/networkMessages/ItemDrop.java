package network.networkMessages;

import network.networkMessages.items.Item;

public class ItemDrop {
    float x;
    float y;
    Item item;
    private int avatarId;
    private int id;

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

    public float getY() {
        return y;
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

    public int getId() {
        return id;
    }
}
