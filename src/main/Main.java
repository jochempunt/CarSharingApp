package main;

import main.Users.LoginSignup;
import main.utils.Encryptor;
import main.utils.jsonHandler;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Main {

    public static void main(String[] args) {



        jsonHandler jH = jsonHandler.getInstance();

        String usrname = "a1A111";
        System.out.println(LoginSignup.validNewUsername(usrname));



    }


}
