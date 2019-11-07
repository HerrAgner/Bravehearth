package network.networkMessages.items;

import database.Column;

public class Weapon extends Item{
    @Column
    private int damage;
    @Column
    private float speed;
    @Column
    private float range;
    @Column
    private int levelRequirement;
    @Column
    private WeaponType weaponType;
    @Column
    private WearableType wearableType;


    public Weapon(Item item, int damage, float speed, float range, WeaponType weaponType, WearableType wearableType) {
        super(item);
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        this.weaponType = weaponType;
        this.wearableType = wearableType;
    }

    public Weapon() { }

    public String getName() { return this.name; }

    @Override
    public String toString() {
        return this.name;
    }
}
