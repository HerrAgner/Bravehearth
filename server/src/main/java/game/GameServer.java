package game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import network.UUIDSerializer;
import network.networkMessages.*;
import handlers.ActiveUserHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.UUID;

public class GameServer {

    private Server server;
    private GameLoop gameLoop = new GameLoop();
    public Avatar avatar;
    private ActiveUserHandler auh;
    public HashMap<Integer, User> au;
    public ConcurrentHashMap<UUID, Avatar> aa;

    private GameServer() {
        auh = new ActiveUserHandler();
        server = new Server();
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        registerClasses();
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

    public Server getServer() {
        return this.server;
    }

    private void registerClasses(){
        Kryo kryo = server.getKryo();
        kryo.register(Health.class);
        kryo.register(Position.class);
        kryo.register(Avatar.class);
        kryo.register(Login.class);
        kryo.register(CharacterClass.class);
        kryo.register(User.class);
        kryo.register(MovementCommands.class);
        kryo.register(UUID.class, new UUIDSerializer());
        kryo.register(Logout.class);
    }
}
