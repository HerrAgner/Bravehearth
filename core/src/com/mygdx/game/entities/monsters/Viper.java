package com.mygdx.game.entities.monsters;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Viper extends Monster {
    private Sprite sprite;

    public Viper(Monster monster) {
        super(monster);
    }

    public void setSprite(Sprite sprite) { this.sprite = sprite; }

    public Sprite getSprite() {
        return sprite;
    }
}
