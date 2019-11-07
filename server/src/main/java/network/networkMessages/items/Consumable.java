package network.networkMessages.items;

import java.util.HashMap;

public class Consumable extends Item {
    private HashMap<String, Float> statChange;

    public Consumable(Item item, HashMap<String, Float> statChange) {
        super(item);
        this.statChange = statChange;
    }

    public Consumable() {}

    public String getName() { return this.name; }

    @Override
    public String toString() {
        return this.name;
    }
}
