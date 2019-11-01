package network;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import game.GameLoop;
import game.GameServer;
import handlers.ActiveUserHandler;
import handlers.CommandHandler;
import handlers.MovementHandler;
import network.networkMessages.CharacterClass;
import network.networkMessages.Login;
import network.networkMessages.Avatar;
import network.networkMessages.User;
import network.networkMessages.MovementCommands;

import java.util.UUID;

public class NetworkListener {

    public NetworkListener() {
        Server server = GameServer.getInstance().getServer();
        CommandHandler ch = new CommandHandler();
        ActiveUserHandler auh = GameServer.getInstance().getAUH();

                server.addListener(new Listener() {
        MovementHandler mh = new MovementHandler();
            public void received (Connection connection, Object object) {

                if (object instanceof Login) {
                    User user = (createUser(object));
                    server.sendToTCP(connection.getID(), user);
                    auh.addToActiveUsers(connection.getID(), user);
                    auh.getActiveAvatars().values().forEach(avatar ->
                            GameServer.getInstance().getServer().sendToTCP(connection.getID(), avatar));
                    GameServer.getInstance().getServer().sendToAllExceptTCP(connection.getID(), user.getAvatar());
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
    private User createUser(Object object) {
        Login loginObject = (Login) object;
        Avatar avatar = new Avatar(loginObject.getAvatar().getName());
        avatar.setCharacterClass(CharacterClass.DUMMYCLASS);
        avatar.setX(10);
        avatar.setY(10);
        avatar.setId(UUID.randomUUID());

        User user = new User(loginObject.getUsername(), avatar);
        return user;
    }
}
