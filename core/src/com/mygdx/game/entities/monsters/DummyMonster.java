package com.mygdx.game.entities.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.mygdx.game.network.ClientConnection;

public class DummyMonster extends Monster {

    private Sprite sprite = new Sprite((Texture) ClientConnection.getInstance().assetManager.get("pik.png"));


    public DummyMonster() {
        this(2, 3, "dummy");
    }

    public DummyMonster(Monster monster) {
        super(monster);
    }

    public DummyMonster(int hp, int attack, String name) {
        super(hp, attack, name);
    }

    public Sprite getSprite() {
        return sprite;
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
