package main;

import main.Users.LoginSignup;
import main.carSharing.Car;
import main.carSharing.CarBO;
import main.carSharing.DriveType;

import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {


        LoginSignup logSign = LoginSignup.getInstance();
        CarBO carBO = CarBO.getInstance();
        System.out.println(logSign.SignUp("Bernd", "1234").getMessage());
        //System.out.println(logSign.SignUp("Franz8iska", "abcd").getMessage());
        System.out.println(logSign.LogIn("Bernd", "1234").getMessage());
        System.out.println(logSign.getCurrentClient().getUsername());

        LocalTime timeE = LocalTime.of(14, 45);
        LocalTime timeL = LocalTime.of(21, 30);


        System.out.println(carBO.addCar("MECA01", "Mercedes A klasse", DriveType.CONVENTIONAL, timeE, timeL, 30.0, 20.5).getMessage());
        carBO.getCallCars().forEach((n)-> System.out.println(n.getDesignation()));

    }


}
