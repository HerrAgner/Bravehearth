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

                if (object instanceof MovementCommands) {
                    mh.addToMovementQueue((MovementCommands) object, connection);
                }
                if (object instanceof ItemPickup) {
                    ch.addToQueue(connection, object);
                }
                if (object instanceof ItemDrop) {
                    ch.addToQueue(connection, object);
                }
                if (object instanceof EquippedItemChange) {
                    ch.addToQueue(connection, object);
                }
            }
        });

    }
}
