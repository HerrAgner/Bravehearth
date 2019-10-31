package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.DummyClass;
import com.mygdx.game.entities.Avatar;
import com.mygdx.game.util.CharacterClass;

public class ClientNetworkListener {
    public ClientNetworkListener() {
        ClientConnection.getInstance().getClient().addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof Avatar) {
                    Avatar avatar;
                    if (((Avatar) object).getCharacterClass().equals(CharacterClass.DUMMYCLASS)){
                        avatar = new DummyClass((Avatar) object);
                        avatar.setPosition(((Avatar) object).getX(), ((Avatar) object).getY());
                        System.out.println("testing. Printing player X: " + avatar.getX());
                        System.out.println("testing. Printing player Y: " + avatar.getY());
                    }
                    ClientConnection.getInstance().setAvatar((Avatar) object);
                }
                if (object instanceof String) {
                    System.out.println(object);
                }
            }
        });
    }
}
