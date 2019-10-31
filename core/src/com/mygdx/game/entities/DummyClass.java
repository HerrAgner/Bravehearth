package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class DummyClass extends Player {
    private final float BOUNDS_RADIUS = 0.4f;
    private final float SIZE = BOUNDS_RADIUS * 2;
    private Circle bounds;

    public DummyClass(String name){
        super(name);
        bounds = new Circle(super.getX(), super.getY(), BOUNDS_RADIUS);
    }

    public DummyClass(Player player) {
    }


    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(bounds.x, bounds.y, bounds.radius, 30);
    }


    private void updateBounds() {
        bounds.setPosition(super.getX(), super.getY());
    }

    @Override
    public void update() {
        super.update();
        updateBounds();
    }
}
