package network.networkMessages.items;

import database.Column;

public class Item {
    @Column
    public String name;
    private int price;
    private int levelReq;
    private boolean equipped;

    public Item(Item item){
        this.name = item.name;
        this.levelReq = item.levelReq;
        this.price = item.price;

        this.equipped = item.equipped;
    }
    public Item(){

    }

    public Item(String name, int levelRequirement) {
        this.name = name;
        this.levelReq = levelRequirement;
    }

    public String getName() { return this.name; }

}
