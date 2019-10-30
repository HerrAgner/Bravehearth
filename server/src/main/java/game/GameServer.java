package game;

import com.esotericsoftware.kryonet.Server;
import network.NetworkListener;

import java.io.IOException;

public class GameServer {

    private Server server;

    public GameServer() {
        server = new Server();
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }

        NetworkListener nl = new NetworkListener(server);
    }

    public Server getServer() {
        return this.server;
    }
}
