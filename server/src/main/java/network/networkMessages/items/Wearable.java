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

    public HashMap<String, Float> getStatChange() {
        return statChange;
    }

    public void setStatChange(HashMap<String, Float> statChange) {
        this.statChange = statChange;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public WearableType getWearableType() {
        return wearableType;
    }

    public void setWearableType(WearableType wearableType) {
        this.wearableType = wearableType;
    }

    @Override
    public int getId() { return this.id; }
}
