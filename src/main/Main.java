package main;

import main.Users.LoginSignup;
import main.carSharing.Car;
import main.carSharing.CarBO;
import main.carSharing.DriveType;
import main.utils.consoleFormat.FORMAT;
import main.utils.consoleFormat.Formatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
            System.out.println("choose an action: " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "s") + " to Signup " +
                    Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "l") + " to Login or " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "a") + " to see All cars, " +
                    Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "f") + " to find a specific car or car-brand " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "e") + " to Exit, ");
            String input = inputScanner.next().toLowerCase();
            switch (input) {
                case "e":
                    System.out.println("goodbye");
                    break mainloop;
                case "f":
                    System.out.println("type in a car model or brand:");

                    String designationS = inputScanner.nextLine();
                    // for optional second word after a space
                    designationS = designationS + inputScanner.nextLine();
                    System.out.println(designationS);
                    DriveType driveType = null;
                    while (true) {
                        System.out.println("choose ur drive type, type " + Formatter.format(FORMAT.YELLOW, "c") + " for conventional" +
                                "and " + Formatter.format(FORMAT.YELLOW, "e") + " for electric");
                        String tempDT = inputScanner.next().toLowerCase();
                        if (tempDT.equals("e")) {
                            driveType = DriveType.ELECTRIC;
                            break;
                        } else if (tempDT.equals("c")) {
                            driveType = DriveType.CONVENTIONAL;
                            break;
                        }
                    }
                    ArrayList<Car> foundCars = carBO.searchCarByDesignation(designationS, driveType);
                    if (foundCars.size() == 0) {
                        System.out.println("sorry we couldnt find any car that matches your search");
                    } else {
                        for (int i = 0; i < foundCars.size(); i++) {
                            System.out.print("[" + i + "]");
                            System.out.println(foundCars.get(i));
                        }
                    }
                    while (true) {
                        System.out.println("Type " + Formatter.format(FORMAT.YELLOW, "a [Number] of a Desired car") + " to check if its  available or press " +
                                Formatter.format(FORMAT.YELLOW, "m") + " to exit to the main menu");
                        String tempInput = inputScanner.next().toLowerCase();
                        if (tempInput.equals("m")) {
                            break;
                        } else {
                            int nr = -1;
                            try {
                                nr = Integer.parseInt(tempInput);
                                System.out.println(Formatter.format(FORMAT.YELLOW, "[" + nr + "]"));
                                break;
                            } catch (NullPointerException | NumberFormatException e) {
                                System.out.println("unkown input");
                            }
                        }
                    }
                    break;

                default:
                    System.out.println("unknown input");
                    break;
            }

        }


        //
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
