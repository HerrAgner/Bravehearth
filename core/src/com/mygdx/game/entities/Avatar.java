package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.util.CharacterClass;
import com.mygdx.game.util.InputHandler;

import java.util.UUID;

public class Avatar {
    private float maxXspeed = 2f;
    private float maxYspeed = 2f;

    private float boundsRadius;
    private float size;

    private float x;
    private float y;
    private InputHandler inputHandler;

    private String name;
    private int health;
    private int maxHealth;
    private int mana;
    private UUID id;
    private CharacterClass characterClass;

    public Avatar(String name) {
        this.name = name;
        inputHandler = new InputHandler();
        boundsRadius = 0.4f;
        size = boundsRadius * 2;
        Gdx.input.setInputProcessor(inputHandler);
    }

    public Avatar() {
        this.name = "dummy";
    }


    public Avatar(float maxXspeed, float MAX_Y_SPEED, float x, float y, String name, int health, int mana, CharacterClass cc) {
        this.maxXspeed = maxXspeed;
        this.maxYspeed = MAX_Y_SPEED;
        this.x = x;
        this.y = y;
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.characterClass = cc;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() { return id; }

    public void update(float delta) {
//        float xSpeed = 0;
//        float ySpeed = 0;
//        if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.S)) {
//
//        } else {
//            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//                xSpeed = maxXspeed;
//            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//                xSpeed = -maxXspeed;
//            }
//            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//                ySpeed = maxYspeed;
//            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//                ySpeed = -maxYspeed;
//            }
//        }
//
//        validMovement(x += xSpeed * delta, y += ySpeed * delta);


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

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public String getName() {
        return name;
    }

    public float getBoundsRadius() {
        return boundsRadius;
    }

    public float getSize() {
        return size;
    }

    public void setBoundsRadius(float boundsRadius) {
        this.boundsRadius = boundsRadius;
        this.size = boundsRadius * 2;
    }

    public void setMaxXspeed(float maxXspeed) {
        this.maxXspeed = maxXspeed;
    }

    public void setMaxYspeed(float maxYspeed) {
        this.maxYspeed = maxYspeed;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }
}
