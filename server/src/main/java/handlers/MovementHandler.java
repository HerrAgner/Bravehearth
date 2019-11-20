package handlers;

import com.esotericsoftware.kryonet.Connection;
import enums.Movement;
import network.Sender;
import network.networkMessages.MovementCommands;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MovementHandler {
    private Sender sender;
    public static ConcurrentHashMap<Connection, CopyOnWriteArrayList<Movement>> movementLoopList = new ConcurrentHashMap<>();
    private LinkedHashMap<Connection, MovementCommands> movementQueue;
    private boolean running;

    public MovementHandler() {
        sender = new Sender();
        movementQueue = new LinkedHashMap<>();
    }

    public void addToMovementQueue(MovementCommands mc, Connection connection) {
        movementQueue.put(connection, mc);
        handleMovement();
    }

    private void handleMovement() {
        movementQueue.entrySet().removeIf(entry ->
                handleMovementLoopList(
                        entry.getKey(),
                        entry.getValue(),
                        Movement.getMovementFromChar(entry.getValue().getLetter().charAt(0))
                )
        );
    }

    private boolean handleMovementLoopList(Connection connection, MovementCommands mc, Movement movement) {
        CopyOnWriteArrayList<Movement> list = new CopyOnWriteArrayList<>();
        if (mc.isKeyPressed()) {
            if (movementLoopList.containsKey(connection)){
                movementLoopList.get(connection).add(movement);
                return true;
            } else {
                list.add(movement);
                movementLoopList.put(connection, list);
                return true;
            }
        } else {
            if (movementLoopList.get(connection) != null) {
                movementLoopList.get(connection).remove(movement);
                if (movementLoopList.get(connection).size() == 0) {
                    movementLoopList.remove(connection);
                    return true;
                }
            }
        }
        return false;
    }
}
