package enums;

import java.util.HashMap;
import java.util.Map;

public enum Movement {
    FORWARD('W'),
    BACKWARD('S'),
    LEFT('A'),
    RIGHT('D');

    private static final Map<Character, Movement> CODE = new HashMap<>();

    static {
        for (Movement c : values()) {
            CODE.put(c.shortCode, c);
        }
    }

    private final char shortCode;

    private Movement(char shortCode) {
        this.shortCode = shortCode;
    }

    public static Movement getMovementFromChar(char code) {
        return CODE.get(code);
    }
}