package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.network.ClientConnection;

public class Player {
    private static final float MAX_X_SPEED = 0.25f;
    private static final float MAX_Y_SPEED = 0.25f;

    private float x;
    private float y;


    public Player() {

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        float xSpeed = 0;
        float ySpeed = 0;


        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            ClientConnection.getInstance().getClient().sendTCP("D");
            xSpeed = MAX_X_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xSpeed = -MAX_X_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ySpeed = MAX_Y_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            ySpeed = -MAX_Y_SPEED;
        }

        x += xSpeed;
        y += ySpeed;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
