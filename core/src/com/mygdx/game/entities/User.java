package com.mygdx.game.entities;

import com.mygdx.game.entities.avatar.Avatar;

public class User {
    private int id;
    private String username;
    private Avatar avatar;

    public User() {}

    public User(String username, Avatar avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) { this.avatar = avatar; }

    public int getId() {
        return id;
    }
}
