package handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import database.DBQueries;
import enums.Command;
import game.GameServer;
import network.networkMessages.*;
import network.networkMessages.avatar.Avatar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
//
//                Monster monster = new Monster(5,5,"ANTONMONSTRET");
//                monster.setY(180);
//                monster.setMaxXspeed(1);
//                monster.setMaxYspeed(1);
//                MonsterHandler.monsterList.put(monster.getId(), monster);

//                server.sendToAllTCP(monster);
            }
        }

        if (o instanceof AttackEnemyTarget) {
            AttackEnemyTarget aet = (AttackEnemyTarget) o;
            auh.getActiveAvatars().get(aet.getAttacker()).setMarkedUnit(aet.getTarget());
            ah.addAttackerToList(aet.getAttacker(), aet.getTarget());
        }

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
        Avatar avatar = new Avatar();
        avatar.setCharacterClass(CharacterClass.DUMMYCLASS);
        avatar.setX(1);
        avatar.setY(197);
        avatar.setMaxHealth(30);
        avatar.setAttackDamage(1);
        avatar.setAttackSpeed((float) 1.5);
        avatar.setAttackRange((float) 1.5);
        avatar.setHealth(avatar.getMaxHealth());
        avatar.setId(UUID.randomUUID());

        User user = DBQueries.getMatchingUser(loginObject.getUsername(), loginObject.getPassword());
        try {

            user.setAvatar(avatar);
        } catch (NullPointerException e) {
            System.out.println("No avatar found on user.");
        }
        return user;
    }
}