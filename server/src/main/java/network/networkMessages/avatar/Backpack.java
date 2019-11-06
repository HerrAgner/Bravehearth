package network.networkMessages.avatar;


import database.Column;
import network.networkMessages.items.Item;

import java.util.ArrayList;

public class Backpack {
    private ArrayList items;
    @Column
    private int avatarId;
    @Column
    private int wallet;
    @Column
    private int id;

    public Backpack(int avatarId) {
        this.avatarId = avatarId;
        this.id = id;
        this.items = new ArrayList<>();
    }

    public Backpack() { }

    public int getId() { return id; }

    public void setItems(ArrayList bpItems) { this.items = bpItems;}

    public ArrayList getItems() { return items; }
}
