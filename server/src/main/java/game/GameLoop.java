package game;

import enums.Movement;
import handlers.MovementHandler;
import network.networkMessages.Avatar;
import network.networkMessages.Position;

public class GameLoop implements Runnable {
    private boolean running;

    public GameLoop() {
        this.running = true;
    }

    @Override
    public void run() {

        while (running) {

            MovementHandler.movementLoopList.forEach((key, value) ->
                    value.forEach(movement -> {
                        Position pos = updatePosition(GameServer.getInstance().aa.get(GameServer.getInstance().au.get(key.getID()).getAvatar().getId()), movement);
                        GameServer.getInstance().aa.get(GameServer.getInstance().au.get(key.getID()).getAvatar().getId()).setX(pos.getX());
                        GameServer.getInstance().aa.get(GameServer.getInstance().au.get(key.getID()).getAvatar().getId()).setY(pos.getY());
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
                position = new Position(avatar.getX(), avatar.getY() + avatar.getMaxYspeed());
                System.out.println(avatar.getMaxXspeed());
                break;
            case BACKWARD:
                position = new Position(avatar.getX(), avatar.getY() - avatar.getMaxYspeed());
                break;
            case LEFT:
                position = new Position(avatar.getX() - avatar.getMaxXspeed(), avatar.getY());
                break;
            case RIGHT:
                position = new Position(avatar.getX() + avatar.getMaxXspeed(), avatar.getY());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + movement);
        }
        return position;
    }
}
