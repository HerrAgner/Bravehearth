package com.mygdx.game.network.networkMessages;

public class HealthChange {

    private int newHealth;
    private int receivingAvatar;
    private int dispatchingAvatar;
    private int type;

    public HealthChange(){ }

    public HealthChange(int newHealth, int receivingAvatar, int dispatchingAvatar, int type) {
        this.newHealth = newHealth;
        this.receivingAvatar = receivingAvatar;
        this.dispatchingAvatar = dispatchingAvatar;
        this.type = type;
    }

    public int getNewHealth() {
        return newHealth;
    }

    public int getReceivingAvatar() {
        return receivingAvatar;
    }

    public int getType() {
        return type;
    }
}
