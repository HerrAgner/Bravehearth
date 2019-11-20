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
    @Column
    private int id;


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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public int getId() { return id; }
}
