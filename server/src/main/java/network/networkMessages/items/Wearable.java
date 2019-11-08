package network.networkMessages.items;

import java.util.HashMap;

public class Wearable extends Item{
    private HashMap<String, Float> statChange;
    private int defence;
    private WearableType wearableType;

    public Wearable(Item item, HashMap<String, Float> statChange, int defence, WearableType wearableType) {
        super(item);
        this.statChange = statChange;
        this.defence = defence;
        this.wearableType = wearableType;
    }

    public Wearable() { }

    public String getName() { return this.name; }

    @Override
    public String toString() {
        return this.name;
    }
}
