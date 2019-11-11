package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Sorcerer extends Avatar {

    private Sprite sprite;

    public Sorcerer(Avatar avatar) {
        super(avatar);
    }

    public Sorcerer() { }

    public void setSprite(Sprite sprite) { this.sprite = sprite; }

    public Sprite getSprite() {
        return sprite;
    }

}
