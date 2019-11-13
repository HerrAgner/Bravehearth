package network.networkMessages;

import database.DBQueries;
import network.networkMessages.avatar.Avatar;

public class AvatarDeath {
    public AvatarDeath() {}

    public AvatarDeath(Avatar avatar) {
        System.out.println("here");
        DBQueries.saveAvatarWhenDead(avatar); }
}
