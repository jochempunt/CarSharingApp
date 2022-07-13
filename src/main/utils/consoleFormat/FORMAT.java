package main.utils.consoleFormat;

public enum FORMAT {
    BOLD("\u001B[1m"), UNDERSCORE("\u001B[4m"), RED("\u001B[31m"),
    GREEN("\u001B[32m"), BLUE("\u001B[34m"), YELLOW("\u001B[33m");
    public final String code;

    FORMAT(String _code) {
        this.code = _code;
    }
}
//Admin bookings error