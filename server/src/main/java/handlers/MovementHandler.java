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
    public static ConcurrentHashMap<Connection, ArrayList<Movement>> movementLoopList = new ConcurrentHashMap<>();
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
        movementQueue.entrySet().removeIf(entry -> {
            switch (Movement.getMovementFromChar(entry.getValue().getLetter().charAt(0))) {
                //add logic for moves later
                case FORWARD:
                    //check valid move - monster or player in way?
                    //send new location to sender TCP
                    if (entry.getValue().isKeyPressed()) {
                        if (movementLoopList.containsKey(entry.getKey())){
                            movementLoopList.get(entry.getKey()).add(Movement.FORWARD);
                        } else {
                            ArrayList<Movement> list = new ArrayList<>();
                            list.add(Movement.FORWARD);
                            movementLoopList.put(entry.getKey(), list);
                        }
                    } else {
                        movementLoopList.remove(entry.getKey());
                    }
                    return true;
                case BACKWARD:
                    if (entry.getValue().isKeyPressed()) {
                        if (movementLoopList.containsKey(entry.getKey())){
                            movementLoopList.get(entry.getKey()).add(Movement.BACKWARD);
                        } else {
                            ArrayList<Movement> list = new ArrayList<>();
                            list.add(Movement.BACKWARD);
                            movementLoopList.put(entry.getKey(), list);
                        }
                    } else {
                        movementLoopList.remove(entry.getKey());
                    }
                    return true;
                case LEFT:
                    if (entry.getValue().isKeyPressed()) {
                        if (movementLoopList.containsKey(entry.getKey())){
                            movementLoopList.get(entry.getKey()).add(Movement.LEFT);
                        } else {
                            ArrayList<Movement> list = new ArrayList<>();
                            list.add(Movement.LEFT);
                            movementLoopList.put(entry.getKey(), list);
                        }
                    } else {
                        movementLoopList.remove(entry.getKey());
                    }
                    return true;
                case RIGHT:
                    if (entry.getValue().isKeyPressed()) {
                        if (movementLoopList.containsKey(entry.getKey())){
                            movementLoopList.get(entry.getKey()).add(Movement.RIGHT);
                        } else {
                            ArrayList<Movement> list = new ArrayList<>();
                            list.add(Movement.RIGHT);
                            movementLoopList.put(entry.getKey(), list);
                        }
                    } else {
                        movementLoopList.remove(entry.getKey());
                    }
                    return true;
            }

            return false;
        });

    }

}
