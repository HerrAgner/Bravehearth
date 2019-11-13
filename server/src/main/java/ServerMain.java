import database.DBQueries;
import network.NetworkListener;
import game.GameServer;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
        GameServer.getInstance();
        NetworkListener nl = new NetworkListener();
        DBQueries.getMonsterById(3);
    }
}
