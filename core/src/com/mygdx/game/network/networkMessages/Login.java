package com.mygdx.game.network.networkMessages;

import com.mygdx.game.entities.avatar.Avatar;

public class Login {
    private String username;
    private String password;
    private Avatar avatar;

    public Login(){}

    public Login(String username, Avatar avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
