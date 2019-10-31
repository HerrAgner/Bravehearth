package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.util.CharacterClass;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.util.InputHandler;

public class Avatar {
    private float MAX_X_SPEED = 2f;
    private float MAX_Y_SPEED = 2f;

    private float x;
    private float y;
    private InputHandler inputHandler;

    private String name;
    private int health;
    private int mana;
    private CharacterClass characterClass;

    public Avatar(String name) {
        this.name = name;
        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);

    }

    public Avatar() {
        this.name = "dummy";
    }


    public Avatar(float MAX_X_SPEED, float MAX_Y_SPEED, float x, float y, String name, int health, int mana, CharacterClass cc) {
        this.MAX_X_SPEED = MAX_X_SPEED;
        this.MAX_Y_SPEED = MAX_Y_SPEED;
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

    public void update(float delta) {
        float xSpeed = 0;
        float ySpeed = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.S)) {

        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                xSpeed = MAX_X_SPEED;
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                xSpeed = -MAX_X_SPEED;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                ySpeed = MAX_Y_SPEED;
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                ySpeed = -MAX_Y_SPEED;
            }
        }

        x += xSpeed * delta ;
        y += ySpeed * delta ;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public String getName() {
        return name;
    }
}
