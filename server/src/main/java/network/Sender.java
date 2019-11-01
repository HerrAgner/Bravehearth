package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import game.GameServer;


public class Sender {
    private Server server;

    public Sender() {
        this.server = GameServer.getInstance().getServer();
    }

        public boolean sendToTcp(Connection connection, Object message){
        server.sendToTCP(connection.getID(), message);
        return true;
    }

    public boolean sendToAllTCP(Object message) {
        server.sendToAllTCP(message); 
        return true;
    }
}
