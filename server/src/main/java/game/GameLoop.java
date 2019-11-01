package game;

import enums.Movement;
import handlers.MovementHandler;
import network.Sender;
import network.networkMessages.Avatar;
import network.networkMessages.Position;

public class GameLoop implements Runnable {
    private boolean running;
    private Sender sender;
    private GameLogic gl;

    public GameLoop() {
        this.running = true;
    }

    @Override
    public void run() {

        while (running) {
            MovementHandler.movementLoopList.forEach((key, value) ->
                    value.forEach(movement -> {
                        Position pos = updatePosition(GameServer.getInstance().avatar, movement);
                        GameServer.getInstance().avatar.setX(pos.getX());
                        GameServer.getInstance().avatar.setY(pos.getY());
                        GameServer.getInstance().getServer().sendToAllUDP(pos);
                    }));
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public Position updatePosition(Avatar avatar, Movement movement) {
        Position position;
        switch (movement) {
            case FORWARD:
                position = new Position(avatar.getX(), avatar.getY() + avatar.getMAX_Y_SPEED());
                break;
            case BACKWARD:
                position = new Position(avatar.getX(), avatar.getY() - avatar.getMAX_Y_SPEED());
                break;
            case LEFT:
                position = new Position(avatar.getX() - avatar.getMAX_X_SPEED(), avatar.getY());
                break;
            case RIGHT:
                position = new Position(avatar.getX() + avatar.getMAX_X_SPEED(), avatar.getY());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + movement);
        }
        return position;
    }
}
