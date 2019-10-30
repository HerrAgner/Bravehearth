package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class ClientConnection {
    private Client client;

    public ClientConnection() {
        client = new Client();
        client.start();
        try {
            client.connect(5000, "localhost", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client getClient() {
        return this.client;
    }
}
