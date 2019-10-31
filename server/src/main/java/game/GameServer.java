package game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import network.networkMessages.*;

import java.io.IOException;

public class GameServer {

    private Server server;

    private GameServer() {
        server = new Server();
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
