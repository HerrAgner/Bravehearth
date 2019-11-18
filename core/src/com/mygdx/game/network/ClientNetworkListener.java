package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.entities.User;
import com.mygdx.game.entities.avatar.Avatar;
import com.mygdx.game.entities.avatar.Marksman;
import com.mygdx.game.entities.avatar.Sorcerer;
import com.mygdx.game.entities.avatar.Warrior;
import com.mygdx.game.entities.monsters.Monster;
import com.mygdx.game.network.networkMessages.*;
import com.mygdx.game.util.CharacterClass;

public class ClientNetworkListener {
    public ClientNetworkListener() {
        ClientConnection.getInstance().getClient().addListener(new Listener() {
            public void received(Connection connection, Object object) {

                if (object instanceof User) {
                    ClientConnection.getInstance().setUser((User) object);
                }
                if (object instanceof Avatar) {
//                    if (ClientConnection.getInstance().getActiveAvatars().contains(object)) {
//                        ClientConnection.getInstance().getActiveAvatars().remove(((Avatar) object).getId());
//                    }
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
//
                        Monster mon = new Monster((Monster) object);
                        ClientConnection.getInstance().getActiveMonsters().put(((Monster) object).getId(), mon);

                    }

                    if (object instanceof Logout) {
                        if (((Logout) object).getAvatar() == (ClientConnection.getInstance().getUser().getAvatar().getId())) {
                            ClientConnection.getInstance().getClient().stop();
                        }
                        ClientConnection.getInstance().getActiveAvatars().remove(((Logout) object).getAvatar());
                    }

                    if (object instanceof String) {
                        if (object.equals("finished")){
                            ClientConnection.getInstance().loggedIn = true;
                        }
                        System.out.println(object);
                    }

                    if (object instanceof HealthChange) {
                        if (((HealthChange) object).getType() == 1) {
                            if (ClientConnection.getInstance().getActiveMonsters().get(((HealthChange) object).getReceivingAvatar()) != null) {
                                ClientConnection.getInstance().getActiveMonsters().get(((HealthChange) object).getReceivingAvatar())
                                        .setHp(((HealthChange) object).getNewHealth());
                            }
                        } else if (((HealthChange) object).getType() == 3) {
                            ClientConnection.getInstance().getActiveAvatars().get(((HealthChange) object).getReceivingAvatar())
                                    .setHealth(((HealthChange) object).getNewHealth());
                        } else {
                            ClientConnection.getInstance().getActiveAvatars().get(((HealthChange) object).getReceivingAvatar())
                                    .setHealth(((HealthChange) object).getNewHealth());
                            ClientConnection.getInstance().getActiveAvatars().get(((HealthChange) object).getReceivingAvatar())
                                    .setHurt(true);
                        }
                    }

                    if (object instanceof AttackEnemyTarget) {
                        if (((AttackEnemyTarget) object).getHc().getType() == 1) {
                            if (ClientConnection.getInstance().getActiveMonsters().get((((AttackEnemyTarget) object).getHc()).getReceivingAvatar()) != null) {
                                ClientConnection.getInstance().getActiveMonsters().get((((AttackEnemyTarget) object).getHc()).getReceivingAvatar())
                                        .setHp((((AttackEnemyTarget) object).getHc()).getNewHealth());
                                ClientConnection.getInstance().getActiveAvatars().get(((AttackEnemyTarget) object).getAttacker())
                                        .setAttacking(((AttackEnemyTarget) object).getAttackType());
                                ClientConnection.getInstance().getActiveAvatars().get(((AttackEnemyTarget) object).getAttacker())
                                        .setTargetPosition(new float[]{ClientConnection.getInstance().getActiveMonsters().get(((AttackEnemyTarget) object).getTarget()).getX(),
                                                ClientConnection.getInstance().getActiveMonsters().get(((AttackEnemyTarget) object).getTarget()).getY()}
                                        );
                            }
                        } else if (((AttackEnemyTarget) object).getHc().getType() == 3) {
                            ClientConnection.getInstance().getActiveAvatars().get((((AttackEnemyTarget) object).getHc()).getReceivingAvatar())
                                    .setHealth((((AttackEnemyTarget) object).getHc()).getNewHealth());
                        } else {
                            ClientConnection.getInstance().getActiveAvatars().get((((AttackEnemyTarget) object).getHc()).getReceivingAvatar())
                                    .setHealth((((AttackEnemyTarget) object).getHc()).getNewHealth());
                            ClientConnection.getInstance().getActiveAvatars().get((((AttackEnemyTarget) object).getHc()).getReceivingAvatar())
                                    .setHurt(true);
                            Avatar av = ClientConnection.getInstance().getActiveAvatars().get((((AttackEnemyTarget) object).getHc()).getReceivingAvatar());
                            if (av.getId() == ClientConnection.getInstance().getAvatar().getId()) {
                                if (av instanceof Warrior) {
                                    ((Warrior) av).playSound();
                                }
                                if (av instanceof Marksman) {
                                    ((Marksman) av).playSound();
                                }
                                if (av instanceof Sorcerer) {
                                    ((Sorcerer) av).playSound();
                                }
                            }
                        }
                    }

                    if (object instanceof Position) {
                        if (((Position) object).getType() == 1) {
                            ClientConnection.getInstance().getActiveAvatars()
                                    .get(((Position) object).getId())
                                    .setPosition(((Position) object).getX(), ((Position) object).getY(), ((Position) object).getDirection());
                        } else if (((Position) object).getType() == 2) {
                            if (ClientConnection.getInstance().getActiveMonsters()
                                    .get(((Position) object).getId()) != null) {
                                ClientConnection.getInstance().getActiveMonsters()
                                        .get(((Position) object).getId())
                                        .setPosition(((Position) object).getX(), ((Position) object).getY());
                            }
                        }
                    }

                    if (object instanceof UnitDeath) {
                        if (((UnitDeath) object).getUnit().equals("monster")) {
                            if (ClientConnection.getInstance().getUser().getAvatar().getMarkedUnit() == ((UnitDeath) object).getTargetId()) {
                                ClientConnection.getInstance().getUser().getAvatar().setMarkedUnit(-1);
                            }
                            ClientConnection.getInstance().getActiveMonsters().remove(((UnitDeath) object).getTargetId());
                        }
                    }
                    if (object instanceof ItemDropClient) {
                        if (((ItemDropClient) object).getAvatarId() == ClientConnection.getInstance().getUser().getAvatar().getId()) {
                            ClientConnection.getInstance().getUser().getAvatar().getBackpack().getItems().remove(((ItemDropClient) object).getId());
                            ClientConnection.getInstance().getUser().getAvatar().getBackpack().setChanged(true);
                        }
                        ClientConnection.getInstance().getItemsOnGround().put(new Float[]{((ItemDropClient) object).getX(), ((ItemDropClient) object).getY()}, ((ItemDropClient) object).getItem());
                    }
                    if (object instanceof ItemPickup) {
                        ClientConnection.getInstance().getItemsOnGround().forEach((floats, item) -> {
                            if (floats[0] == ((ItemPickup) object).getX() && floats[1] == ((ItemPickup) object).getY() && item.getName().equals(((ItemPickup) object).getItem().getName())) {
                                ClientConnection.getInstance().getItemsOnGround().remove(floats);
                            }
                        });
                        if (((ItemPickup) object).getAvatarId() == ClientConnection.getInstance().getUser().getAvatar().getId()) {
                            ClientConnection.getInstance().getUser().getAvatar().getBackpack().getItems().add(((ItemPickup) object).getItem());
                        }
                    }

                }
            }
        });
    }
}
