package com.mygdx.game.entities;

import com.badlogic.gdx.math.Circle;

public class Player {
    private float x;
    private float y;

    private Circle bounds;

    public Player() {
        bounds = new Circle(x, y, 0.4f);
    }
}
