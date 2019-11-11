package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Marksman extends Avatar {
    private Sprite sprite;
    private Sound hurtSound;

    public Marksman(Avatar avatar) {
        super(avatar);
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("audio/MaleGrunt.mp3"));
    }

    public Marksman(){ }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void playSound() {
        hurtSound.play();
    }
}
