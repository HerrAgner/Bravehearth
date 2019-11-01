package com.mygdx.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class DummyMonster extends Monster {

    private Circle bounds;

    public DummyMonster() {
        this(2, 3, "dummy");
    }

    public DummyMonster(int hp, int attack, String name) {
        super(hp, attack, name);
        bounds = new Circle(1,1, super.getBoundsRadius());
    }

    public void drawDebug(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(bounds.x, bounds.y, bounds.radius, 6);
        updateBounds();
    }

    private void updateBounds() {
        super.update(Gdx.graphics.getDeltaTime());
        bounds.setPosition(super.getX(), super.getY());
    }

}
