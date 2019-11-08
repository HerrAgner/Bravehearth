package com.mygdx.game.util;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.config.GameConfig;

public class CameraController {
    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();

    public CameraController() {

    }

    public void setStartPosition(float x, float y) {
        startPosition.set(x, y);
        position.set(x, y);
    }

    public void updatePosition(float x, float y) {
        float validX = x;
        float validY = y;
        if (GameConfig.WORLD_WIDTH / 2 > x || GameConfig.WORLD_WIDTH / 2 < (x + 2) / (GameConfig.WORLD_WIDTH / 2)) {
            validX = position.x;
        }
        if (GameConfig.WORLD_HEIGHT / 2 > y || y > 192.29f) {
            validY = position.y;
        }


        position.set(validX, validY);
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.set(position, 0);
        camera.update();
    }

    private void setPosition(float x, float y) {
        position.set(x, y);
    }

}
