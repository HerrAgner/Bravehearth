package com.mygdx.game.network.networkMessages;

public class AvatarStatChange {

    private float newDefence;
    private float attackRange;
    private int attackDamage;
    private float attackSpeed;

    public AvatarStatChange() {

    }

    public float getNewDefence() {
        return newDefence;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }
}
