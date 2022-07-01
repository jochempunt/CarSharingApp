package main.consoleFormat;

public final class Formatter {

    private static final String ANSI_RESET = "\u001B[0m";

    private Formatter() {
    }

    public static String format(FORMAT _format1, String _text) {
        return _format1.code + _text + ANSI_RESET;

    }

    public static String format(FORMAT _format1, FORMAT _format2, String _text) {
        return _format1.code + _format2.code + _text + ANSI_RESET;
    }


    public static String format(FORMAT _format1, FORMAT _format2, FORMAT _format3, String _text) {
        return _format1.code + _format2.code + _format3.code + _text + ANSI_RESET;
    }


}

