package com.mygdx.game.entities.monsters;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class PulsatingLump extends Monster {
    private Sprite sprite;

    public PulsatingLump(Monster monster) {
        super(monster);
    }

    public void setSprite(Sprite sprite) { this.sprite = sprite; }

    public Sprite getSprite() { return sprite; }
}