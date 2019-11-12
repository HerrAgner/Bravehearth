package handlers;

import game.GameServer;
import game.MapReader;
import network.networkMessages.Monster;
import network.networkMessages.avatar.Avatar;

import java.util.Map;

public class CollisionHandler {
    private MapReader mr = GameServer.getInstance().getMapReader();

    public boolean isMapCollision(int x, int y) {
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

    public boolean isMonsterCollision(float x, float y) {
        for (Map.Entry<Integer, Monster> entry : GameServer.getInstance().getMh().monsterList.entrySet()) {
            Monster monster = entry.getValue();
            return minMax(x, y, monster.getX(), monster.getY());
        }
        return true;
    }

    public boolean isAvatarCollision(float x, float y) {
        for (Map.Entry<Integer, Avatar> entry : GameServer.getInstance().aa.entrySet()) {
            Avatar avatar = entry.getValue();
            return minMax(x, y, avatar.getX(), avatar.getY());
        }
        return true;
    }

    private boolean minMax(float x1, float y1, float x2, float y2) {
        float maxX = Math.max(x1, x2);
        float minX = Math.min(x1, x2);
        float maxY = Math.max(y1, y2);
        float minY = Math.min(y1, y2);
        if (maxX - minX < 1 && maxY - minY < 1) {
            return true;
        }
        return false;
    }
}
