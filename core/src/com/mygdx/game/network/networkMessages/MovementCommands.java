package com.mygdx.game.network.networkMessages;

public class MovementCommands {
    private boolean isKeyPressed;
    private String letter;

    public MovementCommands(boolean isKeyPressed, String letter){
        this.isKeyPressed = isKeyPressed;
        this.letter = letter;
    }

    public MovementCommands(){ }
}
