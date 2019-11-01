package com.mygdx.game.network.networkMessages;

public class Position {

    private Float x = null;
    private Float y = null;

    public Position(Float x, Float y){
        this.x = x;
        this.y = y;
    }

    public Position() {

    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }
}
