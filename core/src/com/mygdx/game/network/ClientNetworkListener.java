package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.DummyClass;
import com.mygdx.game.entities.Avatar;
import com.mygdx.game.entities.User;
import com.mygdx.game.util.CharacterClass;

public class ClientNetworkListener {
    public ClientNetworkListener() {
        ClientConnection.getInstance().getClient().addListener(new Listener() {
            public void received (Connection connection, Object object) {

                if (object instanceof User) {
                    Avatar avatar;
                    User user;
                    if (((User) object).getAvatar().getCharacterClass().equals(CharacterClass.DUMMYCLASS)){
                        avatar = new DummyClass(((User) object).getAvatar());
                        avatar.setPosition(((User) object).getAvatar().getX(), ((User) object).getAvatar().getY());
                        avatar.setCharacterClass(((User) object).getAvatar().getCharacterClass());

                        user = new User(((User) object).getUsername(), avatar);

                        ClientConnection.getInstance().setUser(user);
                    }
                }

                if (object instanceof String) {
                    System.out.println(object);
                }
            }
        });
    }
}
