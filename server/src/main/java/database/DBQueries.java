package database;

import network.networkMessages.User;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.avatar.Backpack;
import network.networkMessages.avatar.EquippedItems;
import network.networkMessages.items.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static network.networkMessages.items.WearableType.*;

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
            result = (Avatar) new ObjectMapper<>(Avatar.class).mapOne(ps.executeQuery());
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
        List items = new ArrayList();
        PreparedStatement ps = prep("SELECT name, weaponType, speed, damage, `range`, levelRequirement, type " +
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
                items.add(new Weapon(new Item(name, levelRequirement), damage, speed, range, weaponType, wearType));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps2 = prep("SELECT name, type, defense, statChange, statToAffect, levelRequirement " +
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
                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                items.add(new Wearable(new Item(name, levelRequirement), map, defense, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps3 = prep("SELECT name, statChange, statToAffect, levelRequirement " +
                "FROM backpackxitem b INNER JOIN consumables c ON b.consumable = c.id WHERE b.id = ?");
        try {
            ps3.setInt(1, bpId);
            ResultSet rs = ps3.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                float statChange = rs.getFloat(2);
                String statToAffect = rs.getString(3);
                int levelRequirement = rs.getInt(4);
                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                items.add(new Consumable(new Item(name, levelRequirement), map));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static EquippedItems getEquippedItems(int avatarId) {
        HashMap<WearableType, Item> items = new HashMap<>();
        EquippedItems eq;

        PreparedStatement ps = prep("SELECT `name`, weaponType, speed, damage, `range`, levelRequirement, type " +
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
                items.put(wearType, new Weapon(new Item(name, levelRequirement), damage, speed, range, weaponType, wearType));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps2 = prep("SELECT name, type, defense, statChange, statToAffect, levelRequirement " +
                "FROM equippedItems INNER JOIN wearables ON equippedItems.headSlot = wearables.id " +
                "OR equippedItems.chestSlot = wearables.id OR equippedItems.legSlot = wearables.id " +
                "OR equippedItems.feetSlot = wearables.id OR equippedItems.accessorySlot = wearables.id " +
                "WHERE equippedItems.avatar = ?");
        try {
            System.out.println("wearables query");

            ps2.setInt(1, avatarId);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                WearableType type = WearableType.valueOf(rs.getString(2));
                int defense = rs.getInt(3);
                float statChange = rs.getFloat(4);
                String statToAffect = rs.getString(5);
                int levelRequirement = rs.getInt(6);

                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                items.put(type, new Wearable(new Item(name, levelRequirement), map, defense, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        items.forEach((wearableType, item) -> {
            System.out.println("EI " + item.getName());
        });
        eq = new EquippedItems(avatarId, items);
        return eq;
    }
}