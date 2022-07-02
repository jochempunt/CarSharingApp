package main;

import main.Users.LoginSignup;

public class Main {

    public static void main(String[] args) {


        LoginSignup logSign = LoginSignup.getInstance();

        System.out.println(logSign.SignUp("Bernd", "1234").getMessage());
        System.out.println(logSign.LogIn("Bernd", "1234").getMessage());


    }


}
