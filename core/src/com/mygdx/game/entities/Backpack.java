package com.mygdx.game.entities;

import com.mygdx.game.entities.Items.Item;

import java.util.ArrayList;
import java.util.UUID;

public class Backpack {
    private ArrayList<Item> items;
    private UUID avatarId;


    public Backpack(UUID avatarId) {
        this.avatarId = avatarId;
        this.items = new ArrayList<>();
    }

    public Backpack() {
        this.items = new ArrayList<>();
    }

}
