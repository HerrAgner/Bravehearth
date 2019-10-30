package enums;

public enum Command {
    //add more commands and decide on keys for them later
    ATTACK("H"),
    SPELL("J"),
    BACKPACK("K"),
    MAP("L");

    public final String command;

    private Command(String command) {
        this.command = command;
    }
}

