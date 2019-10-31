package com.mygdx.game.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.entities.Avatar;
import com.mygdx.game.network.networkMessages.*;
import com.mygdx.game.util.CharacterClass;

import java.io.IOException;

public class ClientConnection {
    private static ClientConnection single_instance = null;

    private Client client;
    private Avatar avatar;

    private ClientConnection() {
        client = new Client();
        client.start();
        try {
            client.connect(5000, "localhost", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        registerClasses();
        login();

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

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    private void login(){
        client.sendTCP(new Login("Ted", "Tedinator"));
    }

    private void registerClasses(){
        Kryo kryo = client.getKryo();
        kryo.register(Health.class);
        kryo.register(Position.class);
        kryo.register(Avatar.class);
        kryo.register(Login.class);
        kryo.register(CharacterClass.class);
        kryo.register(User.class);
    }
}
