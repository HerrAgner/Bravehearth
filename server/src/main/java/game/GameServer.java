package game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import network.networkMessages.*;
import handlers.ActiveUserHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameServer {

    private Server server;
    private ActiveUserHandler userHandler = new ActiveUserHandler();

    private GameServer() {
        server = new Server();
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("here now");
        System.out.println(userHandler.getActiveUsers());
        registerClasses();
    }

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
        kryo.register(Player.class);
        kryo.register(Login.class);
        kryo.register(CharacterClass.class);
    }
}
