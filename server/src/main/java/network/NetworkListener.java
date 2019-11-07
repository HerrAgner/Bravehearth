package network;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import game.GameLoop;
import game.GameServer;
import handlers.ActiveUserHandler;
import handlers.CommandHandler;
import handlers.MovementHandler;
import network.networkMessages.*;

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
                    ch.addToQueue(connection, object);
                }

                if (object instanceof AttackEnemyTarget) {
                    ch.addToQueue(connection, object);
                }

                if (object instanceof Logout){
                    ch.addToQueue(connection, object);
                }

                if (object instanceof String) {
                    System.out.println(object);
//                    ch.addToQueue((String) object, connection);
                }

                if (object instanceof MovementCommands) {
                    mh.addToMovementQueue((MovementCommands) object, connection);
                }

            }
        });

    }
//    private User createUser(Object object) {
//        Login loginObject = (Login) object;
//        Avatar com.mygdx.game.entities.avatar = new Avatar(loginObject.getAvatar().getName());
//        com.mygdx.game.entities.avatar.setCharacterClass(CharacterClass.DUMMYCLASS);
//        com.mygdx.game.entities.avatar.setX(10);
//        com.mygdx.game.entities.avatar.setY(10);
//        com.mygdx.game.entities.avatar.setId(UUID.randomUUID());
//
//        User user = new User(loginObject.getUsername(), com.mygdx.game.entities.avatar);
//        return user;
//    }
}
