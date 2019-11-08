package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.network.ClientConnection;

public class Sorcerer extends Avatar {

    private Sprite texture = new Sprite((Texture) ClientConnection.getInstance().assetManager.get("pik.png"));

    public Sorcerer(Avatar avatar) {
        super(avatar);
    }

    public Sorcerer() {

    }


    public Sprite getSprite() {
        return texture;
    }

}
