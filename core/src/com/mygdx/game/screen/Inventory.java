package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.entities.Items.Consumable;
import com.mygdx.game.entities.Items.Item;
import com.mygdx.game.entities.Items.Weapon;
import com.mygdx.game.entities.Items.Wearable;
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
    private Dialog dialog;
    private Window window;
    private Table windowTable;
    private Skin dropSkin;
    private Window equipWindow;
    private TextButton equip;
    private TextButton drop;

    public Inventory() {
        dropSkin=new Skin(Gdx.files.internal("terra-mother/skin/terra-mother-ui.json"));
        windowTable = new Table();
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
        window = new Window("default", dropSkin);
        equipWindow = new Window("", dropSkin);
        equip = new TextButton("Equip", dropSkin, "default");
        drop = new TextButton("Drop", dropSkin, "default");
        initInventoryWindow();
    }

    private void initInventoryWindow() {
        window.setSize(200, 200);
        window.setPosition(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 200);
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
            slot.addListener(new ClickListener(-1) {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    equipWindow.clear();
                    removeDialog();
                    dropOrEquip(finalI);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    displayItemInfo(finalI);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    removeDialog();
                }


            });
            table.add(image).width(60).height(60);
            itemSlots.add(slot).width(60).height(60);
        }
        table.row();
        itemSlots.row();
        table.setFillParent(true);
        itemSlots.setFillParent(true);
        //     itemSlots.setPosition(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 200);
        stage.addActor(table);
        stage.addActor(itemSlots);
    }


    public Stage getStage() {
        return stage;
    }

    public void toggleInventory() {
        isOpen = !isOpen;
        if (isOpen) {
            AtomicInteger i = new AtomicInteger();
            ClientConnection.getInstance().getUser().getAvatar().getBackpack().getItems().forEach(item -> {
                itemSlot.get(i.getAndIncrement()).setDrawable(itemSkin, item.getTexture());
            });
        }
    }


    public boolean isOpen() {
        return isOpen;
    }

    public void dropOrEquip(int i) {
        equipWindow.setBounds(Gdx.input.getX(), Gdx.input.getY(), 75 , 100);
        equip.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent two, float x, float y){
            }
        });
        drop.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent two, float x, float y){
                ClientConnection.getInstance().getUser().getAvatar().getBackpack().getItems().remove(i);
                System.out.println(itemSlot);
                itemSlot.remove(i);
                System.out.println(itemSlot);
                equipWindow.remove();
            }
        });
        equipWindow.add(equip);
        equipWindow.row();
        equipWindow.add(drop);
        stage.addActor(equipWindow);
    }

    public void displayItemInfo(int i) {
        try {
            Item item = ClientConnection.getInstance().getUser().getAvatar().getBackpack().getItems().get(i);

            if (item instanceof Weapon) {
                windowTable.clear();
                window.getTitleLabel().setText(item.getName());
                Label dmg = new Label("Dmg: " + ((Weapon) item).getDamage(), dropSkin);
                Label as = new Label("As: " + ((Weapon) item).getSpeed(), dropSkin);
                Label type = new Label("Type: " + ((Weapon) item).getWeaponType(), dropSkin);
                windowTable.add(dmg);
                windowTable.row();
                windowTable.add(as);
                windowTable.row();
                windowTable.add(type);
                window.add(windowTable);
            }
            if (item instanceof Wearable) {
                windowTable.clear();
                window.getTitleLabel().setText(item.getName());
                Label defence = new Label("Def: " + ((Wearable) item).getDefence(), dropSkin);
                Label type = new Label("Type: " + ((Wearable) item).getWearableType(), dropSkin);
                windowTable.add(defence);
                windowTable.row();
                windowTable.add(type);
                window.add(windowTable);
            }

            if (item instanceof Consumable) {
                windowTable.clear();
                window.getTitleLabel().setText(item.getName());
                Label type = new Label("Type: Consumable", dropSkin);
                windowTable.add(type);
                window.add(windowTable);
            }


            stage.addActor(window);
        } catch (IndexOutOfBoundsException e) {

        }
    }

    public void removeDialog() {
        window.clear();
        window.remove();
    }
}



