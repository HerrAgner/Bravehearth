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
}
