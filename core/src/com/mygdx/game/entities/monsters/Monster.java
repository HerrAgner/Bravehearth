package com.mygdx.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.util.MonsterType;

import java.util.UUID;

public class Monster {
    private float maxXspeed = 2f;
    private float maxYspeed = 2f;

    private float boundsRadius;
    private float size;
    UUID id;

    private float x;
    private float y;

    private int hp;
    private int maxHp;
    private int attackDamage;
    float attackSpeed;
    private String name;
    UUID markedUnit;
    int spawnRate;
    MonsterType type;

    public Monster() {
        this(3, 1, "default");
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

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attackDamage;
    }

    public String getName() {
        return name;
    }

    public float getBoundsRadius() {
        return boundsRadius;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
