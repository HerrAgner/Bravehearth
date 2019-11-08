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

                Monster monster = new Monster(5,5,"ANTONMONSTRET");
                monster.setY(190);
                monster.setMaxXspeed(0.01f);
                monster.setMaxYspeed(0.01f);
                monster.setBoundsRadius(4f);
                monster.setType(MonsterType.DUMMYMONSTER);

                Monster monster2 = new Monster(5,5,"ANTONMONSTRETt");
                monster2.setY(190);
                monster2.setX(10);
                monster2.setMaxXspeed(0.01f);
                monster2.setMaxYspeed(0.01f);
                monster2.setBoundsRadius(4f);
                monster2.setType(MonsterType.DUMMYMONSTER);

                MonsterHandler.monsterList.put(monster.getId(), monster);
                MonsterHandler.monsterList.put(monster2.getId(), monster2);

                server.sendToAllTCP(monster);
                server.sendToAllTCP(monster2);
            }
        }

        /*if (o instanceof AttackEnemyTarget) {
            AttackEnemyTarget aet = (AttackEnemyTarget) o;
            auh.getActiveAvatars().get(aet.getAttacker()).setMarkedUnit(aet.getTarget());
            ah.addAttackerToList(aet.getAttacker(), aet.getTarget());
        }*/

        if (o instanceof Logout) {
            server.sendToAllTCP(o);
            auh.getActiveAvatars().remove(((Logout) o).getAvatar());
            auh.getActiveUsers().remove(c.getID());
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
        Avatar avatar = DBQueries.getUserAvatar(user.getId());
        System.out.println(avatar.getCharacterClass());
        try {
            Backpack bp = DBQueries.getBackpack(avatar.getId());
            bp.setItems(DBQueries.getBpItems(bp.getId()));
            avatar.setEquippedItems(DBQueries.getEquippedItems(avatar.getId()));
            user.setAvatar(avatar);
        } catch (NullPointerException e) {
            System.out.println("No avatar found for user.");
        }
        return user;
    }
}