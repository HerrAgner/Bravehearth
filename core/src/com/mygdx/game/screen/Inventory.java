package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.network.ClientConnection;


import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Inventory {
    private Stage stage;
    private Skin skin;
    private Skin itemSkin;
    private TextureAtlas atlas;
    private TextureAtlas itemAtlas;
    private Table table;
    private Table itemSlots;
    private boolean isOpen;
    private ArrayList<Image> images;
    private ArrayList<Image> itemSlot;

    public Inventory() {
        stage = new Stage();
        table = new Table();
        skin = new Skin();
        itemSkin = new Skin();
        itemSlots = new Table();
        itemAtlas = ClientConnection.getInstance().assetManager.get("items/items.atlas");
        itemSkin.addRegions(itemAtlas);
        atlas = new TextureAtlas(Gdx.files.internal("hud.atlas"));
        skin.addRegions(atlas);
        images = new ArrayList<>();
        itemSlot = new ArrayList<>();
        isOpen = false;
        for (int i = 0; i < 30; i++) {
            if (i % 10 == 0) {
                table.row();
                itemSlots.row();
            }
            Image slot = new Image();

            itemSlot.add(slot);
            Image image = new Image();
            image.setDrawable(skin, "hudboxmiddlegray");
            images.add(image);
            int finalI = i;
            slot.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    getBackPackSlot(finalI);

                }
            });
            table.add(image).width(60).height(60);
            itemSlots.add(slot).width(60).height(60);
        }
        table.row();
        itemSlots.row();
        table.setFillParent(true);
        itemSlots.setFillParent(true);
        stage.addActor(table);
        stage.addActor(itemSlots);
    }


    public Stage getStage() {
        return stage;
    }

    public void toggleInventory() {
        isOpen = !isOpen;
        if (isOpen){
            AtomicInteger i = new AtomicInteger();
            ClientConnection.getInstance().getUser().getAvatar().getBackpack().getItems().forEach(item -> {
                itemSlot.get(i.getAndIncrement()).setDrawable(itemSkin, item.getTexture());
            });
        }
    }

    public void getBackPackSlot(int i) {
        System.out.println(itemSlot.get(i));
    }

    public boolean isOpen() {
        return isOpen;
    }


}
