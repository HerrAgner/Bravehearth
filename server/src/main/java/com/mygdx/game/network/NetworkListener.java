package com.mygdx.game.network;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import game.GameServer;
import handlers.CommandHandler;

public class NetworkListener {

    public NetworkListener() {
        Server server = GameServer.getInstance().getServer();
        CommandHandler ch = new CommandHandler();
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {

                if (object instanceof String) {
                    ch.addToQueue((String) object);
                }

            }
        });

    }
}
