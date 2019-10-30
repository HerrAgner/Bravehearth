package network;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import game.GameServer;
import handlers.CommandHandler;

public class NetworkListener {

    public NetworkListener(Server server) {
        CommandHandler ch = new CommandHandler();
        server.addListener(new com.esotericsoftware.kryonet.Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof String) {
                    // Send to commandhandler
                }
            }
        });

    }
}
