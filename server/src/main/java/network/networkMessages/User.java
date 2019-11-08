package network.networkMessages;

import database.Column;
import network.networkMessages.avatar.Avatar;

public class User {
    @Column
    private int id;
    @Column
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

    public String getUsername() { return username; }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public int getId() { return id; }
}
