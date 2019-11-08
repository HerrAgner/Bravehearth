package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Marksman extends Avatar {
    private Sprite sprite;

    public Marksman(Avatar avatar) {
        super(avatar);
    }

    public Marksman(){ }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        getSprite();
    }

    public Sprite getSprite() {
        return sprite;
    }

}
