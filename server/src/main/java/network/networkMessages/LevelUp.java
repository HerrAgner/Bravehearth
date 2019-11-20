package network.networkMessages;

import network.networkMessages.avatar.Avatar;

public class LevelUp {
    private int avatarId;
    private int level;
    private int maxHealth;
    private int xp;
    private int strength;
    private int intelligence;
    private int dexterity;

    public LevelUp() {
    }

    public LevelUp(Avatar av){
        this.avatarId = av.getId();
        this.level = av.getLevel();
        this.maxHealth = av.getMaxHealth();
        this.xp = av.getExperiencePoints();
        this.strength = av.getStrength();
        this.intelligence = av.getIntelligence();
        this.dexterity = av.getDexterity();
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }
}
