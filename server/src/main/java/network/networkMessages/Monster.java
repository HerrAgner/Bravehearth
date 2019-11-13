package network.networkMessages;

import database.Column;

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

    private float attackTimer;
    private String isAttacking;

    public Monster() {
        this(3, 1, "default");
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

    public void setExperiencePoints(int experiencePoints) {
        this.xp = experiencePoints;
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

    public float getBoundsRadius() {
        return boundsRadius;
    }

    public void setBoundsRadius(float boundsRadius) {
        this.boundsRadius = boundsRadius;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
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

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
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

    public float getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarkedUnit() {
        return markedUnit;
    }

    public void setMarkedUnit(int markedUnit) {
        this.markedUnit = markedUnit;
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    public void setSpawnRate(int spawnRate) {
        this.spawnRate = spawnRate;
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

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getAttackTimer() {
        return attackTimer;
    }

    public void setAttackTimer(float attackTimer) {
        this.attackTimer = attackTimer;
    }

    public String getIsAttacking() {
        return isAttacking;
    }

    public void setIsAttacking(String isAttacking) {
        this.isAttacking = isAttacking;
    }

    public int getGold() {
        return gold;
    }
}
