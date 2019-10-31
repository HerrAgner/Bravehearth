package handlers;

import com.esotericsoftware.kryonet.Connection;
import network.Sender;
import enums.Movement;
import network.networkMessages.Avatar;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MovementHandler {
    //    private BlockingQueue<String> movementQueue;
    private Sender sender;
    private ExecutorService executorService;
    private ConcurrentHashMap<Avatar, Movement[]> movementLoopList;
    private LinkedHashMap<Connection, String> movementQueue;

    public MovementHandler() {
//        movementQueue = new LinkedBlockingQueue<>();
        sender = new Sender();
        executorService = Executors.newCachedThreadPool();
        movementLoopList = new ConcurrentHashMap<>();
        movementQueue = new LinkedHashMap<>();
    }

    void addToMovementQueue(String move, Connection connection) {
//        movementQueue.add(move);
        handleMovement();
        movementQueue.put(connection, move);
    }

    private void handleMovement() {
        movementQueue.entrySet().removeIf(entry -> {
            switch (Movement.getMovementFromChar(entry.getValue().charAt(0))) {
                //add logic for moves later
                case FORWARD:
                    //check valid move - monster or player in way?
                    //send new location to sender TCP
                    System.out.println("forward");
                    return true;
                case BACKWARD:
                    System.out.println("back");
                    return true;
                case LEFT:
                    System.out.println("left");
                    return true;
                case RIGHT:
                    System.out.println("right");
                    sender.sendToTcp("R");
                    return true;
            }
            if (!movementLoopList.isEmpty()) {
                movementLoop();
            }
            return false;
        });

//        while (!movementQueue.isEmpty()) {
//            //                String move = movementQueue.take();
//            movementQueue.forEach((connection, s) -> {
//                switch (Movement.getMovementFromChar(s.charAt(0))) {
//                    //add logic for moves later
//                    case FORWARD:
//                        //check valid move - monster or player in way?
//                        //send new location to sender TCP
//                        System.out.println("forward");
//                        break;
//                    case BACKWARD:
//                        System.out.println("back");
//                        break;
//                    case LEFT:
//                        System.out.println("left");
//                        break;
//                    case RIGHT:
//                        System.out.println("right");
//                        sender.sendToTcp("R");
//                        break;
//                }
//                if (!movementLoopList.isEmpty()){
//                    movementLoop();
//                }
////                movementQueue.remove(connection);
//            });
////            movementQueue.clear();

//        }
    }

    private void movementLoop() {


    }
}
