package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.network.ClientConnection;

public class DummyClass extends Avatar {

    private Sprite texture = new Sprite((Texture) ClientConnection.getInstance().assetManager.get("pik.png"));

    public DummyClass(String name){
        super(name);
    }
    public DummyClass(Avatar avatar){
        super(avatar);
        super.setBoundsRadius(1);
        super.setMaxXspeed(4);
    }

    public Sprite getSprite() {
        return texture;
    }


//    public void drawDebug(ShapeRenderer shapeRenderer) {
//        shapeRenderer.circle(bounds.x, bounds.y, 16f, 30);
//    }
//
//
//    private void updateBounds() {
//        bounds.setPosition(super.getX(), super.getY());
//    }

    @Override
    public void update(float delta) {
        super.update(delta);
//        updateBounds();
    }
}
