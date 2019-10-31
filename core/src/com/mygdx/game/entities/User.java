package com.mygdx.game.entities;

public class User {
    //on log-in, create user object containing an instance of player
    //add this user to the global array of active users in activeUserHandler
    private String username;
    private Player player;

    public User(String username, Player player) {
        this.username = username;
        this.player = player;
    }

    private void addUserToArray() {}

}
