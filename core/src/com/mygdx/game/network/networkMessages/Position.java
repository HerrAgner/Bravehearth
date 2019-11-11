package com.mygdx.game.network.networkMessages;

public class Position {

    private Float x = null;
    private Float y = null;
    private int type;
    private int id;
    private String direction;

    public Position(Float x, Float y, int type, String direction){
        this.x = x;
        this.y = y;
        this.type = type;
        this.direction = direction;
    }

    public Position() { }

    public int getId(){
        return this.id;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public String getDirection() { return direction; }
}
