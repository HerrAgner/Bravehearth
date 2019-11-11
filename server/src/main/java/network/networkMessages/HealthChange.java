package network.networkMessages;

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
}
