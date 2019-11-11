package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Warrior extends Avatar{
    private Sprite sprite;

    public Warrior(Avatar avatar) {
        super(avatar);
    }

    public Warrior(){ }

    public void setSprite(Sprite sprite) { this.sprite = sprite; }

    public Sprite getSprite() {
        return sprite;
    }

}
