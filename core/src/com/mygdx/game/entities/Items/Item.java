package com.mygdx.game.entities.Items;

public class Item {
    private String name;
    private int price;
    private int levelReq;
    private boolean equipped;
    private String texture;

    public Item(Item item){
        this.name = item.name;
        this.levelReq = item.levelReq;
        this.price = item.price;

        this.equipped = item.equipped;
    }
    public Item(){ }

    public String getName() {
        return name;
    }


    public String getTexture() {
        return texture;
    }
}
