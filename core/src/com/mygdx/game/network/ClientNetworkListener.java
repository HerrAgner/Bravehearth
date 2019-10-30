package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientNetworkListener {
    public ClientNetworkListener() {
        ClientConnection.getInstance().getClient().addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof String) {
                    System.out.println(object);
                }
            }
        });
    }
}
