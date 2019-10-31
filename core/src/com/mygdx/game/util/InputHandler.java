package com.mygdx.game.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        if(keycode== Input.Keys.W){
            System.out.println("W start");
            return true;
        }
        if(keycode== Input.Keys.S){
            System.out.println("S start");
            return true;
        }
        if(keycode== Input.Keys.A){
            System.out.println("A start");
            return true;
        }
        if(keycode== Input.Keys.D){
            System.out.println("D start");
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.W){
            System.out.println("W end");
            return true;
        }
        if(keycode == Input.Keys.S){
            System.out.println("S end");
            return true;
        }
        if(keycode == Input.Keys.A){
            System.out.println("A end");
            return true;
        }
        if(keycode == Input.Keys.D){
            System.out.println("D end");
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
