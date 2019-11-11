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
    private TextureRegion texture = new TextureRegion((Texture) ClientConnection.getInstance().assetManager.get("arrow_6.png"));

    public Arrow() {
        position = new Vector2();
        targetPosition = new Vector2();
        tmp = new Vector2();
        velocity = 0.7f;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void update() {
        updateTarget();
        updatePosition();
    }

    private void updateTarget() {
        if (targetUnitType.equals("monster")) {
            if (ClientConnection.getInstance().getActiveMonsters().get(targetUnit) != null) {
                targetPosition = new Vector2(ClientConnection.getInstance().getActiveMonsters().get(targetUnit).getX(), ClientConnection.getInstance().getActiveMonsters().get(targetUnit).getY());
                float s = (float) (Math.atan2(position.y - targetPosition.y, position.x - targetPosition.x) * (180 / Math.PI));
                angle = (float) (s + Math.ceil(-angle / 360) * 360);

            }
        }
    }

    private void updatePosition() {
        tmp.set(targetPosition).sub(position);
        if (!tmp.isZero()) {
            position.add(tmp.limit(velocity));
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public String getTargetUnitType() {
        return targetUnitType;
    }

    public void setTargetUnitType(String targetUnitType) {
        this.targetUnitType = targetUnitType;
    }

    public int getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(int targetUnit) {
        this.targetUnit = targetUnit;
    }

    public float getAngle() {
        return angle;
    }
}
