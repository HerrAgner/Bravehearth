package com.mygdx.game.util;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

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
        position.set(x, y);
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.set(position, 0);
        camera.update();
    }

    private void setPosition(float x, float y) {
        position.set(x, y);
    }

}
