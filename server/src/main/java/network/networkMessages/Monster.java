package network.networkMessages;

import database.Column;
import network.networkMessages.items.Item;
import java.util.ArrayList;


public class Monster {
    private float maxXspeed = 0.001f;
    private float maxYspeed = 0.001f;

    private float boundsRadius;
    private float size;
    int id;

    private float x;
    private float y;

    @Column
    private float maxSpeed;
    @Column
    private int hp;
    @Column
    private int maxHp;
    @Column
    private int attackDamage;
    @Column
    float attackSpeed;
    @Column
    float attackRange;
    @Column
    private String name;
    private int markedUnit;
    int spawnRate;
    private int spawnerId;
    @Column
    private int xp;
    @Column
    private String texture;
    @Column
    private int gold;
    private ArrayList<Item> loot;

    private float attackTimer;
    private String isAttacking;

    public Monster() {
        loot = new ArrayList<>();
    }

    public Monster(int hp, int attack, String name) {
        this.hp = hp;
        this.attackDamage = attack;
        this.name = name;
        this.boundsRadius = 1;
        this.size = boundsRadius * 2;
    }

    public int getExperiencePoints() {
        return xp;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public int getMarkedUnit() {
        return markedUnit;
    }

    public void setMarkedUnit(int markedUnit) {
        this.markedUnit = markedUnit;
    }

    public int getSpawnerId() {
        return spawnerId;
    }

    public void setSpawnerId(int spawnerId) {
        this.spawnerId = spawnerId;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getAttackTimer() {
        return attackTimer;
    }

    public void setAttackTimer(float attackTimer) {
        this.attackTimer = attackTimer;
    }

    public int getGold() {
        return gold;
    }

    public ArrayList<Item> getLoot() {
        return loot;
    }
}
