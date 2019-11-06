package database;

import network.networkMessages.User;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.avatar.Backpack;

import java.sql.*;

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

   /* public Backpack getBackpack(int avatarId) {
        Backpack result = null;
        PreparedStatement ps = prep("SELECT * FROM avatars WHERE user = ?");
        try {
            ps.setInt(1, avatarId);
            result = (Backpack) new ObjectMapper<>(Backpack.class).map(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }*/
}