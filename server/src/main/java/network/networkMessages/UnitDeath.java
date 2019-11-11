package network.networkMessages;

public class UnitDeath {
    int id;
    String unit;
    int exp;

    public UnitDeath(){
    }

    public UnitDeath(int id, String unit, int exp) {
        this.id = id;
        this.unit = unit;
        this.exp = exp;
    }
}
