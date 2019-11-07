package network;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import game.GameServer;
import handlers.ActiveUserHandler;
import handlers.CommandHandler;
import handlers.MovementHandler;
import network.networkMessages.*;

public class NetworkListener {

    public NetworkListener() {
        Server server = GameServer.getInstance().getServer();
        CommandHandler ch = new CommandHandler();
        ActiveUserHandler auh = GameServer.getInstance().getAUH();

                server.addListener(new Listener() {
        MovementHandler mh = new MovementHandler();
            public void received (Connection connection, Object object) {


                if (object instanceof Login) {
                    System.out.println(((Login) object).getUsername());
                    System.out.println(((Login) object).getPassword());
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
}
