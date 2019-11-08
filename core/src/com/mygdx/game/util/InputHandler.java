package com.mygdx.game.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.entities.User;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.network.Sender;
import com.mygdx.game.screen.GameScreen;

public class InputHandler implements InputProcessor {

    private Sender sender;

    public InputHandler() {

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
            sender.sendInputPressed(Input.Keys.A);;
            return true;
        }
        if (keycode == Input.Keys.D) {
            sender.sendInputPressed(Input.Keys.D);
            return true;
        }if( keycode == Input.Keys.Q){
            ClientConnection.getInstance().getActiveAvatars().forEach((k, v) -> {
                v.setHurt(true);
            });
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
            sender.sendInputReleased(Input.Keys.A);;
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
        Vector3 vec=new Vector3(screenX,screenY,0);
//        GameScreen.camera.unproject(vec);
       /*if (button == Input.Buttons.RIGHT) {
           ClientConnection.getInstance().getActiveAvatars().values().forEach(avatar -> {
               if ((avatar.getX()+1 > vec.x && avatar.getX()-1 < vec.x) && (avatar.getY()+1 > vec.y && avatar.getY()-1 < vec.y)) {
                   if (avatar.getId() != (user.getAvatar().getId())) {
                       if (user.getAvatar().getMarkedUnit() == null) {
                           user.getAvatar().setMarkedUnit(avatar.getId());
                       } else if (!user.getAvatar().getMarkedUnit().equals(avatar.getId())) {
                           user.getAvatar().setMarkedUnit(avatar.getId());
                       } else {
                           user.getAvatar().setMarkedUnit(null);
                       }
                   }
               }
           });
       }*/
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
