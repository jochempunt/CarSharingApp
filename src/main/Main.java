package main;

import main.Users.LoginSignup;
import main.carSharing.CarBO;
import main.utils.consoleFormat.FORMAT;
import main.utils.consoleFormat.Formatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        LoginSignup logSign = LoginSignup.getInstance();
        CarBO carBO = CarBO.getInstance();


        // logSign.SignUp("ADMIN","LargeRichard");

        //System.out.println(logSign.LogIn("ADMIN","LargeRichard").getMessage());


        System.out.println("Welcome to the CarSharingApp");


        Scanner inputScanner = new Scanner(System.in);

        mainloop:
        while (true) {
            System.out.println("choose an action: " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "E") + " to Exit, " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "A")
                    + " to see All cars, " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "L") + " to Login or " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "S") + " to Signup");
            String input = inputScanner.next();
            switch (input) {
                case "E":
                    System.out.println("goodbye");
                    break mainloop;
                default:
                    System.out.println("unknown input");
                    break;
            }

        }

        //TODO --> search car by name
        //TODO --> view all available cars at specific time
        //TODO --> ensure admin rights
        //TODO --> show all booking and total costs / average
        // finish program


        LocalDate dateE = LocalDate.of(2022, 04, 05);
        LocalTime timeE = LocalTime.of(14, 00);
        int duration = 60;


        //carBO.addCar(cID,"VW ID.Buzz",DriveType.ELECTRIC,LocalTime.of(6,0),120,LocalTime.of(22,30),30.0,40.0);

        //carBO.bookCar(cID,dateE,timeE,duration);

        //System.out.println(carBO.checkIfCarAvailable(cID,dateE,LocalTime.of(12,30),60).getMessage());


        //carBO.getAllCarsSorted().forEach((n)-> System.out.println(n.getDesignation()));


    }


}
