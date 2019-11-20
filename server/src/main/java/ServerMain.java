import database.DBQueries;
import game.GameServer;
import network.NetworkListener;
import network.networkMessages.items.Weapon;
import network.networkMessages.items.Wearable;

import java.util.Random;

public class ServerMain {
    public static void main(String[] args) {
        GameServer.getInstance();
        NetworkListener nl = new NetworkListener();
    }
}
