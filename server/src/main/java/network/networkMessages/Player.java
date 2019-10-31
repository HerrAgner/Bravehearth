package network.networkMessages;

public class Player {
    private float MAX_X_SPEED = 0.25f;
    private float MAX_Y_SPEED = 0.25f;

    private float x;
    private float y;

    private String name;
    private int health;
    private int mana;
    private CharacterClass characterClass;

    public Player(String name) {
        this.name = name;
    }

    public Player(float MAX_X_SPEED, float MAX_Y_SPEED, float x, float y, String name, int health, int mana, CharacterClass cc) {
        this.MAX_X_SPEED = MAX_X_SPEED;
        this.MAX_Y_SPEED = MAX_Y_SPEED;
        this.x = x;
        this.y = y;
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.characterClass = cc;
    }

    public float getMAX_X_SPEED() {
        return MAX_X_SPEED;
    }

    public void setMAX_X_SPEED(float MAX_X_SPEED) {
        this.MAX_X_SPEED = MAX_X_SPEED;
    }

    public float getMAX_Y_SPEED() {
        return MAX_Y_SPEED;
    }

    public void setMAX_Y_SPEED(float MAX_Y_SPEED) {
        this.MAX_Y_SPEED = MAX_Y_SPEED;
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

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }
}
