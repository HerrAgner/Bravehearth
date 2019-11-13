package network.networkMessages;

import database.DBQueries;
import network.networkMessages.avatar.Avatar;

public class AvatarDeath {
    public AvatarDeath() {}

    public AvatarDeath(Avatar avatar) {
        loseXP(avatar);
        emptyBackpack(avatar);
        loseGold(avatar);
        //DBQueries.saveAvatar(avatar);
    }

    private boolean loseXP(Avatar avatar) {
        avatar.setExperiencePoints(0);
        return true;
    }

    private boolean emptyBackpack(Avatar avatar) {
        avatar.getBackpack().setItems(null);
        return true;
    }

    private boolean loseGold(Avatar avatar) {
        avatar.getBackpack().setWallet(avatar.getBackpack().getWallet() / 2);
        return true;
    }
}
