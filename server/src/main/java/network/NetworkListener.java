package network;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import game.GameServer;
import handlers.CommandHandler;
import network.networkMessages.CharacterClass;
import network.networkMessages.Login;
import network.networkMessages.Avatar;
import network.networkMessages.User;

public class NetworkListener {

    public NetworkListener() {
        Server server = GameServer.getInstance().getServer();
        CommandHandler ch = new CommandHandler();
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {

                if (object instanceof Login) {
                    server.sendToTCP(connection.getID(), createUser(object));
                    System.out.println("Finished Login");
                }

                if (object instanceof String) {
                    ch.addToQueue((String) object);
                }

            }
        });

    }
    private User createUser(Object object) {
        Login loginObject = (Login) object;
        Avatar avatar = new Avatar(loginObject.getAvatar().getName());
        avatar.setCharacterClass(CharacterClass.DUMMYCLASS);
        avatar.setX(50);
        avatar.setY(50);

        User user = new User(loginObject.getUsername(), avatar);
        return user;
    }
}
