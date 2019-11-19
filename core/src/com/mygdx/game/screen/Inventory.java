package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.entities.Items.*;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.network.networkMessages.ItemDropClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
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
    private Window window;
    private Table windowTable;
    private Skin dropSkin;
    private Window selectWindow;
    private TextButton equip;
    private TextButton drop;


    public Inventory() {
        dropSkin = ClientConnection.getInstance().assetManager.get("terra-mother/skin/terra-mother-ui.json");
        windowTable = new Table();
        stage = new Stage();
        table = new Table();
        skin = new Skin();
        itemSkin = new Skin();
        itemSlots = new Table();
        itemAtlas = ClientConnection.getInstance().assetManager.get("items/items.atlas");
        itemSkin.addRegions(itemAtlas);
        atlas = ClientConnection.getInstance().assetManager.get("hud.atlas");
        skin.addRegions(atlas);
        images = new ArrayList<>();
        itemSlot = new ArrayList<>();
        isOpen = false;
        window = new Window("default", dropSkin);
        initInventoryWindow();
        initEquippedItems();
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

    private void initEquippedItems() {
        Table equippedTable = new Table();
        Table backgroundTable = new Table();
        backgroundTable.setPosition(Gdx.graphics.getWidth() / 2 + 400, Gdx.graphics.getHeight() / 2);
        equippedTable.setPosition(Gdx.graphics.getWidth() / 2 + 400, Gdx.graphics.getHeight() / 2);
        Image head = new Image();
        Image amulet = new Image();
        Image chest = new Image();
        Image weapon = new Image();
        Image legs = new Image();
        Image boots = new Image();

        Image empty1 = new Image();
        Image empty2 = new Image();
        Image empty3 = new Image();
        Image empty4 = new Image();
        Image empty5 = new Image();
        Image empty6 = new Image();

        head.setDrawable(skin, "helmetBox");
        amulet.setDrawable(skin, "amuBox");
        chest.setDrawable(skin, "chestBox");
        weapon.setDrawable(skin, "weaponBox");
        legs.setDrawable(skin, "hudboxmiddlegray");
        boots.setDrawable(skin, "bootBox");

        empty1.setDrawable(skin, "hudboxmiddlegray");
        empty2.setDrawable(skin, "hudboxmiddlegray");
        empty3.setDrawable(skin, "hudboxmiddlegray");
        empty4.setDrawable(skin, "hudboxmiddlegray");
        empty5.setDrawable(skin, "hudboxmiddlegray");
        empty6.setDrawable(skin, "hudboxmiddlegray");

        HashMap<WearableType, Item> equippedItems = ClientConnection.getInstance().getUser().getAvatar().getEquippedItems().getEquippedItems();
        equippedItems.forEach((k, v) -> {
            switch (k) {
                case HEAD:
                    head.setDrawable(itemSkin, v.getTexture());
                    break;
                case ACCESSORY:
                    amulet.setDrawable(itemSkin, v.getTexture());
                    break;
                case WEAPON:
                    weapon.setDrawable(itemSkin, v.getTexture());
                    break;
                case CHEST:
                    chest.setDrawable(itemSkin, v.getTexture());
                    break;
                case LEGS:
                    legs.setDrawable(itemSkin, v.getTexture());
                    break;
                case FEET:
                    boots.setDrawable(itemSkin, v.getTexture());
                    break;
            }
        });

        backgroundTable.add(empty1).width(60).height(60);
        backgroundTable.row();
        backgroundTable.add(empty2).width(60).height(60);
        backgroundTable.row();
        backgroundTable.add(empty3).width(60).height(60);
        backgroundTable.add(empty4).width(60).height(60);
        backgroundTable.row();
        backgroundTable.add(empty5).width(60).height(60);
        backgroundTable.row();
        backgroundTable.add(empty6).width(60).height(60);

        equippedTable.add(head).width(60).height(60);
        equippedTable.row();
        equippedTable.add(amulet).width(60).height(60);
        equippedTable.row();
        equippedTable.add(chest).width(60).height(60);
        equippedTable.add(weapon).width(60).height(60);
        equippedTable.row();
        equippedTable.add(legs).width(60).height(60);
        equippedTable.row();
        equippedTable.add(boots).width(60).height(60);

        stage.addActor(backgroundTable);
        stage.addActor(equippedTable);
    }


    public Stage getStage() {
        return stage;
    }

    public void updateInventory() {
        AtomicInteger i = new AtomicInteger();
        ClientConnection.getInstance().getUser().getAvatar().getBackpack().getItems().forEach(item -> {
            itemSlot.get(i.get()).setDrawable(itemSkin, item.getTexture());
            i.getAndIncrement();
        });
        itemSlot.get(ClientConnection.getInstance().getUser().getAvatar().getBackpack().getItems().size()).setDrawable(null);

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
        selectWindow = new Window("", dropSkin);
        equip = new TextButton("Equip", dropSkin, "default");
        drop = new TextButton("Drop", dropSkin, "default");
        if (itemSlot.get(i).getDrawable() != null) {
            selectWindow.setBounds(Gdx.input.getX(), Gdx.input.getY(), 75, 100);
            equip.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent two, float x, float y) {
                }
            });
            drop.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent two, float x, float y) {
                    ItemDropClient itemDrop = new ItemDropClient();
                    itemDrop.setX(ClientConnection.getInstance().getActiveAvatars().get(ClientConnection.getInstance().getUser().getAvatar().getId()).getX());
                    itemDrop.setY(ClientConnection.getInstance().getActiveAvatars().get(ClientConnection.getInstance().getUser().getAvatar().getId()).getY());
                    itemDrop.setItem(ClientConnection.getInstance().getUser().getAvatar().getBackpack().getItems().get(i));
                    itemDrop.setAvatarId(ClientConnection.getInstance().getUser().getId());
                    itemDrop.setId(i);
                    ClientConnection.getInstance().getClient().sendTCP(itemDrop);
                    selectWindow.remove();
                }
            });
            selectWindow.add(equip);
            selectWindow.row();
            selectWindow.add(drop);
            stage.addActor(selectWindow);
        } else {
            selectWindow.remove();
        }
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

    public void render() {
        updateInventory();
    }
}



