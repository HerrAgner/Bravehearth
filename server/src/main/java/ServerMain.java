import database.DBQueries;
import game.GameServer;
import network.NetworkListener;

import java.util.Random;

public class ServerMain {
    public static void main(String[] args) {
        GameServer.getInstance();
        NetworkListener nl = new NetworkListener();
    }
}
