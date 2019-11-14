package network.networkMessages;

public class Position {

    private Float x = null;
    private Float y = null;
    private int type;
    private int id;
    private String direction;


    public Position(Float x, Float y, int id, int type, String direction){
        this.x = x;
        this.y = y;
        this.id = id;
        this.type = type;
        this.direction = direction;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public String getDirection() { return direction; }
}
