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
    @Column
    private int id;

    public Item(Item item){
        this.name = item.name;
        this.levelReq = item.levelReq;
        this.price = item.price;
        this.equipped = item.equipped;
        this.texture = item.texture;
        this.id = item.id;
    }

    public Item(){ }

    public Item(int id, String name, int levelRequirement, String texture) {
        this.id = id;
        this.name = name;
        this.levelReq = levelRequirement;
        this.texture = texture;
    }

    public String getName() { return this.name; }

    public int getId() { return id; }
}
