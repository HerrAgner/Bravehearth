package network.networkMessages;

public class Position {

    private Float x = null;
    private Float y = null;
    private int type;
    private int id;


    public Position(Float x, Float y, int id, int type){
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
