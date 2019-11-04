package network.networkMessages;

import network.networkMessages.avatar.Avatar;

public class Login {
    private String username;
    private Avatar avatar;

    public Login(){}

    public Login(String username, Avatar avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
