package com.mygdx.game.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.entities.Backpack;
import com.mygdx.game.entities.EquippedItems;
import com.mygdx.game.entities.Items.*;
import com.mygdx.game.entities.User;
import com.mygdx.game.entities.avatar.Avatar;
import com.mygdx.game.entities.monsters.Monster;
import com.mygdx.game.network.networkMessages.*;
import com.mygdx.game.util.CharacterClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientConnection {
    private static ClientConnection single_instance = null;

    private Client client;
    private User user;
    private ConcurrentHashMap<Integer, Avatar> activeAvatars;
    private ConcurrentHashMap<Integer, Monster> activeMonsters;
    public AssetManager assetManager = new AssetManager();


    private ClientConnection() {
        activeAvatars = new ConcurrentHashMap<>();
        client = new Client();
        activeMonsters = new ConcurrentHashMap<>();
        registerClasses();
        addAssets();
        client.start();
        try {
            client.connect(20000, "localhost", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        new Thread(new AttackLoop()).start();

    }

    public static ClientConnection getInstance() {
        if (single_instance == null)
            single_instance = new ClientConnection();

        return single_instance;
    }

    public void addActiveAvatar(Avatar avatar) {
        activeAvatars.put(avatar.getId(), avatar);
    }

    private void addAssets() {
        //monsters
        assetManager.load("monsters/microbat.png", Texture.class);
        assetManager.load("monsters/monsterSprites.txt", TextureAtlas.class);

        //projectiles
        assetManager.load("arrow_6.png", Texture.class);

        assetManager.load("slash.png", Texture.class);

        assetManager.finishLoading();
    }

    public ConcurrentHashMap<Integer, Avatar> getActiveAvatars() {
        return activeAvatars;
    }

    public ConcurrentHashMap<Integer, Monster> getActiveMonsters() {
        return activeMonsters;
    }


    public Client getClient() {
        return this.client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Avatar getAvatar() {
        return user.getAvatar();
    }

    public void login(String username, String password) {
        client.sendTCP(new Login(username, password));
    }

    private void registerClasses() {
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
        kryo.register(Consumable.class);
        kryo.register(Item.class);
        kryo.register(Weapon.class);
        kryo.register(WeaponType.class);
        kryo.register(Wearable.class);
        kryo.register(WearableType.class);
        kryo.register(Backpack.class);
        kryo.register(EquippedItems.class);
        kryo.register(ArrayList.class);
        kryo.register(HashMap.class);
        kryo.register(Monster.class);
        kryo.register(UnitDeath.class);
        kryo.register(AttackEnemyTarget.class);
        kryo.register(float[].class);
    }
}
