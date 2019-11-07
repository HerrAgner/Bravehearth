package network.networkMessages.avatar;

import database.Column;
import network.networkMessages.CharacterClass;
import java.util.UUID;

public class Avatar {
    private float maxXspeed = 1f;
    private float maxYspeed = 1f;

    private float boundsRadius;
    private float size;
    private CharacterClass characterClass;

    @Column
    private float x;
    @Column
    private float y;
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private int health;
    @Column
    private int maxHealth;
    @Column
    private int mana;
    @Column
    private int maxMana;
    @Column
    private int strength;
    @Column
    private int dexterity;
    @Column
    private int intelligence;
    @Column
    private int level;
    @Column
    private int experiencePoints;

    private Backpack backpack;
    private EquippedItems equippedItems;

    @Column
    private int attackDamage;
    @Column
    private float attackSpeed;
    @Column
    private float attackRange;
    @Column
    private float defence;
    private UUID markedUnit;


    public Avatar() {}

    public Avatar(String name) {
        this.name = name;
    }

    public Avatar(float maxXspeed, float maxYspeed, float x, float y, String name, int health, int mana, CharacterClass cc) {
        this.maxXspeed = maxXspeed;
        this.maxYspeed = maxYspeed;
        this.x = x;
        this.y = y;
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.characterClass = cc;
    }

    public float getMaxXspeed() {
        return maxXspeed;
    }

    public void setMaxXspeed(float maxXspeed) {
        this.maxXspeed = maxXspeed;
    }

    public float getMaxYspeed() {
        return maxYspeed;
    }

    public void setMaxYspeed(float maxYspeed) {
        this.maxYspeed = maxYspeed;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public UUID getMarkedUnit() {
        return markedUnit;
    }

    public void setMarkedUnit(UUID markedUnit) {
        this.markedUnit = markedUnit;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public Backpack getBackpack() { return backpack; }

    public void setBackpack(Backpack backpack) { this.backpack = backpack; }

    public EquippedItems getEquippedItems() { return equippedItems; }

    public void setEquippedItems(EquippedItems equippedItems) { this.equippedItems = equippedItems; }
}
