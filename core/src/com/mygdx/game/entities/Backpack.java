package com.mygdx.game.entities;

import com.mygdx.game.entities.Items.Item;

import java.util.ArrayList;
import java.util.List;

public class Backpack {
    private List<Item> items;
    private int avatarId;
    private int wallet;
    private int id;

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

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
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
}
