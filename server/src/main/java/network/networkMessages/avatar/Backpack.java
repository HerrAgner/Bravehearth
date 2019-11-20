package network.networkMessages.avatar;

import database.Column;
import network.networkMessages.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Backpack {
    private List<Item> items;
    @Column
    private int avatarId;
    @Column
    private int wallet;
    @Column
    private int id;
    private boolean changed;

    public Backpack(int avatarId) {
        this.avatarId = avatarId;
        this.id = id;
        this.items = new ArrayList<>();
    }

    public Backpack() { }

    public int getId() { return id; }

    public void setItems(List bpItems) { this.items = bpItems;}

    public List<Item> getItems() { return items; }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }
    public void addGold(int gold){
        this.wallet += gold;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }
    public int getAvatarId(){ return avatarId; }
}
