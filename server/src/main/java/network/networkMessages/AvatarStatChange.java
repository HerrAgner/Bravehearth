package network.networkMessages;

import network.networkMessages.avatar.Avatar;

public class AvatarStatChange {

    private float newDefence;
    private float attackRange;
    private int attackDamage;
    private float attackSpeed;
    private int maxHp;
    private int strength;
    private int intelligence;
    private int dexterity;

    public AvatarStatChange() {

    }

    public AvatarStatChange(Avatar avatar) {
        this.newDefence = avatar.getDefense();
        this.attackRange = avatar.getAttackRange();
        this.attackDamage = avatar.getAttackDamage();
        this.attackSpeed = avatar.getAttackSpeed();
        this.maxHp = avatar.getMaxHealth();
        this.strength = avatar.getStrength();
        this.intelligence = avatar.getIntelligence();
        this.dexterity = avatar.getDexterity();
    }
}
