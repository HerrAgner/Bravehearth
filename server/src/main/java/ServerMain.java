import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.network.NetworkListener;
import game.GameServer;

public class ServerMain {
    public static void main(String[] args) {
        GameServer.getInstance();
        NetworkListener nl = new NetworkListener();

    }
}
