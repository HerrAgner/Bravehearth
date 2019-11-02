package network.networkMessages;

import java.util.UUID;

public class Avatar {
    private float maxXspeed = 0.03f;
    private float maxYspeed = 0.03f;

    private float boundsRadius;
    private float size;

    private float x;
    private float y;

    private String name;
    private int health;
    private int maxHealth;
    private int mana;
    private UUID id;
    private CharacterClass characterClass;

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

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }
}
