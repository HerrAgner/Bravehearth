package game;

import com.esotericsoftware.kryonet.Server;
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
}
