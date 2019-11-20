package network.networkMessages;

import network.networkMessages.avatar.Avatar;

public class AvatarStatChange {

    private float newDefence;
    private float attackRange;
    private int attackDamage;
    private float attackSpeed;

    public AvatarStatChange() {

    }

    public AvatarStatChange(Avatar avatar) {
        this.newDefence = avatar.getDefense();
        this.attackRange = avatar.getAttackRange();
        this.attackDamage = avatar.getAttackDamage();
        this.attackSpeed = avatar.getAttackSpeed();
    }
}
