package network.networkMessages;

import network.networkMessages.items.Item;

public class ItemPickup {
    int avatarId;
    Item item;
    float x;
    float y;

    public ItemPickup() { }

    public ItemPickup(int avatarId, Item item, float x, float y) {
        this.avatarId = avatarId;
        this.item = item;
        this.x = x;
        this.y = y;
    }

    public int getAvatarId() {
        return avatarId;
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

    public float getY() {
        return y;
    }
}
