package com.mygdx.game.network.networkMessages;

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

    public int getMaxHp() {
        return maxHp;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getDexterity() {
        return dexterity;
    }
}
