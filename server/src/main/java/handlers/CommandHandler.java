package handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import database.DBQueries;
import enums.Command;
import game.GameServer;
import network.networkMessages.*;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.avatar.Backpack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandHandler {
    private LinkedBlockingQueue<HashMap<Connection, Object>> commandQueue;
    private MovementHandler movementHandler;
    private Connection connection;
    ActiveUserHandler auh;
    AttackHandler ah;
    Server server;

    public CommandHandler() {
        commandQueue = new LinkedBlockingQueue<>();
        movementHandler = new MovementHandler();
        ah = new AttackHandler();
        auh = GameServer.getInstance().getAUH();
        server = GameServer.getInstance().getServer();
    }

    public void addToQueue(Connection connection, Object object) {
        this.connection = connection;
        HashMap<Connection, Object> map = new HashMap<>() {
            {
                put(connection, object);
            }
        };
        commandQueue.add(map);
        checkCommandInputType();
    }

    private void checkCommandInputType() {
        while (!commandQueue.isEmpty()) {
            try {
                HashMap<Connection, Object> comm = commandQueue.take();
                handleCommand(comm);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleCommand(HashMap<Connection, Object> command) {
        Map.Entry<Connection, Object> entry = command.entrySet().iterator().next();
        Object o = entry.getValue();
        Connection c = entry.getKey();

        if (o instanceof Login) {
            User user = (createUser(o));
            if (user != null) {
                server.sendToTCP(connection.getID(), user);
                auh.addToActiveUsers(connection.getID(), user);
                auh.getActiveAvatars().values().forEach(avatar ->
                        server.sendToTCP(connection.getID(), avatar));
                server.sendToAllExceptTCP(connection.getID(), user.getAvatar());
                GameServer.getInstance().getMh().itemsOnGround.forEach((floats, item) -> {
                    server.sendToTCP(connection.getID(), new ItemDrop(floats[0], floats[1], item));
                });
                GameServer.getInstance().getMh().monsterList.forEach((integer, monster1) -> server.sendToTCP(connection.getID(), monster1));
                GameServer.getInstance().getServer().sendToTCP(connection.getID(), "finished");
            }
        }

        if (o instanceof AttackEnemyTarget) {
            AttackEnemyTarget aet = (AttackEnemyTarget) o;
            auh.getActiveAvatars().get(aet.getAttacker()).setMarkedUnit(aet.getTarget());
//            ah.addAttackerToList(aet.getAttacker(), aet.getTarget(), 1);
        }

        if (o instanceof Logout) {
            server.sendToAllTCP(o);
            auh.getActiveAvatars().remove(((Logout) o).getAvatar());
            auh.getActiveUsers().remove(c.getID());
            GameServer.getInstance().getMh().monsterList.forEach((integer, monster) -> {
                if (monster.getMarkedUnit() == ((Logout) o).getAvatar()){
                    monster.setMarkedUnit(-1);
                }
            });
        }
        if (o instanceof ItemPickup) {
            GameServer.getInstance().getMh().itemsOnGround.forEach((floats, item) -> {
                if (floats[0] == ((ItemPickup) o).getX() && floats[1] == ((ItemPickup) o).getY() && item.getName().equals(((ItemPickup) o).getItem().getName())) {
                    GameServer.getInstance().getMh().itemsOnGround.remove(floats);
                }
            });
            GameServer.getInstance().aa.get(((ItemPickup) o).getAvatarId()).getBackpack().getItems().add(((ItemPickup) o).getItem());
            GameServer.getInstance().getServer().sendToAllTCP(o);
        }

    }

    private void handleCommand(String command) {
        switch (Command.valueOf(command)) {
            //add logic for commands later
            case ATTACK:
                System.out.println("attack");
                break;
            case SPELL:
                System.out.println("spell");
                break;
            case BACKPACK:
                System.out.println("backpack");
                break;
            case MAP:
                System.out.println("map");
                break;
        }
    }

    private User createUser(Object object) {
        Login loginObject = (Login) object;
        User user = DBQueries.getMatchingUser(loginObject.getUsername(), loginObject.getPassword());
        try {
            Avatar avatar = DBQueries.getUserAvatar(user.getId());
            Backpack bp = DBQueries.getBackpack(avatar.getId());
            bp.setItems(DBQueries.getBpItems(bp.getId()));
            avatar.setBackpack(bp);
            avatar.setEquippedItems(DBQueries.getEquippedItems(avatar.getId()));
            avatar.setBackpack(bp);
            user.setAvatar(avatar);
        } catch (NullPointerException e) {
            System.out.println("No avatar found for user.");
        }
        return user;
    }
}