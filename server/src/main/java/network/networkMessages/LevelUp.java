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

    public LevelUp() { }

    public LevelUp(Avatar av){
        this.avatarId = av.getId();
        this.level = av.getLevel();
        this.maxHealth = av.getMaxHealth();
        this.xp = av.getExperiencePoints();
        this.strength = av.getStrength();
        this.intelligence = av.getIntelligence();
        this.dexterity = av.getDexterity();
    }
}
