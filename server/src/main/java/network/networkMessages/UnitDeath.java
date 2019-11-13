package network.networkMessages;

import database.DBQueries;
import network.networkMessages.avatar.Avatar;

public class UnitDeath {
    int attackerId;
    int targetId;
    String unit;
    int exp;

    public UnitDeath() {
    }

    public UnitDeath(int attackerId, int targetId, String unit, int exp) {
        this.attackerId = attackerId;
        this.targetId = targetId;
        this.unit = unit;
        this.exp = exp;
    }

    public UnitDeath(Avatar avatar) { DBQueries.saveAvatarWhenDead(avatar);}
}
