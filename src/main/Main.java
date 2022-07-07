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
        LocalTime timeE = LocalTime.of(14, 00);
        int duration = 60;

        String cID = "BMW3er";

        //carBO.addCar(cID,"BMW 3er",DriveType.CONVENTIONAL,LocalTime.of(6,0),120,LocalTime.of(20,30),30.0,40.0);

        //carBO.bookCar(cID,dateE,timeE,duration);

        System.out.println(carBO.checkIfCarAvailable(cID,dateE,LocalTime.of(12,30),60).getMessage());


        //System.out.println(carBO.bookCar(cID,dateE,timeE,duration).getMessage());




    }


}
