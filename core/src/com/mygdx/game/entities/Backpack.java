package com.mygdx.game.entities;

import com.mygdx.game.entities.Items.Item;
import java.util.ArrayList;
import java.util.List;

public class Backpack {
    private List<Item> items;
    private int avatarId;
    private int wallet;
    private int id;
    private boolean changed;

    public Backpack(int avatarId) {
        this.avatarId = avatarId;
        this.id = id;
        this.items = new ArrayList<>();
    }

    public Backpack() {
        this.items = new ArrayList<>();
    }

    public void setItems(List items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public int getWallet() { return wallet; }

    public void setWallet(int wallet) { this.wallet = wallet; }
}
