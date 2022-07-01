package main;

import main.Users.Client;
import main.utils.consoleFormat.FORMAT;
import main.utils.consoleFormat.Formatter;
import main.utils.jsonHandler;

public class Main {

    public static void main(String[] args) {

        System.out.println(Formatter.format(FORMAT.BLUE, "hellooo"));

        jsonHandler jH = jsonHandler.getInstance();
       Client c = new Client("hallo","1234");
        System.out.println(jH.toJsonObject(c));

    }
}
