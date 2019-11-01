package handlers;

import network.networkMessages.Avatar;
import network.networkMessages.User;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActiveUserHandler {
    
    private HashMap<Integer, User> activeUsers;
    private ConcurrentHashMap<Integer, String> activeAvatar;

    public ActiveUserHandler() { activeUsers = new HashMap<>(); }

    public void addToActiveUsers(int connectionId, User user) {
        activeUsers.put(connectionId, user);
        System.out.println("user added" + user.getUsername());
        addToActiveAvatars(user.getAvatar());
    }

    public HashMap<Integer, User> getActiveUsers() {
        return activeUsers;
    }

    private void addToActiveAvatars(Avatar avatar) {
        //go through array of users and add the player they are using to a different array



    }
}
