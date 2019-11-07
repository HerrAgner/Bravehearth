package game;

import enums.Movement;
import handlers.AttackHandler;
import handlers.MovementHandler;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.Position;


public class GameLoop implements Runnable {
    private boolean running;

    public GameLoop() {
        this.running = true;
    }

    @Override
    public void run() {
        long prevtime = System.currentTimeMillis();

        while (running) {
            long time = System.currentTimeMillis();
            float delta = (float) ((time - prevtime) / 1000.0);

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
                prevtime = time;
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public Position updatePosition(Avatar avatar, Movement movement, float delta) {
        Position position;
        boolean moved = false;
        switch (movement) {
            case FORWARD:
                position = new Position(avatar.getX(), avatar.getY() + avatar.getMaxYspeed() * (delta*30), avatar.getId());
                break;
            case BACKWARD:
                position = new Position(avatar.getX(), avatar.getY() - avatar.getMaxYspeed() * (delta*30), avatar.getId());
                break;
            case LEFT:
                position = new Position(avatar.getX() - avatar.getMaxXspeed() * (delta*30), avatar.getY(), avatar.getId());
                break;
            case RIGHT:
                position = new Position(avatar.getX() + avatar.getMaxXspeed() * (delta*30), avatar.getY(), avatar.getId());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + movement);
        }

        if (GameServer.getInstance().getMapReader().getMapCollision()
                .get((int) Math.ceil(position.getX()))
                .contains((int) Math.ceil(position.getY()))
                ||
                GameServer.getInstance().getMapReader().getMapCollision()
                        .get((int) Math.ceil(position.getX() - 1))
                        .contains((int) Math.ceil(position.getY()))
                ||
                GameServer.getInstance().getMapReader().getMapCollision()
                        .get((int) Math.ceil(position.getX() - 1))
                        .contains((int) Math.ceil(position.getY() - 1))
                ||
                GameServer.getInstance().getMapReader().getMapCollision()
                        .get((int) Math.ceil(position.getX()))
                        .contains((int) Math.ceil(position.getY() - 1))
        ) {
            return null;
        } else {
            var ref = new Object() {
                boolean isValidMove = true;
            };
            GameServer.getInstance().aa.forEach((key, value) -> {
                if (key != avatar.getId()) {
                    if (Math.max(value.getX(), position.getX()) - Math.min(value.getX(), position.getX()) < 1 &&
                            Math.max(value.getY(), position.getY()) - Math.min(value.getY(), position.getY()) < 1 ) {
                        ref.isValidMove = false;
                    }
                }
            });
            if (ref.isValidMove) {
                return position;
            } else {
                return null;
            }

        }
    }
}
