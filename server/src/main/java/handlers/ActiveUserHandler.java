package handlers;

import java.util.concurrent.CopyOnWriteArrayList;

public class ActiveUserHandler {

    //thread-safe variant of arrayList
    private CopyOnWriteArrayList<Object> activeUsers;

    public ActiveUserHandler() {
        activeUsers = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayList getActiveUsers() {
        return activeUsers;
    }

    public void addToActiveUsers(Object user) {
        activeUsers.add(user);
    }
}
