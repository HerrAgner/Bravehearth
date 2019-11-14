package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Warrior extends Avatar {
    private Sprite sprite;
    private Sound hurtSound;
    private boolean showAttackAnimation;

    public Warrior(Avatar avatar) {
        super(avatar);
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("audio/punch.mp3"));
    }

    public Warrior() {
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void playSound() {
        hurtSound.play();
    }

    public boolean showAttackAnimation() {
        return showAttackAnimation;
    }

    public void setShowAttackAnimation(boolean showAttackAnimations) {
        showAttackAnimation = showAttackAnimations;
    }
}
