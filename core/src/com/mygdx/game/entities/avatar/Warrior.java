package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.network.ClientConnection;

public class Warrior extends Avatar{
    private Sprite sprite;


    public Warrior(Avatar avatar) {
        super(avatar);
    }

    public Warrior(){ }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        getSprite();
    }

    public Sprite getSprite() {
        return sprite;
    }

}
