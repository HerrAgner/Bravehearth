package com.mygdx.game.entities;

public class User {
    //on log-in, create user object containing an instance of player
    //add this user to the global array of active users in activeUserHandler
    // ActiveUserHandler.addToActiveUsers();
    //do this on log-in, maybe?

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

    public String getUsername() { return username; }

}