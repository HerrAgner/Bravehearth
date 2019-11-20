package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.network.ClientConnection;

public class Marksman extends Avatar {
    private Sprite sprite;
    private Sound hurtSound;
    private Animation<TextureRegion> front;
    private Animation<TextureRegion> back;
    private Animation<TextureRegion> left_side;
    private Animation<TextureRegion> right_side;
    TextureAtlas atlas = ClientConnection.getInstance().assetManager.get("avatars/avatarSprites.txt");

    public Marksman(Avatar avatar) {
        super(avatar);
        sprite = new Sprite();
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("audio/MaleGrunt.mp3"));
        initAnimation();
    }

    public Marksman(){ }

    public Sprite getSprite() {
        return sprite;
    }

    public void playSound() {
        hurtSound.play();
    }

    private void initAnimation(){
        front = new Animation<TextureRegion>(0.5f, atlas.findRegions("marksman_front"), Animation.PlayMode.LOOP);
        back = new Animation<TextureRegion>(0.5f, atlas.findRegions("marksman_back"), Animation.PlayMode.LOOP);
        left_side = new Animation<TextureRegion>(0.5f, atlas.findRegions("marksman_left_side"), Animation.PlayMode.LOOP);
        right_side = new Animation<TextureRegion>(0.5f, atlas.findRegions("marksman_right_side"), Animation.PlayMode.LOOP);
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
