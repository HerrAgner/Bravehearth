package network.networkMessages.items;

import database.Column;

public class Item {
    @Column
    private String name;
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

}
