import game.GameServer;
import network.NetworkListener;

public class ServerMain {
    public static void main(String[] args) {
        GameServer.getInstance();
        NetworkListener nl = new NetworkListener();
    }
}
