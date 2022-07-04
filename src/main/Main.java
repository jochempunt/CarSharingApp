package main;

import main.Users.LoginSignup;
import main.carSharing.Booking;
import main.carSharing.Car;
import main.carSharing.CarBO;
import main.carSharing.DriveType;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {


        LoginSignup logSign = LoginSignup.getInstance();
        CarBO carBO = CarBO.getInstance();
        System.out.println(logSign.SignUp("Bernd", "1234").getMessage());
        //System.out.println(logSign.SignUp("Franz8iska", "abcd").getMessage());
        System.out.println(logSign.LogIn("Bernd", "1234").getMessage());
        System.out.println(logSign.getCurrentClient().getUsername());

        LocalTime timeE = LocalTime.of(14,45);
        LocalTime timeL = LocalTime.of(21,30);

        Car c = new Car("TEMOD2","Tesla Model 2", DriveType.ELECTRIC,timeE,timeL,10.0,50.0);
        System.out.println(c.toJson());

        carBO.addCar("TEMOD2","Tesla Model X", DriveType.ELECTRIC,timeE,timeL,50.0,100.5);



    }


}
