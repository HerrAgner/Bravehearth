package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.DummyClass;
import com.mygdx.game.entities.Player;
import com.mygdx.game.util.CharacterClass;

public class ClientNetworkListener {
    public ClientNetworkListener() {
        ClientConnection.getInstance().getClient().addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof Player) {
                    Player player;
                    if (((Player) object).getCharacterClass().equals(CharacterClass.DUMMYCLASS)){
                        player = new DummyClass((Player) object);
                        player.setPosition(((Player) object).getX(), ((Player) object).getY());
                        System.out.println("testing. Printing player X: " + player.getX());
                        System.out.println("testing. Printing player Y: " + player.getY());
                    }
                    ClientConnection.getInstance().setPlayer((Player) object);
                }
                if (object instanceof String) {
                    System.out.println(object);
                }
            }
        });
    }
}
