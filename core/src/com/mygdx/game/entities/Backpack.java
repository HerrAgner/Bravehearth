package com.mygdx.game.entities;

import com.mygdx.game.entities.Items.Item;

import java.util.ArrayList;
import java.util.UUID;

public class Backpack {
    private ArrayList<Item> items;
    private int avatarId;
    private int wallet;


    public Backpack(int avatarId) {
        this.avatarId = avatarId;
        this.items = new ArrayList<>();
    }

    public Backpack() {
        this.items = new ArrayList<>();
    }

}
