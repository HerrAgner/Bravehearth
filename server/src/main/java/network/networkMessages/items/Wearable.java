package network.networkMessages.items;

import java.util.HashMap;
import java.util.Map;

public class Wearable extends Item{
    private Map<String, Float> statChange = new HashMap<>();
    private int defence;
    private WearableType wearableType;


    public Wearable(Item item, HashMap<String, Float> statChange, int defence, WearableType wearableType) {
        super(item);
        this.statChange = statChange;
        this.defence = defence;
        this.wearableType = wearableType;
    }

    public Wearable() {

    }

    public String getName() { return this.name; }

    @Override
    public String toString() {
        return this.name;
    }
}
