package game;

import enums.Movement;
import network.networkMessages.Avatar;
import network.networkMessages.Position;

public class GameLogic {
    public Position updatePosition(Avatar avatar, Movement movement) {
        Position position;
        System.out.println(movement);
        switch (movement) {
            case FORWARD:
                position = new Position(avatar.getX(), avatar.getY() + avatar.getMAX_Y_SPEED());
                System.out.println("FORWARD");
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
