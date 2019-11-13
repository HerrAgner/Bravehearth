package com.mygdx.game.entities;

import java.util.ArrayList;
import java.util.List;

public class Backpack {
    private List items;
    private int avatarId;
    private int wallet;
    private int id;

    public Backpack(int avatarId) {
        this.avatarId = avatarId;
        this.id = id;
        this.items = new ArrayList<>();
    }

    public Backpack() { this.items = new ArrayList<>(); }
}
