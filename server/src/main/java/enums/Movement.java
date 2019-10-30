package enums;

public enum Movement {
    FORWARD("W"),
    BACKWARD("S"),
    LEFT("A"),
    RIGHT("D");

    public final String direction;

    private Movement(String direction) {
        this.direction = direction;
    }
}