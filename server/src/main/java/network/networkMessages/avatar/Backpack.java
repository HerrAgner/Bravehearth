package network.networkMessages.avatar;


import network.networkMessages.items.Item;

import java.util.ArrayList;
import java.util.UUID;

public class Backpack {
    private ArrayList<Item> items;
    private UUID avatarId;
    private int wallet;

    public Backpack(UUID avatarId) {
        this.avatarId = avatarId;
        this.items = new ArrayList<>();
    }

    public Backpack() {

    }

}
