package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.network.ClientConnection;

public class Arrow {
    private Vector2 position;
    private float velocity;
    private Vector2 targetPosition;
    private Vector2 tmp;
    private String targetUnitType = "";
    private int targetUnit;
    private float angle;
    private float timer;
    private TextureRegion texture = new TextureRegion((Texture) ClientConnection.getInstance().assetManager.get("arrow_6.png"));

    public Arrow(float delta) {
        position = new Vector2();
        targetPosition = new Vector2();
        tmp = new Vector2();
        velocity = 0.7f;
        this.timer = delta;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void update(float delta) {
        updateTarget();
        updatePosition(delta);
    }

    private void updateTarget() {
        if (targetUnitType.equals("monster")) {
                float s = (float) (Math.atan2(position.y - targetPosition.y, position.x - targetPosition.x) * (180 / Math.PI));
                angle = (float) (s + Math.ceil(-angle / 360) * 360);
        }
    }

    private void updatePosition(float delta) {
        tmp.set(targetPosition).sub(position);
        if (!tmp.isZero()) {
            position.add(tmp.limit(velocity+delta));
        }
    }

    public boolean shouldDispose(float delta){
        if (position.isOnLine(targetPosition)) {
            this.timer = delta;
            return true;
        } else {
            return false;
        }
    }

    public void increaseTimer(float delta){
        this.timer += delta;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setTargetUnitType(String targetUnitType) {
        this.targetUnitType = targetUnitType;
    }

    public float getAngle() {
        return angle;
    }

    public void setTargetPosition(Vector2 targetPosition) {
        this.targetPosition = targetPosition;
    }
}
