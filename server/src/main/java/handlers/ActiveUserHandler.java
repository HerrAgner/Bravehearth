package handlers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActiveUserHandler {

    //thread-safe variant of arrayList
    private CopyOnWriteArrayList<Object> activeUsers;
    private ConcurrentHashMap<Integer, String> activePlayers;

    public ActiveUserHandler() {
        activeUsers = new CopyOnWriteArrayList<>();
    }

    public void addToActiveUsers(Object user) {
        activeUsers.add(user);
    }

    public CopyOnWriteArrayList getActiveUsers() {
        return activeUsers;
    }

    private void addToActivePlayers() {
        //go through array of users and add the player they are using to a different array
    }
}
