package game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import database.DBQueries;
import network.UUIDSerializer;
import network.networkMessages.*;
import handlers.ActiveUserHandler;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.avatar.Backpack;
import network.networkMessages.avatar.EquippedItems;
import network.networkMessages.items.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer {

    private Server server;
    private GameLoop gameLoop = new GameLoop();
    public Avatar avatar;
    private ActiveUserHandler auh;
    public HashMap<Integer, User> au;
    public ConcurrentHashMap<Integer, Avatar> aa;
    private MapReader mapReader;


    private GameServer() {
        auh = new ActiveUserHandler();
        server = new Server();
        registerClasses();
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mapReader = new MapReader();
        this.mapReader.readMap();
        new Thread(gameLoop).start();

        this.au = getAUH().getActiveUsers();
        this.aa = getAUH().getActiveAvatars();

    }

    public ActiveUserHandler getAUH() { return auh; }

    private static GameServer single_instance = null;

    public static GameServer getInstance()
    {
        if (single_instance == null)
            single_instance = new GameServer();

        return single_instance;
    }

    public MapReader getMapReader() {
        return mapReader;
    }

    public Server getServer() {
        return this.server;
    }

    private void registerClasses(){
        Kryo kryo = server.getKryo();
        kryo.register(HealthChange.class, 1);
        kryo.register(Position.class, 2);
        kryo.register(Avatar.class, 3);
        kryo.register(Login.class, 4);
        kryo.register(CharacterClass.class, 5);
        kryo.register(User.class, 6);
        kryo.register(MovementCommands.class, 7);
        kryo.register(UUID.class, new UUIDSerializer(), 8);
        kryo.register(Logout.class, 9);
        kryo.register(AttackEnemyTarget.class, 10);
        kryo.register(Consumable.class, 11);
        kryo.register(Item.class, 12);
        kryo.register(Weapon.class, 13);
        kryo.register(WeaponType.class, 14);
        kryo.register(Wearable.class, 15);
        kryo.register(WearableType.class, 16);
        kryo.register(Backpack.class, 17);
        kryo.register(EquippedItems.class, 18);
        kryo.register(ArrayList.class,19);
        kryo.register(HashMap.class, 20);
    }
}
