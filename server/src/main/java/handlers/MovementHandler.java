package handlers;

import com.esotericsoftware.minlog.Log;
import enums.Movement;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MovementHandler {
    private BlockingQueue<String> movementQueue;

    public MovementHandler() {
        movementQueue = new LinkedBlockingQueue<>();
    }

    void addToMovementQueue(String move) {
        movementQueue.add(move);
        handleMovement();
    }

    private void handleMovement() {
        while (!movementQueue.isEmpty()) {
            try {
                String move = movementQueue.take();
                switch (Movement.getMovementFromChar(move.charAt(0))) {
                    //add logic for moves later
                    case FORWARD:
                        //check valid move - monster or player in way?
                        //send new location to sender TCP
                        System.out.println("forward");
                        break;
                    case BACKWARD:
                        System.out.println("back");
                        break;
                    case LEFT:
                        System.out.println("left");
                        break;
                    case RIGHT:
                        System.out.println("right");
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
