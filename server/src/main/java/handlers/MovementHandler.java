package handlers;

import com.esotericsoftware.kryonet.Connection;
import network.Sender;
import enums.Movement;
import network.networkMessages.Avatar;
import network.networkMessages.MovementCommands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MovementHandler {
    //    private BlockingQueue<String> movementQueue;
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
//            switch (Movement.getMovementFromChar(entry.getValue().getLetter().charAt(0))) {
//                //add logic for moves later
//                case FORWARD:
//                    //check valid move - monster or player in way?
//                    //send new location to sender TCP
//                    if (entry.getValue().isKeyPressed()) {
//                        if (movementLoopList.containsKey(entry.getKey())){
//                            movementLoopList.get(entry.getKey()).add(Movement.FORWARD);
//                        } else {
//                            list.add(Movement.FORWARD);
//                            movementLoopList.put(entry.getKey(), list);
//                        }
//                    } else {
//                        movementLoopList.get(entry.getKey()).remove(Movement.getMovementFromChar(entry.getValue().getLetter().charAt(0)));
//                        if (movementLoopList.get(entry.getKey()).size() == 0) {
//                            movementLoopList.remove(entry.getKey());
//                        }
//
//                    }
//                    return true;
//                case BACKWARD:
//                    if (entry.getValue().isKeyPressed()) {
//                        if (movementLoopList.containsKey(entry.getKey())){
//                            movementLoopList.get(entry.getKey()).add(Movement.BACKWARD);
//                        } else {
//                            list.add(Movement.BACKWARD);
//                            movementLoopList.put(entry.getKey(), list);
//                        }
//                    } else {
//                        movementLoopList.get(entry.getKey()).remove(Movement.getMovementFromChar(entry.getValue().getLetter().charAt(0)));
//                        if (movementLoopList.get(entry.getKey()).size() == 0) {
//                            movementLoopList.remove(entry.getKey());
//                        }
//                    }
//                    return true;
//                case LEFT:
//                    if (entry.getValue().isKeyPressed()) {
//                        if (movementLoopList.containsKey(entry.getKey())){
//                            movementLoopList.get(entry.getKey()).add(Movement.LEFT);
//                        } else {
//                            list.add(Movement.LEFT);
//                            movementLoopList.put(entry.getKey(), list);
//                        }
//                    } else {
//                        movementLoopList.get(entry.getKey()).remove(Movement.getMovementFromChar(entry.getValue().getLetter().charAt(0)));
//                        if (movementLoopList.get(entry.getKey()).size() == 0) {
//                            movementLoopList.remove(entry.getKey());
//                        }
//                    }
//                    return true;
//                case RIGHT:
//                    if (entry.getValue().isKeyPressed()) {
//                        if (movementLoopList.containsKey(entry.getKey())){
//                            movementLoopList.get(entry.getKey()).add(Movement.RIGHT);
//                        } else {
//                            list.add(Movement.RIGHT);
//                            movementLoopList.put(entry.getKey(), list);
//                        }
//                    } else {
//                        movementLoopList.get(entry.getKey()).remove(Movement.getMovementFromChar(entry.getValue().getLetter().charAt(0)));
//                        if (movementLoopList.get(entry.getKey()).size() == 0) {
//                            movementLoopList.remove(entry.getKey());
//                        }
//                    }
//                    return true;
//            }
//
//            return false;


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
            movementLoopList.get(connection).remove(movement);
            if (movementLoopList.get(connection).size() == 0) {
                movementLoopList.remove(connection);
                return true;
            }
        }
        return false;
    }

}
