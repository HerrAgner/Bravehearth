package com.mygdx.game.network.networkMessages;

import com.mygdx.game.entities.Avatar;

public class Login {
    private String username;
    private Avatar avatar;


    public Login(){}

    public Login(String username, Avatar avatar) {
        this.username = username;
        this.avatar = avatar;
    }
}
