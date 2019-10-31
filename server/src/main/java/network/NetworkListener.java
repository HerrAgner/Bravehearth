package network;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import game.GameServer;
import handlers.CommandHandler;
import handlers.MovementHandler;
import network.networkMessages.CharacterClass;
import network.networkMessages.Login;
import network.networkMessages.Avatar;
import network.networkMessages.MovementCommands;

public class NetworkListener {

    public NetworkListener() {
        Server server = GameServer.getInstance().getServer();
        CommandHandler ch = new CommandHandler();
        MovementHandler mh = new MovementHandler();
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {

                if (object instanceof Login) {
                    Login loginObject = (Login) object;
                    Avatar avatar = new Avatar(loginObject.getCharacter());
                    avatar.setCharacterClass(CharacterClass.DUMMYCLASS);
                    avatar.setX(10);
                    avatar.setY(20);
                    System.out.println(avatar.getX());
                    System.out.println(avatar.getY());
                    server.sendToTCP(connection.getID(), avatar);
                }

                if (object instanceof String) {
                    ch.addToQueue((String) object, connection);
                }

                if (object instanceof MovementCommands) {
                    mh.addToMovementQueue((MovementCommands) object, connection);
                }

            }
        });

    }
}
