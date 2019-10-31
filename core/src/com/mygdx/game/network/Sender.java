package com.mygdx.game.network;

import com.badlogic.gdx.Input;
import com.mygdx.game.network.networkMessages.MovementCommands;

public class Sender {

    public void sendInputPressed(int keyCode) {
        switch (keyCode) {
            case Input.Keys.W:
                ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(true, "W"));
                break;
            case Input.Keys.S:
                ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(true, "S"));
                break;
            case Input.Keys.A:
                ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(true, "A"));
                break;
            case Input.Keys.D:
                ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(true, "D"));
                break;
            default:
                break;
        }
    }

    public void sendInputReleased (int keyCode) {
        switch (keyCode) {
            case Input.Keys.W:
                ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(false, "W"));
                break;
            case Input.Keys.S:
                ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(false, "S"));
                break;
            case Input.Keys.A:
                ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(false, "A"));
                break;
            case Input.Keys.D:
                ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(false, "D"));
                break;
            default:
                break;
        }
    }
}
