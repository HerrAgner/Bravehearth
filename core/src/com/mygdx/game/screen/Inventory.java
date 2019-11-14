package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Inventory {
    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private Image image;
    private Image helmet;
    private Image armor;
    private Image weapon;
    private Image boots;
    private Viewport viewport;
    private Table table;
    private boolean isOpen;

    public Inventory() {
        stage = new Stage();
        table = new Table();
        skin = new Skin();
        atlas = new TextureAtlas(Gdx.files.internal("hud.atlas"));
        skin.addRegions(atlas);
        image = new Image();
        image.setDrawable(skin, "3x3boxes");
        image.setSize(200, 200);
        stage.addActor(image);
        isOpen = false;
    }


    public Stage getStage() {
        return stage;
    }

    public void toggleInventory() {
        isOpen = !isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
