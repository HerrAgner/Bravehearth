package com.mygdx.game.network.networkMessages;

import java.util.UUID;

public class HealthChange {

    private int newHealth;
    private UUID receivingAvatar;
    private UUID dispatchingAvatar;

    public HealthChange(){

    }

    public HealthChange(int newHealth, UUID receivingAvatar, UUID dispatchingAvatar) {
        this.newHealth = newHealth;
        this.receivingAvatar = receivingAvatar;
        this.dispatchingAvatar = dispatchingAvatar;
    }

    public int getNewHealth() {
        return newHealth;
    }

    public UUID getReceivingAvatar() {
        return receivingAvatar;
    }

    public UUID getDispatchingAvatar() {
        return dispatchingAvatar;
    }
}
