import database.Database;
import network.NetworkListener;
import game.GameServer;

public class ServerMain {
    public static void main(String[] args) {
        GameServer.getInstance();
        NetworkListener nl = new NetworkListener();
        Database db = new Database();
    }
}
