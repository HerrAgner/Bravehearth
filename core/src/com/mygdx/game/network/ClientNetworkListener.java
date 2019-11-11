package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.avatar.*;
import com.mygdx.game.entities.monsters.DummyMonster;
import com.mygdx.game.entities.monsters.Monster;
import com.mygdx.game.network.networkMessages.HealthChange;
import com.mygdx.game.network.networkMessages.Logout;
import com.mygdx.game.network.networkMessages.Position;
import com.mygdx.game.entities.User;
import com.mygdx.game.network.networkMessages.UnitDeath;
import com.mygdx.game.util.CharacterClass;
import com.mygdx.game.util.MonsterType;

public class ClientNetworkListener {
    public ClientNetworkListener() {
        ClientConnection.getInstance().getClient().addListener(new Listener() {
            public void received(Connection connection, Object object) {

                if (object instanceof User) {
                    ClientConnection.getInstance().setUser((User) object);
                }
                if (object instanceof Avatar) {
                    if (((Avatar) object).getCharacterClass() == CharacterClass.SORCERER) {
                        Sorcerer sorc = new Sorcerer((Avatar) object);
                        ClientConnection.getInstance().addActiveAvatar(sorc);
                    } else if (((Avatar) object).getCharacterClass() == CharacterClass.WARRIOR) {
                        Warrior war = new Warrior((Avatar) object);
                        ClientConnection.getInstance().addActiveAvatar(war);
                    } else if (((Avatar) object).getCharacterClass() == CharacterClass.MARKSMAN) {
                        Marksman mark = new Marksman((Avatar) object);
                        ClientConnection.getInstance().addActiveAvatar(mark);
                    }
                }
                if (ClientConnection.getInstance().getUser() != null) {

                    if (object instanceof Monster) {
                        if (((Monster) object).getType().equals(MonsterType.DUMMYMONSTER)) {
                            DummyMonster dm = new DummyMonster((Monster) object);
                            ClientConnection.getInstance().getActiveMonsters().put(((Monster) object).getId(), dm);

                        }
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
                        if (((HealthChange) object).getType() == 1) {
                            if (ClientConnection.getInstance().getActiveMonsters().get(1) != null) {
                                ClientConnection.getInstance().getActiveMonsters().get(((HealthChange) object).getReceivingAvatar())
                                        .setHp(((HealthChange) object).getNewHealth());
                            }
                        }

                        else if(((HealthChange) object).getType() == 3){
                            ClientConnection.getInstance().getActiveAvatars().get(((HealthChange) object).getReceivingAvatar())
                                    .setHealth(((HealthChange) object).getNewHealth());
                        }

                        else {
                            ClientConnection.getInstance().getActiveAvatars().get(((HealthChange) object).getReceivingAvatar())
                                    .setHealth(((HealthChange) object).getNewHealth());
                            ClientConnection.getInstance().getActiveAvatars().get(((HealthChange) object).getReceivingAvatar())
                                    .setHurt(true);
                        }
                    ClientConnection.getInstance().getActiveAvatars().get(((HealthChange) object).getReceivingAvatar())
                            .setHealth(((HealthChange) object).getNewHealth());
                    }

                    if (object instanceof Position) {

                        if (((Position) object).getType() == 1) {
                            ClientConnection.getInstance().getActiveAvatars()
                                    .get(((Position) object).getId())
                                    .setPosition(((Position) object).getX(), ((Position) object).getY());
                        } else if (((Position) object).getType() == 2) {
                            ClientConnection.getInstance().getActiveMonsters()
                                    .get(((Position) object).getId())
                                    .setPosition(((Position) object).getX(), ((Position) object).getY());
                        }
                    }

                    if (object instanceof UnitDeath) {
                        if (((UnitDeath) object).getUnit().equals("monster")) {
                            ClientConnection.getInstance().getUser().getAvatar().setMarkedUnit(-1);
                            ClientConnection.getInstance().getActiveMonsters().remove(((UnitDeath) object).getId());
                        }
                    }

                }
            }
        });
    }
}
