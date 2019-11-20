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
        PreparedStatement ps = prep("SELECT weap.id, name, weaponType, speed, damage, `range`, levelRequirement, type, texture " +
                "FROM backpackxitem b INNER JOIN weapons weap ON b.weapon = weap.id WHERE b.id = ?");
        try {
            ps.setInt(1, bpId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                WeaponType weaponType = WeaponType.valueOf(rs.getString(3));
                int speed = rs.getInt(4);
                int damage = rs.getInt(5);
                int range = rs.getInt(6);
                int levelRequirement = rs.getInt(7);
                WearableType wearType = WearableType.valueOf(rs.getString(8));
                String texture = rs.getString(9);
                items.add(new Weapon(new Item(id, name, levelRequirement, texture), damage, speed, range, weaponType, wearType));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps2 = prep("SELECT wear.id, name, type, defense, statChange, statToAffect, levelRequirement, texture " +
                "FROM backpackxitem b INNER JOIN wearables wear ON b.wearable = wear.id WHERE b.id = ?");
        try {
            ps2.setInt(1, bpId);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                WearableType type = WearableType.valueOf(rs.getString(3));
                int defense = rs.getInt(4);
                float statChange = rs.getFloat(5);
                String statToAffect = rs.getString(6);
                int levelRequirement = rs.getInt(7);
                String texture = rs.getString(8);
                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                items.add(new Wearable(new Item(id, name, levelRequirement, texture), map, defense, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps3 = prep("SELECT c.id, name, statChange, statToAffect, levelRequirement, texture " +
                "FROM backpackxitem b INNER JOIN consumables c ON b.consumable = c.id WHERE b.id = ?");
        try {
            ps3.setInt(1, bpId);
            ResultSet rs = ps3.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                float statChange = rs.getFloat(3);
                String statToAffect = rs.getString(4);
                int levelRequirement = rs.getInt(5);
                String texture = rs.getString(6);
                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                items.add(new Consumable(new Item(id, name, levelRequirement, texture), map));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static EquippedItems getEquippedItems(int avatarId) {
        HashMap<WearableType, Item> items = new HashMap<>();
        EquippedItems eq;

        PreparedStatement ps = prep("SELECT weapons.id, `name`, weaponType, speed, damage, `range`, levelRequirement, type, texture " +
                "FROM equippedItems INNER JOIN weapons ON equippedItems.offHandSlot = weapons.id " +
                "OR equippedItems.weaponSlot = weapons.id WHERE equippedItems.avatar = ?");
        try {
            ps.setInt(1, avatarId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                WeaponType weaponType = WeaponType.valueOf(rs.getString(3));
                int speed = rs.getInt(4);
                int damage = rs.getInt(5);
                int range = rs.getInt(6);
                int levelRequirement = rs.getInt(7);
                WearableType wearType = WearableType.valueOf(rs.getString(8));
                String texture = rs.getString(9);
                items.put(wearType, new Weapon(new Item(id, name, levelRequirement, texture), damage, speed, range, weaponType, wearType));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps2 = prep("SELECT wearables.id, name, type, defense, statChange, statToAffect, levelRequirement, texture " +
                "FROM equippedItems INNER JOIN wearables ON equippedItems.headSlot = wearables.id " +
                "OR equippedItems.chestSlot = wearables.id OR equippedItems.legSlot = wearables.id " +
                "OR equippedItems.feetSlot = wearables.id OR equippedItems.accessorySlot = wearables.id " +
                "WHERE equippedItems.avatar = ?");
        try {
            ps2.setInt(1, avatarId);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                WearableType type = WearableType.valueOf(rs.getString(3));
                int defense = rs.getInt(4);
                float statChange = rs.getFloat(5);
                String statToAffect = rs.getString(6);
                int levelRequirement = rs.getInt(7);
                String texture = rs.getString(8);

                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                items.put(type, new Wearable(new Item(id, name, levelRequirement, texture), map, defense, type));
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
        return drop;
    }

    public static Weapon getWeapon(int weaponId) {
        Weapon w = null;

        PreparedStatement ps = prep("SELECT id, `name`, weaponType, speed, damage, `range`, levelRequirement, type, texture " +
                "FROM weapons WHERE id = ?");
        try {
            ps.setInt(1, weaponId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                WeaponType weaponType = WeaponType.valueOf(rs.getString(3));
                int speed = rs.getInt(4);
                int damage = rs.getInt(5);
                int range = rs.getInt(6);
                int levelRequirement = rs.getInt(7);
                WearableType wearType = WearableType.valueOf(rs.getString(8));
                String texture = rs.getString(9);
                w = new Weapon(new Item(weaponId, name, levelRequirement, texture), damage, speed, range, weaponType, wearType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return w;
    }

    public static Wearable getWearable(int wearableId) {
        Wearable w = null;
        PreparedStatement ps2 = prep("SELECT id, name, type, defense, statChange, statToAffect, levelRequirement, texture " +
                "FROM wearables WHERE id = ?");
        try {
            ps2.setInt(1, wearableId);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                WearableType type = WearableType.valueOf(rs.getString(3));
                int defense = rs.getInt(4);
                float statChange = rs.getFloat(5);
                String statToAffect = rs.getString(6);
                int levelRequirement = rs.getInt(7);
                String texture = rs.getString(8);
                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                w = new Wearable(new Item(id, name, levelRequirement, texture), map, defense, type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return w;
    }

    public static Consumable getConsumable(int consumableId) {
        Consumable c = null;
        PreparedStatement ps3 = prep("SELECT id, name, statChange, statToAffect, levelRequirement, texture " +
                "FROM consumables WHERE id = ?");
        try {
            ps3.setInt(1, consumableId);
            ResultSet rs = ps3.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                float statChange = rs.getFloat(3);
                String statToAffect = rs.getString(4);
                int levelRequirement = rs.getInt(5);
                String texture = rs.getString(6);
                HashMap<String, Float> map = new HashMap<>();
                map.put(statToAffect, statChange);
                c = new Consumable(new Item(id, name, levelRequirement, texture), map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static void saveAvatarOnLevelUp(Avatar avatar) {
        PreparedStatement ps = prep("UPDATE avatars SET health = ?, maxHealth = ?, maxMana = ?, " +
                "strength = ?, dexterity = ?, intelligence = ?, `level` = ?, experiencePoints = ?, x = ?, y = ? WHERE id = ?");
        try {
            ps.setInt(1, avatar.getHealth());
            ps.setInt(2, avatar.getMaxHealth());
            ps.setInt(3, avatar.getMaxMana());
            ps.setInt(4, avatar.getStrength());
            ps.setInt(5, avatar.getDexterity());
            ps.setInt(6, avatar.getIntelligence());
            ps.setInt(7, avatar.getLevel());
            ps.setInt(8, avatar.getExperiencePoints());
            ps.setFloat(9, avatar.getX());
            ps.setFloat(10, avatar.getY());
            ps.setInt(11, avatar.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("update successful");
    }

    public static void saveAvatarWhenDead(Avatar avatar) {
        PreparedStatement ps = prep("UPDATE avatars a INNER JOIN backpacks b ON a.id = b.avatarId " +
                "SET a.health = ?, a.experiencePoints = ?, a.x = 99, a.y = 57, b.wallet = ? WHERE a.id = ?");
        try {
            ps.setInt(1, avatar.getMaxHealth());
            ps.setInt(2, 0);
            ps.setInt(3, avatar.getBackpack().getWallet() / 2);
            ps.setInt(4, avatar.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement ps2 = prep("DELETE FROM backpackxitem WHERE id = ?");
        try {
            ps2.setInt(1, avatar.getId());
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveToBackpack(int bpId, Item item) {
        PreparedStatement ps;
        try {
            if(item instanceof Weapon) {
                ps = prep("INSERT INTO backpackxitem (id, weapon) VALUES (?, ?)");
            } else if(item instanceof Wearable) {
                ps = prep("INSERT INTO backpackxitem (id, wearable) VALUES (?, ?)");
            } else ps = prep("INSERT INTO backpackxitem (id, consumable) VALUES (?, ?)");
            ps.setInt(1, bpId);
            ps.setInt(2, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeFromBackpack(int bpId, Item item) {
        PreparedStatement ps;
        try {
            if(item instanceof Weapon) {
                ps = prep("DELETE FROM backpackxitem WHERE id = ? AND weapon = ? LIMIT 1");
            } else if(item instanceof Wearable) {
                ps = prep("DELETE FROM backpackxitem WHERE id = ? AND wearable = ? LIMIT 1");
            } else ps = prep("DELETE FROM backpackxitem WHERE id = ? AND consumable = ? LIMIT 1");
            ps.setInt(1, bpId);
            ps.setInt(2, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveEquippedItems(int avatarId, EquippedItems eq) {
        PreparedStatement ps = prep("UPDATE equippedItems SET headSlot = ?, chestSlot = ?, legSlot = ?, " +
                "feetSlot = ?, weaponSlot = ?, accessorySlot = ? WHERE avatar = ?");
        try {
            if(eq.getEquippedItems().get(WearableType.HEAD) != null) { ps.setInt(1, eq.getEquippedItems().get(WearableType.HEAD).getId()); }
            else {ps.setString(1, null);}

            if(eq.getEquippedItems().get(WearableType.CHEST) != null) { ps.setInt(2, eq.getEquippedItems().get(WearableType.CHEST).getId()); }
            else {ps.setString(2, null);}

            if(eq.getEquippedItems().get(WearableType.LEGS) != null) { ps.setInt(3, eq.getEquippedItems().get(WearableType.LEGS).getId()); }
            else {ps.setString(3, null);}

            if(eq.getEquippedItems().get(WearableType.FEET) != null) { ps.setInt(4, eq.getEquippedItems().get(WearableType.FEET).getId()); }
            else {ps.setString(4, null);}

            if(eq.getEquippedItems().get(WearableType.WEAPON) != null) { ps.setInt(5, eq.getEquippedItems().get(WearableType.WEAPON).getId()); }
            else {ps.setString(5, null);}

            if(eq.getEquippedItems().get(WearableType.ACCESSORY) != null) { ps.setInt(6, eq.getEquippedItems().get(WearableType.ACCESSORY).getId()); }
            else {ps.setString(6, null);}

            ps.setInt(7, avatarId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}