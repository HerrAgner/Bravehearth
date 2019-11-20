package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.network.ClientConnection;


public class Warrior extends Avatar {
    private Sprite sprite;
    private Sound hurtSound;
    private boolean showAttackAnimation;
    private Animation<TextureRegion> front;
    private Animation<TextureRegion> back;
    private Animation<TextureRegion> left_side;
    private Animation<TextureRegion> right_side;
    TextureAtlas atlas = ClientConnection.getInstance().assetManager.get("avatars/avatarSprites.txt");

    public Warrior(Avatar avatar) {
        super(avatar);
        sprite = new Sprite();
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("audio/punch.mp3"));
        initAnimation();
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

    private void initAnimation(){
        front = new Animation<TextureRegion>(0.5f, atlas.findRegions("warrior_front"), Animation.PlayMode.LOOP);
        back = new Animation<TextureRegion>(0.5f, atlas.findRegions("warrior_back"), Animation.PlayMode.LOOP);
        left_side = new Animation<TextureRegion>(0.5f, atlas.findRegions("warrior_left_side"), Animation.PlayMode.LOOP);
        right_side = new Animation<TextureRegion>(0.5f, atlas.findRegions("warrior_right_side"), Animation.PlayMode.LOOP);
    }

    public Animation<TextureRegion> getAnimation(){
        switch (super.getDirection()) {
            case "front":
                return front;
            case "back":
                return back;
            case "left_side":
                return left_side;
            case "right_side":
                return right_side;
        }
        return front;
    }

}
