package com.mygdx.game.util;

import com.mygdx.game.entities.User;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.network.Sender;

public class AttackLoop implements Runnable {
    private boolean running;


    public AttackLoop() {
        this.running = true;
    }


    @Override
    public void run() {
        while(running) {
            if (!ClientConnection.getInstance().getClient().isConnected()) {
                this.running = false;
                break;
            }
            if (ClientConnection.getInstance().getUser() != null) {
                if (ClientConnection.getInstance().getUser().getAvatar().getMarkedUnit() != null) {
                    new Sender().targetEnemy(ClientConnection.getInstance().getUser().getAvatar().getId(), ClientConnection.getInstance().getUser().getAvatar().getMarkedUnit());
                }
                try {
                    Thread.sleep((long) ClientConnection.getInstance().getUser().getAvatar().getAttackSpeed() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
