package network.networkMessages.items;

import java.util.HashMap;
import java.util.Map;

public class Consumable extends Item {
    private Map<String, Float> statChange;

    public Consumable(Item item, Map<String, Float> statChange) {
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
