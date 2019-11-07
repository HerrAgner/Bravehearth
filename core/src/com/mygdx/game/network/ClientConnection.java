package com.mygdx.game.network;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.entities.*;
import com.mygdx.game.entities.Items.*;
import com.mygdx.game.entities.avatar.Avatar;
import com.mygdx.game.entities.avatar.DummyClass;
import com.mygdx.game.entities.monsters.Monster;
import com.mygdx.game.network.networkMessages.*;
import com.mygdx.game.util.AttackLoop;
import com.mygdx.game.util.CharacterClass;
import com.mygdx.game.util.MonsterType;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientConnection {
    private static ClientConnection single_instance = null;

    private Client client;
    private User user;
    private ConcurrentHashMap<UUID, Avatar> activeAvatars;
    private ConcurrentHashMap<UUID, Monster> activeMonsters;
    public AssetManager assetManager = new AssetManager();


    private ClientConnection() {
        activeAvatars = new ConcurrentHashMap<>();
        client = new Client();
        activeMonsters = new ConcurrentHashMap<>();
        registerClasses();
        addAssets();
        client.start();
        try {
            client.connect(5000, "localhost", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new AttackLoop()).start();

    }
    public static ClientConnection getInstance()
    {
        if (single_instance == null)
            single_instance = new ClientConnection();

        return single_instance;
    }

    public void addActiveAvatar(Avatar avatar) {
        if (avatar.getCharacterClass().equals(CharacterClass.DUMMYCLASS)){
            activeAvatars.put(avatar.getId(), new DummyClass(avatar));
        }
    }

    private void addAssets(){
        assetManager.load("pik.png", Texture.class);
        assetManager.load("monsters/microbat.png", Texture.class);

        assetManager.finishLoading();
    }

    public ConcurrentHashMap<UUID, Avatar> getActiveAvatars() {
        return activeAvatars;
    }

    public ConcurrentHashMap<UUID, Monster> getActiveMonsters() {
        return activeMonsters;
    }

    public User getUser() { return user; }

    public Client getClient() {
        return this.client;
    }

    public void setUser(User user) { this.user = user; }

    public Avatar getAvatar() { return user.getAvatar(); }



    public void login(String username, String password){
        client.sendTCP(new Login(username, password));
    }

    private void registerClasses(){
        Kryo kryo = client.getKryo();
        kryo.register(HealthChange.class);
        kryo.register(Position.class);
        kryo.register(Avatar.class);
        kryo.register(Login.class);
        kryo.register(CharacterClass.class);
        kryo.register(User.class);
        kryo.register(MovementCommands.class);
        kryo.register(UUID.class, new UUIDSerializer());
        kryo.register(Logout.class);
        kryo.register(AttackEnemyTarget.class);
        kryo.register(Consumable.class);
        kryo.register(Item.class);
        kryo.register(Weapon.class);
        kryo.register(WeaponType.class);
        kryo.register(Wearable.class);
        kryo.register(WearableType.class);
        kryo.register(Backpack.class);
        kryo.register(EquippedItems.class);
        kryo.register(Monster.class);
        kryo.register(MonsterType.class);
    }
}
