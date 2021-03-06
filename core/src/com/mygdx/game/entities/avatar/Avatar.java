package com.mygdx.game.entities.avatar;

import com.mygdx.game.config.GameConfig;
import com.mygdx.game.entities.Backpack;
import com.mygdx.game.entities.EquippedItems;
import com.mygdx.game.util.CharacterClass;

public class Avatar {
    private float maxXspeed = 0.5f;
    private float maxYspeed = 0.5f;

    private float boundsRadius;
    private float size;

    private float x;
    private float y;

    private int id;
    private CharacterClass characterClass;
    private String name;
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int level;
    private int experiencePoints;

    private Backpack backpack;
    private EquippedItems equippedItems;

    private int attackDamage;
    private float attackSpeed;
    private float attackRange;
    private float defence;
    private int markedUnit;
	private boolean isHurt;
	private float hpRegen;
    private float attackLoop;
	private String direction;
	private String isAttacking = "";
	private float[] targetPosition;
    private boolean isDead = false;

    public Avatar(String name) {
        this.name = name;

        boundsRadius = 0.4f;
        size = boundsRadius * 2;
    }

    public Avatar(Avatar avatar) {
        this.maxXspeed = avatar.maxXspeed;
        this.maxYspeed = avatar.maxYspeed;
        this.boundsRadius = avatar.boundsRadius;
        this.size = avatar.size;
        this.x = avatar.x;
        this.y = avatar.y;
        this.name = avatar.name;
        this.health = avatar.health;
        this.maxHealth = avatar.maxHealth;
        this.mana = avatar.mana;
        this.attackDamage = avatar.attackDamage;
        this.attackSpeed = avatar.attackSpeed;
        this.attackRange = avatar.attackRange;
        this.id = avatar.id;
        this.characterClass = avatar.characterClass;
        this.markedUnit = avatar.markedUnit;
        this.maxMana = avatar.maxMana;
        this.strength = avatar.strength;
        this.dexterity = avatar.dexterity;
        this.intelligence = avatar.intelligence;
        this.level = avatar.level;
        this.experiencePoints = avatar.experiencePoints;
        this.backpack = avatar.backpack;
        this.equippedItems = avatar.equippedItems;
        this.defence = avatar.defence;
        this.isHurt = avatar.isHurt;
        this.direction = avatar.direction;
    }

    public Avatar() {
        this.name = "dummy";
    }


    public Avatar(float maxXspeed, float MAX_Y_SPEED, float x, float y, String name, int health, int mana, CharacterClass characterClass) {
        this.maxXspeed = maxXspeed;
        this.maxYspeed = MAX_Y_SPEED;
        this.x = x;
        this.y = y;
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.characterClass = characterClass;
    }

    public void setPosition(float x, float y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public String isAttacking() {
        return isAttacking;
    }

    public void setAttacking(String attacking) {
        isAttacking = attacking;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return id; }

    public void update(float delta) { }

    public void validMovement(float x, float y) {
        if (x < 0 + size / 2) {
            this.x = 0 + size / 2;
        }
        if (x > GameConfig.WORLD_WIDTH - size / 2) {
            this.x = GameConfig.WORLD_WIDTH - size / 2;
        }
        if (y < 0 + size / 2) {
            this.y = 0 + size / 2;
        }
        if (y > GameConfig.WORLD_HEIGHT - size / 2) {
            this.y = GameConfig.WORLD_HEIGHT - size / 2;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isHurt() {
        return isHurt;
    }

    public void setHurt(boolean hurt) {
        isHurt = hurt;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMarkedUnit() { return markedUnit; }

    public void setMarkedUnit(int markedUnit) {
        this.markedUnit = markedUnit;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public String getName() {
        return name;
    }

    public float getSize() {
        return size;
    }

    public void setBoundsRadius(float boundsRadius) {
        this.boundsRadius = boundsRadius;
        this.size = boundsRadius * 2;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public void setMaxXspeed(float maxXspeed) {
        this.maxXspeed = maxXspeed;
    }

    public String getDirection() { return direction; }

    public float[] getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(float[] targetPosition) {
        this.targetPosition = targetPosition;
    }

    public int getExperiencePoints() { return experiencePoints; }

    public void setExperiencePoints(int xp) { this.experiencePoints = xp; }

    public boolean getIsDead() { return isDead; }

    public void setIsDead(boolean isDead) { this.isDead = isDead; }

    public Backpack getBackpack() {
        return backpack;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getLevel() {
        return level;
    }

    public float getDefence() {
        return defence;
    }

    public EquippedItems getEquippedItems() {
        return equippedItems;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    public void setDefence(float defence) {
        this.defence = defence;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
