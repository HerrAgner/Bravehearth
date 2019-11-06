package network.networkMessages;

import java.util.UUID;

public class HealthChange {

    int newHealth;
    int receivingAvatar;
    int dispatchingAvatar;

    public HealthChange(){

    }

    public HealthChange(int newHealth, int receivingAvatar, int dispatchingAvatar) {
        this.newHealth = newHealth;
        this.receivingAvatar = receivingAvatar;
        this.dispatchingAvatar = dispatchingAvatar;
    }
}
