package com.mygdx.game.entities.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.entities.Items.Item;

import java.util.ArrayList;

public class Monster {
    private float maxXspeed;
    private float maxYspeed;

    private float boundsRadius;
    private float size;
    private int id;

    private float x;
    private float y;

    private float maxSpeed;
    private int hp;
    private int maxHp;
    private int attackDamage;
    private float attackSpeed;
    private float attackRange;
    private String name;
    private int markedUnit;
    private int spawnRate;
    private int spawnerId;
    private int xp;
    private String texture;

    private float attackTimer;
    private String isAttacking;
    private int gold;
    private ArrayList<Item> loot;

    public Monster() {
        this(3, 1, "default");
    }

    public Monster(Monster monster) {
        this.maxXspeed = monster.maxXspeed;
        this.maxYspeed = monster.maxYspeed;

        this.boundsRadius = monster.boundsRadius;
        this.size = monster.size;
        this.id = monster.id;

        this.x = monster.x;
        this.y = monster.y;

        this.hp = monster.hp;
        this.maxHp = monster.maxHp;
        this.maxSpeed = monster.maxSpeed;
        this.attackRange = monster.attackRange;
        this.texture = monster.texture;
        this.xp = monster.xp;
        this.attackDamage = monster.attackDamage;
        this.attackSpeed = monster.attackSpeed;
        this.name = monster.name;
        this.markedUnit = monster.markedUnit;
        this.spawnRate = monster.spawnRate;
        this.loot = monster.loot;
    }

    public Monster(int hp, int attack, String name) {
        this.hp = hp;
        this.attackDamage = attack;
        this.name = name;
        this.boundsRadius = 1;
        this.size = boundsRadius * 2;
        this.x = 3;
        this.y = 4;
    }

    public void update(float delta) {
        float xSpeed = 0;
        float ySpeed = 0;
        if (MathUtils.randomBoolean()) {
            xSpeed = MathUtils.random(0, 1) * 10;
            ySpeed = MathUtils.random(0, 1) * 10;
        } else {
            xSpeed = -MathUtils.random(0, 1) * 10;
            ySpeed = -MathUtils.random(0, 1) * 10;
        }
        validMovement(x += xSpeed * delta, y += ySpeed * delta);
    }


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
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
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

    public String getTexture() {
        return texture;
    }
}
