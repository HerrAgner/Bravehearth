package network.networkMessages;

import network.networkMessages.avatar.Avatar;

public class User {

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
}
