package network.networkMessages.items;

import database.Column;

public class Item {
    @Column
    public String name;
    private int price;
    private int levelReq;
    private boolean equipped;
    @Column
    private String texture;

    public Item(Item item){
        this.name = item.name;
        this.levelReq = item.levelReq;
        this.price = item.price;
        this.equipped = item.equipped;
        this.texture = item.texture;
    }

    public Item(){ }

    public Item(String name, int levelRequirement, String texture) {
        this.name = name;
        this.levelReq = levelRequirement;
        this.texture = texture;
    }

    public String getName() { return this.name; }

}
