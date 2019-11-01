package handlers;

import network.networkMessages.Avatar;
import network.networkMessages.User;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ActiveUserHandler {

    private HashMap<Integer, User> activeUsers;
    private ConcurrentHashMap<UUID, Avatar> activeAvatars;

    public ActiveUserHandler() {
        activeUsers = new HashMap<>();
        activeAvatars = new ConcurrentHashMap<>();
    }

    public void addToActiveUsers(int connectionId, User user) {
        activeUsers.put(connectionId, user);
        addToActiveAvatars(user.getAvatar());
    }

    public HashMap<Integer, User> getActiveUsers() {
        return activeUsers;
    }

    private void addToActiveAvatars(Avatar avatar) {
        activeAvatars.put(UUID.randomUUID(), avatar);
    }

    public ConcurrentHashMap<UUID, Avatar> getActiveAvatars() { return activeAvatars; }
}
