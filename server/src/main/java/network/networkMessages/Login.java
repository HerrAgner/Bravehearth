package network.networkMessages;

import network.networkMessages.avatar.Avatar;

public class Login {
    private String username;
    private String password;
    private Avatar avatar;

    public Login(){}

    public Login(String username, Avatar avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }
}
