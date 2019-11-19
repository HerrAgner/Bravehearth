package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TiledPlayer {

    Texture texture;

    private Vector2 position = new Vector2();

    private float speed = 32f;

    public TiledPlayer() {
        this.texture = new Texture("pik.png");
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
           position.x += speed * delta;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y += speed * delta;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y -= speed * delta;
        }
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSpeed() {
        return speed;
    }
}
