package com.mygdx.game.network.networkMessages;

import com.mygdx.game.network.ClientConnection;

public class Logout {
    private int avatar;

    public Logout() {

        if (ClientConnection.getInstance().getUser() != null) {
            this.avatar = ClientConnection.getInstance().getUser().getAvatar().getId();
        }
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
