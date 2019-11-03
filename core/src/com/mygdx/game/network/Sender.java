package com.mygdx.game.network;

import com.badlogic.gdx.Input;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.network.networkMessages.AttackEnemyTarget;
import com.mygdx.game.network.networkMessages.MovementCommands;

import java.util.UUID;

public class Sender {

    Client c = ClientConnection.getInstance().getClient();

    public Sender() {
    }

    public void sendInputPressed(int keyCode) {
        switch (keyCode) {
            case Input.Keys.W:
                c.sendTCP(new MovementCommands(true, "W"));
                break;
            case Input.Keys.S:
                c.sendTCP(new MovementCommands(true, "S"));
                break;
            case Input.Keys.A:
                c.sendTCP(new MovementCommands(true, "A"));
                break;
            case Input.Keys.D:
                c.sendTCP(new MovementCommands(true, "D"));
                break;
            default:
                break;
        }
    }

    public void sendInputReleased (int keyCode) {
        switch (keyCode) {
            case Input.Keys.W:
                c.sendTCP(new MovementCommands(false, "W"));
                break;
            case Input.Keys.S:
                c.sendTCP(new MovementCommands(false, "S"));
                break;
            case Input.Keys.A:
                c.sendTCP(new MovementCommands(false, "A"));
                break;
            case Input.Keys.D:
                c.sendTCP(new MovementCommands(false, "D"));
                break;
            default:
                break;
        }
    }

    public void targetEnemy(UUID attacker, UUID target){
        c.sendTCP(new AttackEnemyTarget(attacker, target));
    }
}
