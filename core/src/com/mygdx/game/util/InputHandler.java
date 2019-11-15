package com.mygdx.game.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.entities.User;
import com.mygdx.game.entities.avatar.Avatar;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.network.Sender;
import com.mygdx.game.network.networkMessages.AttackEnemyTarget;
import com.mygdx.game.screen.GameScreen;

public class InputHandler implements InputProcessor {

    private Sender sender;
    private Avatar av;

    public InputHandler() {
        av = ClientConnection.getInstance().getActiveAvatars().get(ClientConnection.getInstance().getUser().getAvatar().getId());
        sender = new Sender();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.W) {
            sender.sendInputPressed(Input.Keys.W);
            return true;
        }
        if (keycode == Input.Keys.S) {
            sender.sendInputPressed(Input.Keys.S);
            return true;
        }
        if (keycode == Input.Keys.A) {
            sender.sendInputPressed(Input.Keys.A);
            ;
            return true;
        }
        if (keycode == Input.Keys.D) {
            sender.sendInputPressed(Input.Keys.D);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.W) {
            sender.sendInputReleased(Input.Keys.W);
            return true;
        }
        if (keycode == Input.Keys.S) {
            sender.sendInputReleased(Input.Keys.S);
            return true;
        }
        if (keycode == Input.Keys.A) {
            sender.sendInputReleased(Input.Keys.A);
            ;
            return true;
        }
        if (keycode == Input.Keys.D) {
            sender.sendInputReleased(Input.Keys.D);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
            return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        User user = ClientConnection.getInstance().getUser();
        Vector3 vec = new Vector3(screenX, screenY, 0);
        GameScreen.camera.unproject(vec);
        if (button == Input.Buttons.RIGHT) {
            ClientConnection.getInstance().getActiveMonsters().values().forEach(monster -> {
                if ((monster.getX() + 1 > vec.x && monster.getX() < vec.x) && (monster.getY() + 1 > vec.y && monster.getY() < vec.y)) {
                    if (user.getAvatar().getMarkedUnit() == -1) {
                        user.getAvatar().setMarkedUnit(monster.getId());
                        ClientConnection.getInstance().getActiveAvatars().get(user.getAvatar().getId()).setMarkedUnit(monster.getId());
                    } else if (user.getAvatar().getMarkedUnit() != monster.getId()) {
                        ClientConnection.getInstance().getActiveAvatars().get(user.getAvatar().getId()).setMarkedUnit(monster.getId());
                        user.getAvatar().setMarkedUnit(monster.getId());
                    } else {
                        user.getAvatar().setMarkedUnit(-1);
                        ClientConnection.getInstance().getActiveAvatars().get(user.getAvatar().getId()).setMarkedUnit(-1);

                    }

                    ClientConnection.getInstance().getClient().sendTCP(new AttackEnemyTarget(user.getAvatar().getId(), user.getAvatar().getMarkedUnit()));
                }
            });
        } else if (button == Input.Buttons.LEFT) {
            ClientConnection.getInstance().getItemsOnGround().forEach((floats, item) -> {
                if ((floats[0] +1f > vec.x && floats[0] < vec.x) && (floats[1] +1 > vec.y && floats[1] < vec.y)){
                    if ((floats[0] +2f > av.getX() && floats[0]-1f < av.getX()) && (floats[1] +2f > av.getY() && floats[1]-1f < av.getY())) {
                        //Send loot to server/open loot menu
                        System.out.println("looting: " + item.getName());
                    }
                }
            });
        }
            return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
