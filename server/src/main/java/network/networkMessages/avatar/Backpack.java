package network.networkMessages.avatar;

import database.Column;
import java.util.ArrayList;
import java.util.List;

public class Backpack {
    private List items;
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

    public void setItems(List bpItems) { this.items = bpItems;}

    public List getItems() { return items; }
}
