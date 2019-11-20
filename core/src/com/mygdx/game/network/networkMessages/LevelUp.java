package com.mygdx.game.network.networkMessages;

public class LevelUp {
    private int avatarId;
    private int level;
    private int maxHealth;
    private int xp;
    private int strength;
    private int intelligence;
    private int dexterity;

    public LevelUp() { }

    public int getLevel() {
        return level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getXp() {
        return xp;
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

    public int getAvatarId() {
        return avatarId;
    }
}
