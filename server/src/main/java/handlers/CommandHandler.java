package handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import database.DBQueries;
import game.GameServer;
import network.networkMessages.*;
import network.networkMessages.avatar.Avatar;
import network.networkMessages.avatar.Backpack;
import network.networkMessages.items.Item;
import network.networkMessages.items.Weapon;
import network.networkMessages.items.Wearable;

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
        }

        if (o instanceof Logout) {
            server.sendToAllTCP(o);
            DBQueries.saveAvatarOnLevelUp(auh.getActiveAvatars().get(((Logout) o).getAvatar()));
            auh.getActiveAvatars().remove(((Logout) o).getAvatar());
            auh.getActiveUsers().remove(c.getID());
            GameServer.getInstance().getMh().monsterList.forEach((integer, monster) -> {
                if (monster.getMarkedUnit() == ((Logout) o).getAvatar()) {
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
            DBQueries.saveToBackpack(GameServer.getInstance().aa.get(((ItemPickup) o).getAvatarId()).getBackpack().getId(), ((ItemPickup) o).getItem());
            GameServer.getInstance().aa.get(((ItemPickup) o).getAvatarId()).getBackpack().getItems().add(((ItemPickup) o).getItem());
            GameServer.getInstance().getServer().sendToAllTCP(o);
        }

        if (o instanceof ItemDrop) {
            if (((ItemDrop) o).getAvatarId() != -1) {
                GameServer.getInstance().getMh().itemsOnGround.put(new Float[]{GameServer.getInstance().aa.get(((ItemDrop) o).getAvatarId()).getX(), GameServer.getInstance().aa.get(((ItemDrop) o).getAvatarId()).getY()}, ((ItemDrop) o).getItem());
                DBQueries.removeFromBackpack(GameServer.getInstance().aa.get(((ItemDrop) o).getAvatarId()).getBackpack().getId(), ((ItemDrop) o).getItem());
                GameServer.getInstance().aa.get(((ItemDrop) o).getAvatarId()).getBackpack().getItems().remove(((ItemDrop) o).getId());
                GameServer.getInstance().getServer().sendToAllTCP(o);
            }
        }

        if (o instanceof EquippedItemChange) {
            Avatar av = GameServer.getInstance().aa.get(((EquippedItemChange) o).getAvatarId());
            if (((EquippedItemChange) o).isUnequipping()) {
                DBQueries.saveToBackpack(av.getBackpack().getId(), ((EquippedItemChange) o).getItem());
                Item item = av.getEquippedItems().getItems().remove(((EquippedItemChange) o).getType());
                av.getBackpack().getItems().add(item);
                GameServer.getInstance().getServer().sendToTCP(connection.getID(), o);
            } else {
                Item previousItem = GameServer.getInstance().aa.get(((EquippedItemChange) o).getAvatarId()).getEquippedItems().getItems().remove(((EquippedItemChange) o).getType());
                DBQueries.removeFromBackpack(av.getBackpack().getId(), ((EquippedItemChange) o).getItem());
                av.getBackpack().getItems().add(previousItem);
                av.getEquippedItems().getItems().put(((EquippedItemChange) o).getType(), ((EquippedItemChange) o).getItem());
                av.getBackpack().getItems().remove(((EquippedItemChange) o).getItem());
                GameServer.getInstance().getServer().sendToTCP(connection.getID(), o);
            }
            DBQueries.saveEquippedItems(av.getId(), av.getEquippedItems());
            Avatar av2 = addEquippedItemStatsToAvatar(GameServer.getInstance().aa.get(((EquippedItemChange) o).getAvatarId()));
            GameServer.getInstance().getServer().sendToTCP(connection.getID(), new AvatarStatChange(av2));
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
            addEquippedItemStatsToAvatar(avatar);
            Float[] position = CollisionHandler.newValidPosition(avatar.getX(), avatar.getY());
            avatar.setPosition(position[0], position[1]);
            user.setAvatar(avatar);
        } catch (NullPointerException e) {
            System.out.println("No avatar found for user.");
        }
        return user;
    }

    private Avatar addEquippedItemStatsToAvatar(Avatar avatar) {
        Avatar av = avatar;
        var ref = new Object() {
            float newDefence = 0;
            float attackRange = 0;
            int attackDamage = 0;
            float attackSpeed = 0;
        };
        av.getEquippedItems().getItems().forEach((o, o2) -> {
            switch (o) {
                case ACCESSORY:
                case FEET:
                case HEAD:
                case LEGS:
                case CHEST:
                    Wearable wearable = (Wearable) o2;
                    ref.newDefence += wearable.getDefence();
                    break;
                case WEAPON:
                    Weapon weapon = (Weapon) o2;
                    ref.attackDamage = weapon.getDamage();
                    ref.attackRange = weapon.getRange();
                    ref.attackSpeed = weapon.getSpeed();
                    break;
                default:
            }
        });

        av.setDefense(ref.newDefence);
        av.setAttackDamage(ref.attackDamage);
        av.setAttackSpeed(ref.attackSpeed);
        av.setAttackRange(ref.attackRange);
        return av;
    }
}