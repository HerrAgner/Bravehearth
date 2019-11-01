package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.DummyClass;
import com.mygdx.game.entities.Avatar;
import com.mygdx.game.network.networkMessages.Position;
import com.mygdx.game.entities.User;
import com.mygdx.game.util.CharacterClass;

public class ClientNetworkListener {
    public ClientNetworkListener() {
        ClientConnection.getInstance().getClient().addListener(new Listener() {
            public void received(Connection connection, Object object) {

                if (object instanceof User) {
                    ClientConnection.getInstance().setUser(createUser((User) object));
                }

                if(object instanceof Avatar) {
                    System.out.println("here I am in instanceOf Avatar!");
                    ClientConnection.getInstance().addActiveAvatar((Avatar) object);
                }

                if (object instanceof String) {
                    System.out.println(object);
                }
                if (object instanceof Position) {
                    ClientConnection.getInstance().getAvatar().setPosition(((Position) object).getX(), ((Position) object).getY());
                }
            }
        });
    }

    private User createUser(User user) {
        Avatar avatar;
        if (user.getAvatar().getCharacterClass().equals(CharacterClass.DUMMYCLASS)) {
            avatar = new DummyClass(user.getAvatar());
            avatar.setPosition(user.getAvatar().getX(), (user.getAvatar().getY()));
            avatar.setCharacterClass(user.getAvatar().getCharacterClass());

            user = new User(user.getUsername(), avatar);
        }
        return (user);
    }
}
