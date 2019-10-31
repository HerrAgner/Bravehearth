package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.mygdx.game.util.InputHandler;

public class Player {
    private final float BOUNDS_RADIUS = 0.4f;
    private final float SIZE = BOUNDS_RADIUS * 2;
    private final float MAX_X_SPEED = 1.0f;
    private final float MAX_Y_SPEED = 1.0f;

    private float x;
    private float y;
    private InputHandler inputHandler;

    private Circle bounds;

    public Player() {
        bounds = new Circle(x, y, BOUNDS_RADIUS);
        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(bounds.x, bounds.y, bounds.radius, 30);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    private void updateBounds() {
        bounds.setPosition(x, y);
    }

    public void update(float delta) {

        float xSpeed = 0;
        float ySpeed = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ySpeed = MAX_Y_SPEED * delta;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            ySpeed = -MAX_Y_SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xSpeed = -MAX_X_SPEED * delta;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xSpeed = MAX_X_SPEED * delta;
        }

        x += xSpeed;
        y += ySpeed;
       /* float xSpeed = 0;
        float ySpeed = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xSpeed = MAX_X_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xSpeed = -MAX_X_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            ySpeed = MAX_Y_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            ySpeed = -MAX_Y_SPEED;
        }

        x += xSpeed;
        y += ySpeed;

        */




        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
