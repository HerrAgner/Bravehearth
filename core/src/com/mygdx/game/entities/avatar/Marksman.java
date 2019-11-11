package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Marksman extends Avatar {
    private Sprite sprite;
    private String direction;

    public Marksman(Avatar avatar) {
        super(avatar);
    }

    public Marksman(){ }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public String getDirection() { return direction; }
}
