package main;

import main.Users.LoginSignup;
import main.carSharing.Car;
import main.carSharing.CarBO;
import main.carSharing.DriveType;
import main.utils.DateTimeManager;
import main.utils.Response;
import main.utils.ResponseWithDate;
import main.utils.consoleFormat.FORMAT;
import main.utils.consoleFormat.Formatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    LocalDate dateE = LocalDate.of(2022, 04, 05);
    LocalTime timeE = LocalTime.of(14, 00);
    int duration = 60;


    //TODO --> view all available cars at specific time
    //TODO --> ensure admin rights
    //TODO --> show all booking and total costs / average
    // finish program

    public static boolean isInteger(String s) {
        return s.matches("\\d+");
    }

    //function that returns validated console input as  Date and Time , entry[0] = date, entry[1]= time
    public static String[] inputDateTime(Scanner sc) {
        String[] dateTimeArray = new String[2];
        boolean invalidDate = true;
        while (invalidDate) {
            System.out.println("input a desired dat and time , in this format:" +
                    Formatter.format(FORMAT.BLUE, "dd-mm-yy hh:mm") + " e.g: " +
                    "24-04-2000 20:30");
            String dateInput = sc.next();
            String timeInput = sc.next();
            String dateTimeInput = dateInput + " " + timeInput;
            System.out.println("dateTime = " + dateTimeInput);
            if (DateTimeManager.isDateTime(dateTimeInput)) {
                invalidDate = false;
                dateTimeArray[0] = dateInput;
                dateTimeArray[1] = timeInput;
            } else {
                System.out.println("invalid date, try again \n" +
                        Formatter.format(FORMAT.YELLOW, "hint: a year has only 12 month and a month can have a maximum of 31 days"));
            }
        }
        return dateTimeArray;
    }


    public static Response validateAvailability(Car car, Scanner sc) {
        //Scanner sc = new Scanner(System.in);
        LocalTime time = null;
        LocalDate date = null;
        int duration = 0;

        String[] dateTimeArray = inputDateTime(sc);
        if (dateTimeArray.length == 2) {
            date = DateTimeManager.getDateFromString(dateTimeArray[0]);
            time = DateTimeManager.getTimeFromString(dateTimeArray[1]);
        }

        boolean invalidDuration = true;

        while (invalidDuration) {
            System.out.println("input desired duration in minutes e.g: 60");
            String durationInput = sc.next();
            if (isInteger(durationInput)) {
                duration = Integer.parseInt(durationInput);
                if (duration > 0) {
                    invalidDuration = false;
                } else {
                    System.out.println("duration cant be 0");
                }
            }
        }
        Response response = CarBO.getInstance().checkIfCarAvailable(car.getId(), date, time, duration);
        if (response.isSuccess()) {
            response = new ResponseWithDate(response, date, time, duration);
        }

        return response;
    }

    public static void main(String[] args) {


        LoginSignup logSign = LoginSignup.getInstance();
        CarBO carBO = CarBO.getInstance();

        Scanner inputScanner = new Scanner(System.in);

        // logSign.SignUp("ADMIN","LargeRichard");

        //System.out.println(logSign.LogIn("ADMIN","LargeRichard").getMessage());


        System.out.println("Welcome to the CarSharingApp");


        mainloop:
        while (true) {
            System.out.println("choose an action: " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "s") + " to Signup " +
                    Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "l") + " to Login or " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "a") + " to see All cars, " +
                    Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "f") + " to find a specific car or car-brand " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "e") + " to Exit, ");
            String input = inputScanner.next().toLowerCase();
            switch (input) {
                case "e": //--------------------------------- Exit-----------------------------------//
                    System.out.println("goodbye");
                    break mainloop;
                case "f": //-------------------------------- Find Car -------------------------------//
                    System.out.println("type in a car model or brand:");
                    String designationS = inputScanner.nextLine();
                    // for optional second word after a space
                    designationS = designationS + inputScanner.nextLine();
                    DriveType driveType;
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
                        System.out.println("didnt recognise the input");
                    }
                    ArrayList<Car> foundCars = carBO.searchCarByDesignation(designationS, driveType);
                    if (foundCars.size() == 0) {
                        System.out.println("sorry we couldn't find any car that matches your search");
                    } else {
                        for (int i = 0; i < foundCars.size(); i++) {
                            System.out.print("[" + i + "]");
                            System.out.println(foundCars.get(i));
                        }

                        boolean validated = false;
                        validationLoop:
                        while (!validated) {
                            System.out.println("Type " + Formatter.format(FORMAT.YELLOW, "a Number of a Desired car") + " to check if its  available or press " +
                                    Formatter.format(FORMAT.YELLOW, "m") + " to exit to the main menu");
                            String tempInput = inputScanner.next().toLowerCase();
                            int nr;
                            if (tempInput.equals("m")) {
                                break validationLoop;
                            } else {
                                if (isInteger(tempInput)) {
                                    nr = Integer.parseInt(tempInput);

                                    Car currentCar = foundCars.get(nr);
                                    System.out.println(Formatter.format(FORMAT.BLUE,currentCar.getDesignation()));

                                    Response available = validateAvailability(currentCar, inputScanner);

                                    if (available.isSuccess()) {
                                        System.out.println(Formatter.format(FORMAT.GREEN, available.getMessage()));

                                        if (logSign.getCurrentClient() != null) {
                                            System.out.println("logged in --");
                                            ResponseWithDate responseWithD = (ResponseWithDate) available;
                                            System.out.println(responseWithD.getDate() + " " + responseWithD.getTime() + " " + responseWithD.getDuration() + "min");
                                            // Todo --> with the data Book the Car
                                        } else {
                                            System.out.println("to be able to book this car you must first log in (or sign up");
                                        }
                                    } else {
                                        System.out.println(Formatter.format(FORMAT.RED, available.getMessage()));
                                    }
                                    validated = true;
                                } else {
                                    System.out.println("unknown input");
                                }
                            }
                            // TODO --> Login

                        }
                    }
                    break;

                default:
                    System.out.println("unknown input");
                    break;
            }

        }


        //carBO.addCar(cID,"VW ID.Buzz",DriveType.ELECTRIC,LocalTime.of(6,0),120,LocalTime.of(22,30),30.0,40.0);

        //carBO.bookCar(cID,dateE,timeE,duration);

        //System.out.println(carBO.checkIfCarAvailable(cID,dateE,LocalTime.of(12,30),60).getMessage());


        //carBO.getAllCarsSorted().forEach((n)-> System.out.println(n.getDesignation()));


    }


}
