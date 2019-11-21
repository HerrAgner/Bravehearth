package handlers;

import game.GameServer;
import game.MapReader;
import network.networkMessages.Monster;
import network.networkMessages.avatar.Avatar;

import java.util.Map;
import java.util.Random;

public abstract class CollisionHandler {
    private static MapReader mr = GameServer.getInstance().getMapReader();

    public static boolean isMapCollision(float x, float y) {
        if (mr.getMapCollision()
                .get((int) Math.ceil(x))
                .contains((int) Math.ceil(y))
                ||
                mr.getMapCollision()
                        .get((int) Math.ceil(x - 1))
                        .contains((int) Math.ceil(y))
                ||
                mr.getMapCollision()
                        .get((int) Math.ceil(x - 1))
                        .contains((int) Math.ceil(y - 1))
                ||
                mr.getMapCollision()
                        .get((int) Math.ceil(x))
                        .contains((int) Math.ceil(y - 1))) {
            return true;
        }
        return false;
    }

    public static boolean isMonsterCollision(float x, float y) {
        if (GameServer.getInstance().getMh().monsterList.size() == 0) return false;
        for (Map.Entry<Integer, Monster> entry : GameServer.getInstance().getMh().monsterList.entrySet()) {
            Monster monster = entry.getValue();
            return minMax(x, y, monster.getX(), monster.getY());
        }
        return true;
    }

    public static boolean isAvatarCollision(float x, float y) {
        if (GameServer.getInstance().aa.size() == 0) return false;
        for (Map.Entry<Integer, Avatar> entry : GameServer.getInstance().aa.entrySet()) {
            Avatar avatar = entry.getValue();
            return minMax(x, y, avatar.getX(), avatar.getY());
        }
        return true;
    }

    public static boolean isAnyCollision(float x, float y) {
        return (isMonsterCollision(x, y) || isAvatarCollision(x, y) || isMapCollision(x, y));
    }

    private static boolean minMax(float x1, float y1, float x2, float y2) {
        float maxX = Math.max(x1, x2);
        float minX = Math.min(x1, x2);
        float maxY = Math.max(y1, y2);
        float minY = Math.min(y1, y2);
        if (maxX - minX < 1 && maxY - minY < 1) {
            return true;
        }
        return false;
    }

    public static Float[] newValidPosition(float x, float y) {
        Random r = new Random();
        float newX = x;
        float newY = y;
        float radius = 5f;
        while (true) {
            if (!CollisionHandler.isAnyCollision(newX, newY)) {
                return new Float[]{newX, newY};
            }
            newX = (x - radius) + r.nextFloat() * ((x + radius) - (x - radius));
            newY = (y - radius) + r.nextFloat() * ((y + radius) - (y - radius));
        }
    }
}
