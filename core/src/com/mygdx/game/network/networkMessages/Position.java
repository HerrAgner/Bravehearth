package com.mygdx.game.network.networkMessages;

import java.util.UUID;

public class Position {

    private Float x = null;
    private Float y = null;
    private UUID id;

    public Position(Float x, Float y){
        this.x = x;
        this.y = y;
    }

    public Position() {

    }

    public UUID getId(){
        return this.id;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }
}
