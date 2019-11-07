package game;

import enums.Movement;
import handlers.AttackHandler;
import handlers.MovementHandler;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.Position;

import java.util.Timer;
import java.util.stream.Stream;

public class GameLoop implements Runnable {
    private boolean running;

    public GameLoop() {
        this.running = true;
    }

    @Override
    public void run() {
        long prevtime = System.currentTimeMillis();

        while (running) {
            float delta = (float) ((System.currentTimeMillis() - prevtime) / 1000.0);

            MovementHandler.movementLoopList.forEach((key, value) ->
                    value.forEach(movement -> {
                        Position pos = updatePosition(GameServer.getInstance().aa.get(GameServer.getInstance().au.get(key.getID()).getAvatar().getId()), movement, delta);
                        if (pos != null) {
                            GameServer.getInstance().aa.get(GameServer.getInstance().au.get(key.getID()).getAvatar().getId()).setX(pos.getX());
                            GameServer.getInstance().aa.get(GameServer.getInstance().au.get(key.getID()).getAvatar().getId()).setY(pos.getY());
                            GameServer.getInstance().getServer().sendToAllUDP(pos);
                        }
                    }));

            if (AttackHandler.validatedAttacks.size() > 0) {
                try {
                    GameServer.getInstance().getServer().sendToAllTCP(AttackHandler.validatedAttacks.take());
                } catch (InterruptedException e) {
                    System.out.println("Could not send attack. Trying again.");
                }

            }
            try {
                prevtime = System.currentTimeMillis();
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public Position updatePosition(Avatar avatar, Movement movement, float delta) {
        Position position;
        boolean moved = false;
        boolean isValid = true;
        switch (movement) {
            case FORWARD:
                position = new Position(avatar.getX(), avatar.getY() + avatar.getMaxYspeed() * delta, avatar.getId());
                break;
            case BACKWARD:
                position = new Position(avatar.getX(), avatar.getY() - avatar.getMaxYspeed() * delta, avatar.getId());
                break;
            case LEFT:
                position = new Position(avatar.getX() - avatar.getMaxXspeed() * delta, avatar.getY(), avatar.getId());
                break;
            case RIGHT:
                position = new Position(avatar.getX() + avatar.getMaxXspeed() * delta, avatar.getY(), avatar.getId());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + movement);
        }

        if (GameServer.getInstance().getMapReader().getMapCollision().get(position.getX().intValue()).contains(position.getY().intValue())) {
            return null;
        } else {
            var ref = new Object() {
                boolean apa = true;
            };
            GameServer.getInstance().aa.forEach((key, value) -> {
                if (key != avatar.getId()) {
                    if (value.getX() < position.getX() + 1 && value.getX() < position.getX() - 1 ) {
                        System.out.println("Valid");
                        ref.apa = true;
                    } else {
                        System.out.println("Invalid as fuck");
                        ref.apa = false;
                    }
                }
            });
            if (ref.apa) {
                return position;
            } else {
                return null;
            }

        }
    }
}
