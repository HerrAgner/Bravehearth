package network.networkMessages;

import java.util.UUID;

public class Position {

    private Float x = null;
    private Float y = null;
    private UUID id;
    private int type;


    public Position(Float x, Float y, UUID id, int type){
        this.x = x;
        this.y = y;
        this.id = id;
        this.type = type;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }
}
