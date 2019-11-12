package com.mygdx.game.entities.monsters;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class DeathDrake extends Monster{
    private Sprite sprite;

    public DeathDrake(Monster monster) {
        super(monster);
    }

    public void setSprite(Sprite sprite) { this.sprite = sprite; }

    public Sprite getSprite() { return sprite; }
}
