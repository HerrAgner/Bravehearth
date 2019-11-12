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
                        if (((Monster) object).getType().equals(MonsterType.VIPER)) {
                            Viper v = new Viper((Monster) object);
                            ClientConnection.getInstance().getActiveMonsters().put(((Monster) object).getId(), v);
                        }
                        if (((Monster) object).getType().equals(MonsterType.PULSATINGLUMP)) {
                            PulsatingLump pl = new PulsatingLump((Monster) object);
                            ClientConnection.getInstance().getActiveMonsters().put(((Monster) object).getId(), pl);
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
                            ClientConnection.getInstance().getUser().getAvatar().setMarkedUnit(-1);
                            ClientConnection.getInstance().getActiveMonsters().remove(((UnitDeath) object).getTargetId());
                        }
                    }

                }
            }
        });
    }
}
