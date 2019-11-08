package com.mygdx.game.entities.avatar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.network.ClientConnection;

public class Marksman extends Avatar {
    private Sprite texture = new Sprite((Texture) ClientConnection.getInstance().assetManager.get("pik.png"));


    public Marksman(Avatar avatar) {
        super(avatar);
    }

    public Marksman(){

    }

    public Sprite getSprite() {
        return texture;
    }

}
