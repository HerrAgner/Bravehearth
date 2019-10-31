package com.mygdx.game.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.network.Sender;

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
