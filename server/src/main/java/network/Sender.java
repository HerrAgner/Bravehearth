package network;

import com.esotericsoftware.kryonet.Server;
import game.GameServer;


public class Sender {
    private Server server;

    public Sender() {
        this.server = GameServer.getInstance().getServer();
    }

    public boolean sendToTcp(Object message){
        //server.sendToTCP(message);
        return true;
    }

    public boolean sendToAllTCP(Object message) {
        server.sendToAllTCP(message);
        return true;
    }
}
