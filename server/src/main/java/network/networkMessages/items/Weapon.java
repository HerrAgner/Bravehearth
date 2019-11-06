package network.networkMessages.items;

import database.Column;

public class Weapon extends Item {
    @Column
    private int damage;
    @Column
    private float speed;
    @Column
    private float range;
    @Column
    private int levelRequirement;
    @Column
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
