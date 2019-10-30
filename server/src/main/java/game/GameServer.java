package game;

import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.network.NetworkListener;
import com.mygdx.game.network.Sender;

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

        NetworkListener nl = new NetworkListener();
    }

    private static GameServer single_instance = null;


    // static method to create instance of Singleton class
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
