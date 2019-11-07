package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.avatar.DummyClass;
import com.mygdx.game.entities.avatar.Avatar;
import com.mygdx.game.network.networkMessages.HealthChange;
import com.mygdx.game.network.networkMessages.Logout;
import com.mygdx.game.network.networkMessages.Position;
import com.mygdx.game.entities.User;
import com.mygdx.game.util.CharacterClass;

public class ClientNetworkListener {
    public ClientNetworkListener() {
        ClientConnection.getInstance().getClient().addListener(new Listener() {
            public void received(Connection connection, Object object) {

                if (object instanceof User) {
                    ClientConnection.getInstance().setUser((User) object);
                }

                if(object instanceof Avatar) {
                    ClientConnection.getInstance().addActiveAvatar((Avatar) object);
                }

                if (object instanceof Logout) {
                    if (((Logout) object).getAvatar() == (ClientConnection.getInstance().getUser().getAvatar().getId())) {
                        ClientConnection.getInstance().getClient().stop();
                    }
                    ClientConnection.getInstance().getActiveAvatars().remove(((Logout) object).getAvatar());
                }

                if (object instanceof String) {
                    System.out.println(object);
                }

                if (object instanceof HealthChange) {
                    ClientConnection.getInstance().getActiveAvatars().get(((HealthChange) object).getReceivingAvatar())
                            .setHealth(((HealthChange) object).getNewHealth());
                }

                if (object instanceof Position) {
                    ClientConnection.getInstance().getActiveAvatars()
                            .get(((Position) object).getId())
                            .setPosition(((Position) object).getX(), ((Position) object).getY());
                }
            }
        });
    }
}
