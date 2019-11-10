package game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import database.DBQueries;
import handlers.MonsterHandler;
import network.UUIDSerializer;
import handlers.ActiveUserHandler;
import network.networkMessages.MonsterType;
import network.networkMessages.*;
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
    private MonsterHandler mh;
    public HashMap<Integer, User> au;
    public ConcurrentHashMap<Integer, Avatar> aa;
    private MapReader mapReader;


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
        new Thread(gameLoop).start();

        this.au = getAUH().getActiveUsers();
        this.aa = getAUH().getActiveAvatars();

        initDummyMonsters();
    }

    public ActiveUserHandler getAUH() { return auh; }

    private static GameServer single_instance = null;

    public static GameServer getInstance()
    {
        if (single_instance == null)
            single_instance = new GameServer();

        return single_instance;
    }

    private void initDummyMonsters() {
        Monster monster = new Monster(5,5,"ANTONMONSTRET");
        monster.setY(10);
        monster.setX(10);
        monster.setMaxXspeed(0.01f);
        monster.setMaxYspeed(0.01f);
        monster.setBoundsRadius(4f);
        monster.setMaxHp(5);
        monster.setType(MonsterType.DUMMYMONSTER);
        monster.setId(1);

        Monster monster2 = new Monster(5,5,"ANTONMONSTRETt");
        monster2.setY(30);
        monster2.setX(30);
        monster2.setMaxXspeed(0.01f);
        monster2.setMaxYspeed(0.01f);
        monster2.setBoundsRadius(4f);
        monster2.setMaxHp(5);
        monster2.setType(MonsterType.DUMMYMONSTER);
        monster2.setId(2);

        MonsterHandler.monsterList.put(monster.getId(), monster);
        MonsterHandler.monsterList.put(monster2.getId(), monster2);
    }

    public MapReader getMapReader() {
        return mapReader;
    }

    public MonsterHandler getMh() {
        return mh;
    }

    public Server getServer() {
        return this.server;
    }

    private void registerClasses(){
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
        kryo.register(AttackEnemyTarget.class);
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
        kryo.register(MonsterType.class);
    }
}
