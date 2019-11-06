package handlers;

import game.GameServer;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.User;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ActiveUserHandler {

    private HashMap<Integer, User> activeUsers;
    private ConcurrentHashMap<Integer, Avatar> activeAvatars;

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
        activeAvatars.put(avatar.getId(), avatar);
//        sendAvatarToClient(com.mygdx.game.entities.avatar);
    }

    private void sendAvatarToClient(Avatar avatar) {
        GameServer.getInstance().getServer().sendToAllTCP(avatar);
    }

    public ConcurrentHashMap<Integer, Avatar> getActiveAvatars() { return activeAvatars; }
}
