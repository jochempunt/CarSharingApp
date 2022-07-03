package main;

import main.Users.LoginSignup;
import main.carSharing.Booking;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {


        LoginSignup logSign = LoginSignup.getInstance();

        System.out.println(logSign.SignUp("Bernd", "1234").getMessage());
        //System.out.println(logSign.SignUp("Franz8iska", "abcd").getMessage());
        System.out.println(logSign.LogIn("Bernd", "1234").getMessage());
        System.out.println(logSign.getCurrentClient().getUsername());
        LocalDate date = LocalDate.of(2022,04,24);
        LocalTime time = LocalTime.of(14,45);
        Booking b = new Booking("TeslaMODS01","Bernd",date,time,480, 50);
        System.out.println(b);


    }


}
