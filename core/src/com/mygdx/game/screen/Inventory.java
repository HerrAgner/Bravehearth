package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class Inventory {
    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private Image image;
    private Table table;
    private boolean isOpen;
    private ArrayList<Image> images;

    public Inventory() {
        stage = new Stage();
        table = new Table();
        skin = new Skin();
        atlas = new TextureAtlas(Gdx.files.internal("hud.atlas"));
        skin.addRegions(atlas);
        images = new ArrayList<>();
        isOpen = false;
        for (int i = 0; i <30; i++){
            if(i % 10 == 0){
                table.row();
            }
                Image image = new Image();
                image.setDrawable(skin, "hudboxmiddlegray");
                images.add(image);
                image.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y){
                        System.out.println();
                    }
                });
                table.add(image).width(60).height(60);
            }
            table.row();
        table.setFillParent(true);
        stage.addActor(table);
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
