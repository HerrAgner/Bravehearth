package com.mygdx.game.network.networkMessages;

public class Login {
    private String username;
    private String character;


    public Login(){

    }
    public Login(String username, String character) {
        this.username = username;
        this.character = character;
    }
}
