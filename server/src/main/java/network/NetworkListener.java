package network;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import game.GameServer;
import handlers.CommandHandler;
import network.networkMessages.CharacterClass;
import network.networkMessages.Login;
import network.networkMessages.Player;

public class NetworkListener {

    public NetworkListener() {
        Server server = GameServer.getInstance().getServer();
        CommandHandler ch = new CommandHandler();
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {

                if (object instanceof Login) {
                    Login loginObject = (Login) object;
                    Player player = new Player(loginObject.getCharacter());
                    player.setCharacterClass(CharacterClass.DUMMYCLASS);
                    player.setX(10);
                    player.setY(20);
                    System.out.println(player.getX());
                    System.out.println(player.getY());
                    server.sendToTCP(connection.getID(), player);
                }

                if (object instanceof String) {
                    ch.addToQueue((String) object);
                }

            }
        });

    }
}
