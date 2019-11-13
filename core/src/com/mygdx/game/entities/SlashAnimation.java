package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.network.ClientConnection;

public class SlashAnimation {
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 targetPosition;
    private Vector2 tmp;
    private String targetUnitType = "";
    private int targetUnit;
    private float angle;
    private float timer;

    public SlashAnimation(float delta) {
        position = new Vector2();
        targetPosition = new Vector2();
        tmp = new Vector2();
        this.timer = delta;
        texture = new TextureRegion((Texture) ClientConnection.getInstance().assetManager.get("slash.png"));
    }

    public TextureRegion getTexture() {
        return texture;
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

    public void increaseTimer(float delta) {
        this.timer += delta;
    }

    public void setTargetPosition(Vector2 targetPosition) {
        this.targetPosition = targetPosition;
    }


    public Vector2 getTargetPosition() {
        return targetPosition;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void updateAngle() {
        if (targetUnitType.equals("monster")) {
            float s = (float) (Math.atan2(position.y - targetPosition.y, position.x - targetPosition.x) * (180 / Math.PI));
            angle = (float) (s + Math.ceil(-angle / 360) * 360);
        }
    }

    public boolean shouldDispose() {
        if (this.timer > 0.5f) {
            timer = 0;
            return true;
        } else {
            return false;
        }
    }
}
