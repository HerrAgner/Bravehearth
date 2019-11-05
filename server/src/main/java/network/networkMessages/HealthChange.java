package network.networkMessages;

import java.util.UUID;

public class HealthChange {

    int newHealth;
    UUID receivingAvatar;
    UUID dispatchingAvatar;

    public HealthChange(){

    }

    public HealthChange(int newHealth, UUID receivingAvatar, UUID dispatchingAvatar) {
        this.newHealth = newHealth;
        this.receivingAvatar = receivingAvatar;
        this.dispatchingAvatar = dispatchingAvatar;
    }
}
