package handlers;

import com.esotericsoftware.kryonet.Connection;
import enums.Command;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandHandler {
    private BlockingQueue<String> commandQueue;
    private MovementHandler movementHandler;
    private Connection connection;

    public CommandHandler() {
        commandQueue = new LinkedBlockingQueue<>();
        movementHandler = new MovementHandler();
    }

    public void addToQueue(String command, Connection connection) {
        this.connection = connection;
        commandQueue.add(command);
        checkCommandInputType();
    }

    private void checkCommandInputType() {
        while (!commandQueue.isEmpty()) {
            try {
                String comm = commandQueue.take();
                if(comm.equals("W") || comm.equals("A") || comm.equals("S") || comm.equals("D")) {
                    movementHandler.addToMovementQueue(comm.toUpperCase(), connection);
                } else {
                    handleCommand(comm.toUpperCase());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleCommand(String command) {
        switch(Command.valueOf(command)) {
            //add logic for commands later
            case ATTACK:
                System.out.println("attack");
                break;
            case SPELL:
                System.out.println("spell");
                break;
            case BACKPACK:
                System.out.println("backpack");
                break;
            case MAP:
                System.out.println("map");
                break;
        }
    }
}