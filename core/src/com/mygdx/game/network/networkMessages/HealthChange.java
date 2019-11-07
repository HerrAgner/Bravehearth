package com.mygdx.game.network.networkMessages;

public class HealthChange {

    private int newHealth;
    private int receivingAvatar;
    private int dispatchingAvatar;

    public HealthChange(){ }

    public HealthChange(int newHealth, int receivingAvatar, int dispatchingAvatar) {
        this.newHealth = newHealth;
        this.receivingAvatar = receivingAvatar;
        this.dispatchingAvatar = dispatchingAvatar;
    }

    public int getNewHealth() {
        return newHealth;
    }

    public int getReceivingAvatar() {
        return receivingAvatar;
    }

    public int getDispatchingAvatar() {
        return dispatchingAvatar;
    }
}
