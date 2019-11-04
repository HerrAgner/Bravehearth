package network.networkMessages;

import java.util.UUID;

public class Position {

    private Float x = null;
    private Float y = null;
    private UUID id;


    public Position(Float x, Float y, UUID id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }
}
