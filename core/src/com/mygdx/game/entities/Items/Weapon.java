package com.mygdx.game.entities.Items;

public class Weapon extends Item {
    private int damage;
    private float speed;
    private float range;
    private int levelRequirement;
    private WeaponType weaponType;
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

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    public float getRange() {
        return range;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public WearableType getWearableType() {
        return wearableType;
    }
}
