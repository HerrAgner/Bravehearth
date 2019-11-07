package com.mygdx.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class DummyMonster extends Monster {


    public DummyMonster() {
        this(2, 3, "dummy");
    }

    public DummyMonster(Monster monser) {
        super(monser);
    }

    public DummyMonster(int hp, int attack, String name) {
        super(hp, attack, name);
    }

//    public void drawDebug(ShapeRenderer shapeRenderer) {
//        shapeRenderer.circle(bounds.x, bounds.y, bounds.radius, 6);
//        updateBounds();
//    }
//
//    private void updateBounds() {
//        super.update(Gdx.graphics.getDeltaTime());
//        bounds.setPosition(super.getX(), super.getY());
//    }

}
