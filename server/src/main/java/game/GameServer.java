package game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import handlers.ActiveUserHandler;
import handlers.MonsterHandler;
import network.UUIDSerializer;
import network.networkMessages.*;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.avatar.Backpack;
import network.networkMessages.avatar.EquippedItems;
import network.networkMessages.items.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer {

    private Server server;
//    private GameLoop gameLoop = new GameLoop();
    public Avatar avatar;
    private ActiveUserHandler auh;
    private MonsterHandler mh;
    public HashMap<Integer, User> au;
    public ConcurrentHashMap<Integer, Avatar> aa;
    private MapReader mapReader;
    private MapReader monsterSpawnLocations;


    private GameServer() {
        auh = new ActiveUserHandler();
        server = new Server();
        mh = new MonsterHandler();
        registerClasses();
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mapReader = new MapReader();
        this.mapReader.readMap();
        addMonsterSpawners();

        new Thread(new GameLoop()).start();
        new Thread(new MonsterSpawnLoop()).start();

        this.au = getAUH().getActiveUsers();
        this.aa = getAUH().getActiveAvatars();



    }

    public ActiveUserHandler getAUH() {
        return auh;
    }

    private static GameServer single_instance = null;

    public static GameServer getInstance() {
        if (single_instance == null)
            single_instance = new GameServer();

        return single_instance;
    }

    public MapReader getMapReader() {
        return mapReader;
    }

    private void addMonsterSpawners() {
        this.monsterSpawnLocations = new MapReader("server/src/main/resources/monsterSpawner_MonsterLayer.csv", "monster");
        this.monsterSpawnLocations.readMap();
        this.monsterSpawnLocations.getMonsterSpawner().forEach((integer, integers) -> {
            this.monsterSpawnLocations.getMonsterSpawner().get(integer).forEach(integers1 -> {
                mh.addMonsterSpawner(new MonsterSpawner(integer, integers1, mh.getNewSpawnerId()));
            });
        });

    }

    public MonsterHandler getMh() {
        return mh;
    }

    public Server getServer() {
        return this.server;
    }

    private void registerClasses() {
        Kryo kryo = server.getKryo();
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
        kryo.register(ItemDrop.class);
        kryo.register(ItemPickup.class);
    }
}
