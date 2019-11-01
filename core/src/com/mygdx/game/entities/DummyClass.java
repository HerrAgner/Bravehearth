package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class DummyClass extends Avatar {
    private Circle bounds;

    public DummyClass(String name){
        super(name);
        bounds = new Circle(super.getX(), super.getY(), super.getBoundsRadius());
    }

    public DummyClass(Avatar avatar){
        super(avatar.getName());
        setPosition(avatar.getX(), avatar.getY());
        super.setBoundsRadius(1);
        super.setMaxXspeed(4);
        bounds = new Circle(super.getX(), super.getY(), super.getBoundsRadius());
    }


    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(bounds.x, bounds.y, bounds.radius, 30);
    }


    private void updateBounds() {
        bounds.setPosition(super.getX(), super.getY());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        updateBounds();
    }
}
