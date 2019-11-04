package network.networkMessages.items;

public class Item {
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
