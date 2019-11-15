package database;

import network.networkMessages.CharacterClass;
import network.networkMessages.Monster;
import network.networkMessages.User;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.avatar.Backpack;
import network.networkMessages.avatar.EquippedItems;
import network.networkMessages.items.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class DBQueries {

    public static PreparedStatement prep(String SQLQuery) {
        return Database.getInstance().prepareStatement(SQLQuery);
    }

    public static User getMatchingUser(String username, String password) {
        User result = null;
        PreparedStatement ps = prep("SELECT * FROM users WHERE username = ? AND password = ?");
        try {
            ps.setString(1, username);
            ps.setString(2, password);
            result = (User) new ObjectMapper<>(User.class).mapOne(ps.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Avatar getUserAvatar(int userId) {
        Avatar result = null;
        PreparedStatement ps = prep("SELECT * FROM avatars WHERE user = ?");
        try {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int user = rs.getInt(2);
                String name = rs.getString(3);
                int health = rs.getInt(4);
                int maxHealth = rs.getInt(5);
                int mana = rs.getInt(6);
                int maxMana = rs.getInt(7);
                int attackDamage = rs.getInt(8);
                float attackSpeed = rs.getFloat(9);
                float attackRange = rs.getInt(10);
                CharacterClass characterClass = CharacterClass.valueOf(rs.getString(11));
                int strength = rs.getInt(12);
                int dexterity = rs.getInt(13);
                int intelligence = rs.getInt(14);
                int level = rs.getInt(15);
                int experiencePoints = rs.getInt(16);
                float defense = rs.getFloat(17);
                int x = rs.getInt(18);
                int y = rs.getInt(19);
                result = new Avatar(x, y, name, health, maxHealth, mana, attackDamage, attackSpeed, attackRange, id, characterClass, maxMana, strength, dexterity, intelligence, level, experiencePoints, defense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Backpack getBackpack(int avatarId) {
        Backpack result = null;
        PreparedStatement ps = prep("SELECT * FROM backpacks WHERE avatarId = ?");
        try {
            ps.setInt(1, avatarId);
            result = (Backpack) new ObjectMapper<>(Backpack.class).mapOne(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List getBpItems(int bpId) {
        List<Item> items = new ArrayList();
        PreparedStatement ps = prep("SELECT name, weaponType, speed, damage, `range`, levelRequirement, type, texture " +
                "FROM backpackxitem b INNER JOIN weapons weap ON b.weapon = weap.id WHERE b.id = ?");
        try {
            ps.setInt(1, bpId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                WeaponType weaponType = WeaponType.valueOf(rs.getString(2));
                int speed = rs.getInt(3);
                int damage = rs.getInt(4);
                int range = rs.getInt(5);
                int levelRequirement = rs.getInt(6);
                WearableType wearType = WearableType.valueOf(rs.getString(7));
                String texture = rs.getString(8);
                items.add(new Weapon(new Item(name, levelRequirement, texture), damage, speed, range, weaponType, wearType));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps2 = prep("SELECT name, type, defense, statChange, statToAffect, levelRequirement, texture " +
                "FROM backpackxitem b INNER JOIN wearables wear ON b.wearable = wear.id WHERE b.id = ?");
        try {
            ps2.setInt(1, bpId);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                WearableType type = WearableType.valueOf(rs.getString(2));
                int defense = rs.getInt(3);
                float statChange = rs.getFloat(4);
                String statToAffect = rs.getString(5);
                int levelRequirement = rs.getInt(6);
                String texture = rs.getString(7);
                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                items.add(new Wearable(new Item(name, levelRequirement, texture), map, defense, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps3 = prep("SELECT name, statChange, statToAffect, levelRequirement, texture " +
                "FROM backpackxitem b INNER JOIN consumables c ON b.consumable = c.id WHERE b.id = ?");
        try {
            ps3.setInt(1, bpId);
            ResultSet rs = ps3.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                float statChange = rs.getFloat(2);
                String statToAffect = rs.getString(3);
                int levelRequirement = rs.getInt(4);
                String texture = rs.getString(5);
                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                items.add(new Consumable(new Item(name, levelRequirement, texture), map));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static EquippedItems getEquippedItems(int avatarId) {
        HashMap<WearableType, Item> items = new HashMap<>();
        EquippedItems eq;

        PreparedStatement ps = prep("SELECT `name`, weaponType, speed, damage, `range`, levelRequirement, type, texture " +
                "FROM equippedItems INNER JOIN weapons ON equippedItems.offHandSlot = weapons.id " +
                "OR equippedItems.weaponSlot = weapons.id WHERE equippedItems.avatar = ?");
        try {
            ps.setInt(1, avatarId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                WeaponType weaponType = WeaponType.valueOf(rs.getString(2));
                int speed = rs.getInt(3);
                int damage = rs.getInt(4);
                int range = rs.getInt(5);
                int levelRequirement = rs.getInt(6);
                WearableType wearType = WearableType.valueOf(rs.getString(7));
                String texture = rs.getString(8);
                items.put(wearType, new Weapon(new Item(name, levelRequirement, texture), damage, speed, range, weaponType, wearType));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps2 = prep("SELECT name, type, defense, statChange, statToAffect, levelRequirement, texture " +
                "FROM equippedItems INNER JOIN wearables ON equippedItems.headSlot = wearables.id " +
                "OR equippedItems.chestSlot = wearables.id OR equippedItems.legSlot = wearables.id " +
                "OR equippedItems.feetSlot = wearables.id OR equippedItems.accessorySlot = wearables.id " +
                "WHERE equippedItems.avatar = ?");
        try {
            ps2.setInt(1, avatarId);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                WearableType type = WearableType.valueOf(rs.getString(2));
                int defense = rs.getInt(3);
                float statChange = rs.getFloat(4);
                String statToAffect = rs.getString(5);
                int levelRequirement = rs.getInt(6);
                String texture = rs.getString(7);

                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                items.put(type, new Wearable(new Item(name, levelRequirement, texture), map, defense, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        eq = new EquippedItems(avatarId, items);
        return eq;
    }

    public static Monster getMonsterById(int monsterId) {
        Monster result = null;
        PreparedStatement ps = prep("SELECT * FROM monsters WHERE id = ?");
        try {
            ps.setInt(1, monsterId);
            result = (Monster) new ObjectMapper<>(Monster.class).mapOne(ps.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static HashMap<Item, Float> getMonsterDrop(int monsterId) {
        HashMap<Item, Float> drop = new HashMap<>();
        PreparedStatement ps = prep("SELECT * FROM monsterxitemdrop WHERE id = ?");
        try {
            ps.setInt(1, monsterId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                float dropChance = rs.getFloat(5);
                if(rs.getInt(2) != 0) { drop.put(getWeapon(rs.getInt(2)), dropChance); }
                if(rs.getInt(3) != 0) { drop.put(getWearable(rs.getInt(3)), dropChance); }
                if(rs.getInt(4) != 0) { drop.put(getConsumable(rs.getInt(4)), dropChance); }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        drop.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
        return drop;
    }

    public static Weapon getWeapon(int weaponId) {
        Weapon w = null;

        PreparedStatement ps = prep("SELECT `name`, weaponType, speed, damage, `range`, levelRequirement, type, texture " +
                "FROM weapons WHERE id = ?");
        try {
            ps.setInt(1, weaponId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                WeaponType weaponType = WeaponType.valueOf(rs.getString(2));
                int speed = rs.getInt(3);
                int damage = rs.getInt(4);
                int range = rs.getInt(5);
                int levelRequirement = rs.getInt(6);
                WearableType wearType = WearableType.valueOf(rs.getString(7));
                String texture = rs.getString(8);
                w = new Weapon(new Item(name, levelRequirement, texture), damage, speed, range, weaponType, wearType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return w;
    }

    public static Wearable getWearable(int wearableId) {
        Wearable w = null;
        PreparedStatement ps2 = prep("SELECT name, type, defense, statChange, statToAffect, levelRequirement, texture " +
                "FROM wearables WHERE id = ?");
        try {
            ps2.setInt(1, wearableId);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                WearableType type = WearableType.valueOf(rs.getString(2));
                int defense = rs.getInt(3);
                float statChange = rs.getFloat(4);
                String statToAffect = rs.getString(5);
                int levelRequirement = rs.getInt(6);
                String texture = rs.getString(7);
                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                w = new Wearable(new Item(name, levelRequirement, texture), map, defense, type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return w;
    }

    public static Consumable getConsumable(int consumableId) {
        Consumable c = null;
        PreparedStatement ps3 = prep("SELECT name, statChange, statToAffect, levelRequirement, texture " +
                "FROM consumables WHERE id = ?");
        try {
            ps3.setInt(1, consumableId);
            ResultSet rs = ps3.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                float statChange = rs.getFloat(2);
                String statToAffect = rs.getString(3);
                int levelRequirement = rs.getInt(4);
                String texture = rs.getString(5);
                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                c = new Consumable(new Item(name, levelRequirement, texture), map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static void saveAvatarOnLevelUp(Avatar avatar) {
        PreparedStatement ps = prep("UPDATE avatars SET health = ?, maxHealth = ?, maxMana = ?, " +
                "strength = ?, dexterity = ?, intelligence = ?, `level` = ?, experiencepoints = 0 WHERE id = ?");
        try {
            ps.setInt(1, avatar.getMaxHealth());
            ps.setInt(2, avatar.getMaxHealth());
            ps.setInt(3, avatar.getMaxMana());
            ps.setInt(4, avatar.getStrength());
            ps.setInt(5, avatar.getDexterity());
            ps.setInt(6, avatar.getIntelligence());
            ps.setInt(7, avatar.getLevel());
            ps.setInt(8, avatar.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("update successful");
    }
}