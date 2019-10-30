package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.entities.Player;

import java.io.IOException;

public class ClientConnection {
    private static ClientConnection single_instance = null;

    private Client client;
    private Player player;

    private ClientConnection() {
        client = new Client();
        client.start();
        try {
            client.connect(5000, "localhost", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static ClientConnection getInstance()
    {
        if (single_instance == null)
            single_instance = new ClientConnection();

        return single_instance;
    }


    public Client getClient() {
        return this.client;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
