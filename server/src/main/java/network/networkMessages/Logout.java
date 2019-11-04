package network.networkMessages;

import java.util.UUID;

public class Logout {
    private UUID avatar;

    public Logout() {

    }

    public UUID getAvatar() {
        return avatar;
    }

    public void setAvatar(UUID avatar) {
        this.avatar = avatar;
    }
}
