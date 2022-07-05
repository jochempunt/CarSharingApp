package main;

import main.Users.LoginSignup;
import main.carSharing.CarBO;
import main.carSharing.DriveType;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {


        LoginSignup logSign = LoginSignup.getInstance();
        CarBO carBO = CarBO.getInstance();
        System.out.println(logSign.SignUp("Bernd", "1234").getMessage());

        System.out.println(logSign.LogIn("Bernd", "1234").getMessage());


        LocalDate dateE = LocalDate.of(2022,04,05);
        LocalTime timeE = LocalTime.of(14, 45);
        int duration = 11;

        String cID = "BMW3er";

        System.out.println(carBO.bookCar(cID,dateE,timeE,duration).getMessage());




    }


}
