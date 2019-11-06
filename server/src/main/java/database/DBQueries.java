package database;

import network.networkMessages.User;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.avatar.Backpack;
import network.networkMessages.items.Item;
import network.networkMessages.items.Weapon;

import java.sql.*;
import java.util.ArrayList;

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

    public static ArrayList getBpItems(int bpId) {
        ResultSet result;
        ArrayList items = new ArrayList();
        PreparedStatement ps = prep("SELECT * FROM backpackxitem b INNER JOIN weapons weap ON b.id = ? INNER JOIN wearables wear ON b.id  = ? INNER JOIN consumables c ON b.id  = ?");
        try {
            ps.setInt(1, bpId);
            ps.setInt(2, bpId);
            ps.setInt(3, bpId);

            result = ps.executeQuery();
            while(result.next()) {
                //items.add(result.getString("name"));
                System.out.println("result " + result.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}