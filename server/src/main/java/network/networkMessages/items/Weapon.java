package network.networkMessages.items;

public class Weapon extends Item {
    private int damage;
    private float speed;
    private float range;
    private WeaponType type;


    public Weapon(Item item, int damage, float speed, float range, WeaponType type) {
        super(item);
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        this.type = type;
    }

    public Weapon() {

    }
}
