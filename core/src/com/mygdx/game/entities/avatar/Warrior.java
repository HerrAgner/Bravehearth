package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Warrior extends Avatar {
    private Sprite sprite;
    private Sound hurtSound;


    public Warrior(Avatar avatar) {
        super(avatar);

        hurtSound = Gdx.audio.newSound(Gdx.files.internal("audio/punch.mp3"));
    }

    public Warrior() {
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        getSprite();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void playSound() {
        hurtSound.play();
    }

}
