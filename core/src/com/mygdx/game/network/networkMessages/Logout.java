package com.mygdx.game.network.networkMessages;

import com.mygdx.game.network.ClientConnection;

import java.util.UUID;

public class Logout {
    private UUID avatar;


    public Logout() {
        if (ClientConnection.getInstance().getUser() != null) {
            this.avatar = ClientConnection.getInstance().getUser().getAvatar().getId();
        }
    }

    public UUID getAvatar() {
        return avatar;
    }

    public void setAvatar(UUID avatar) {
        this.avatar = avatar;
    }
}
