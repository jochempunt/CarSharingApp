package main;

import main.Users.LoginSignup;
import main.carSharing.Booking;
import main.carSharing.Car;
import main.carSharing.CarBookingBO;
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


    public static boolean isDouble(String s) {
        return s.matches("([0-9]{1,13}(\\.[0-9]*))?");
    }

    public static boolean isInteger(String s) {
        return s.matches("\\d+");
    }


    //function that returns validated console input as  Date and Time , entry[0] = date, entry[1]= time
    public static Response bookCar(Car car, LocalDate date, LocalTime time, int duration, Scanner sc) {
        double price = duration * car.getPricePerMinute() + car.getInitialFee();

        System.out.println("Do you want to book " + car.getDesignation() + " for a total of " + price + ",-" +
                "\nYes " + Formatter.format(FORMAT.YELLOW, "y") + " No " + Formatter.format(FORMAT.YELLOW, "n"));
        String acceptInput = sc.next();
        switch (acceptInput) {
            case "y":
                return CarBookingBO.getInstance().bookCar(car.getId(), date, time, duration);
            case "n":
                break;
            default:
                return new Response(false, "unknown input, please try again");
        }
        return null;
    }

    public static String[] inputDateTimeDuration(Scanner sc) {
        String[] dateTimeArray = new String[3];
        boolean invalidDate = true;
        while (invalidDate) {
            System.out.println("input a desired date and time , in this format:" +
                    Formatter.format(FORMAT.BLUE, "dd-mm-yyyy hh:mm") + " e.g: " +
                    "24-04-2000 20:30");
            String dateInput = sc.next();
            String timeInput = sc.next();
            String dateTimeInput = dateInput + " " + timeInput;
            if (DateTimeManager.isDateTime(dateTimeInput)) {
                invalidDate = false;
                dateTimeArray[0] = dateInput;
                dateTimeArray[1] = timeInput;
            } else {
                System.out.println("invalid date, try again \n" +
                        Formatter.format(FORMAT.YELLOW, "hint: a year has only 12 month and a month can have a maximum of 31 days"));
            }
        }

        boolean invalidDuration = true;
        int duration = -1;
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
        dateTimeArray[2] = duration + "";
        return dateTimeArray;
    }

    public static Response validateAvailability(Car car, Scanner sc) {
        //Scanner sc = new Scanner(System.in);
        LocalTime time = null;
        LocalDate date = null;
        int duration = 0;

        String[] dateTimeDuration = inputDateTimeDuration(sc);
        if (dateTimeDuration.length == 3) {
            date = DateTimeManager.getDateFromString(dateTimeDuration[0]);
            time = DateTimeManager.getTimeFromString(dateTimeDuration[1]);
            duration = Integer.parseInt(dateTimeDuration[2]);
        }


        Response response = CarBookingBO.getInstance().checkIfCarAvailable(car.getId(), date, time, duration);
        if (response.isSuccess()) {
            response = new ResponseWithDate(response, date, time, duration);
        }

        return response;
    }

    //---------------------------------------- Main ---------------------------------------------//
    public static void main(String[] args) {

        LoginSignup logSign = LoginSignup.getInstance();
        CarBookingBO carBookingBO = CarBookingBO.getInstance();
        Scanner inputScanner = new Scanner(System.in);
        // logSign.SignUp("ADMIN","LargeRichard");

        System.out.println("Welcome to the CarSharingApp");


        mainloop:
        while (true) {

            String specialFeaturesString;
            if (logSign.getCurrentClient() == null) {
                specialFeaturesString = Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "s") + " to Signup " +
                        Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "l") + " to Login ";
            } else {
                specialFeaturesString = Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "b") + " to see your previous Bookings, " +
                        Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "c") + " see cost Statistics, ";

                if (logSign.getCurrentClient().getUsername().equals("ADMIN")) {
                    specialFeaturesString += Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "ac") + " too add a new car, ";
                }
            }


            String menuString = "choose an action: " + specialFeaturesString + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "a") + " to see All cars, " +
                    Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "f") + " to find a specific car or car-brand " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "v") +
                    " to find all available cars at desired time " + Formatter.format(FORMAT.BOLD, FORMAT.YELLOW, "e") + " to Exit, ";


            System.out.println(menuString);
            String input = inputScanner.next().toLowerCase();
            switch (input) {
                case "e": //--------------------------------- Exit-------------------------------------//
                    System.out.println("goodbye");
                    inputScanner.close();
                    break mainloop;
                case "f": //-------------------------------- Find Car ---------------------------------//
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
                    ArrayList<Car> foundCars = carBookingBO.searchCarByDesignation(designationS, driveType);
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
                                    System.out.println(Formatter.format(FORMAT.BLUE, currentCar.getDesignation()));

                                    Response available = validateAvailability(currentCar, inputScanner);

                                    if (available.isSuccess()) {
                                        System.out.println(Formatter.format(FORMAT.GREEN, available.getMessage()));

                                        if (logSign.getCurrentClient() != null) {
                                            ResponseWithDate responseWithD = (ResponseWithDate) available;
                                            System.out.println(responseWithD.getDate() + " " + responseWithD.getTime() + " " + responseWithD.getDuration() + "min");
                                            //------------------------------------ book found car---------------------------//
                                            Response bookingResponse = bookCar(currentCar, responseWithD.getDate(),
                                                    responseWithD.getTime(), responseWithD.getDuration(), inputScanner);
                                            if (bookingResponse != null) {
                                                FORMAT responseFormat;
                                                if (bookingResponse.isSuccess()) {
                                                    responseFormat = FORMAT.GREEN;
                                                } else {
                                                    responseFormat = FORMAT.RED;
                                                }
                                                System.out.println(Formatter.format(responseFormat, bookingResponse.getMessage()));
                                            }
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
                        }
                    }
                    break;
                case "l"://-------------------------------------------------------LogIn-----------------------------------------------//
                    if (logSign.getCurrentClient() != null) {
                        System.out.println("unknown input");
                        break;
                    }
                    System.out.println("Type in username:");
                    String username = inputScanner.next();
                    System.out.println("Type in password");
                    String password = inputScanner.next();
                    FORMAT format;
                    Response loginResponse = logSign.LogIn(username, password);
                    if (loginResponse.isSuccess()) {
                        format = FORMAT.GREEN;
                    } else {
                        format = FORMAT.RED;
                    }
                    System.out.println(Formatter.format(format, loginResponse.getMessage()));
                    break;
                case "s"://--------------------------------------------------------SignUp-----------------------------------------------//
                    if (logSign.getCurrentClient() != null) {
                        System.out.println("unknown input");
                        break;
                    }

                    System.out.println("type in a username (caution: the username is case insensitive " +
                            "and needs to be made of alphanumeric values"+  Formatter.format(FORMAT.BLUE,"(a-z, 0-9)"));
                    String newUsername = inputScanner.next();
                    System.out.println("Type in a password");
                    String newPassword = inputScanner.next();
                    FORMAT errorFormatSign;
                    Response signUpResponse = logSign.SignUp(newUsername, newPassword);
                    String hint;
                    if (signUpResponse.isSuccess()) {
                        errorFormatSign = FORMAT.GREEN;
                        hint = " continue to login";
                    } else {
                        errorFormatSign = FORMAT.RED;
                        hint = "try again";
                    }
                    System.out.println(Formatter.format(errorFormatSign, signUpResponse.getMessage()) + " " + hint);
                    break;
                case "a": //------------------------------------------------ Show All Cars -----------------------------------------------------""
                    ArrayList<Car> sortedCars = carBookingBO.getAllCarsSorted();
                    boolean moreCarsToShow = true;
                    int carpointer = 0;
                    while (moreCarsToShow) {
                        carBookingBO.showTenCars(sortedCars, carpointer);
                        if (sortedCars.size() > 10 + carpointer) {
                            System.out.println("show next page of cars, type: " + Formatter.format(FORMAT.YELLOW, ">") + " else press " + Formatter.format(FORMAT.YELLOW, "ANY KEY"));
                            String inputNext = inputScanner.next();
                            if (!inputNext.equals(">")) {
                                moreCarsToShow = false;
                            } else {
                                carpointer += 10;
                            }

                        } else {
                            moreCarsToShow = false;
                        }
                    }
                    System.out.println("Type " + Formatter.format(FORMAT.YELLOW, "a Number of a Desired car") + " to check if its  available and book it or press " +
                            Formatter.format(FORMAT.YELLOW, "m") + " to exit to the main menu");
                    String tempInputString = inputScanner.next().toLowerCase();
                    int nr;
                    boolean validated = false;
                    while (!validated)
                        if (isInteger(tempInputString)) {
                            nr = Integer.parseInt(tempInputString);
                            if (nr >= sortedCars.size()) {
                                System.out.println("Pick a number that matches a car please");
                            } else {

                                Car tempCar = sortedCars.get(nr);
                                Response available = validateAvailability(tempCar, inputScanner);
                                validated = true;

                                if (available.isSuccess()) {
                                    System.out.println(Formatter.format(FORMAT.GREEN, available.getMessage()));
                                    ResponseWithDate dateTimeOfAvailability = (ResponseWithDate) available;
                                    if (logSign.getCurrentClient() != null) {
                                        Response bookingResponse = bookCar(tempCar, dateTimeOfAvailability.getDate(), dateTimeOfAvailability.getTime(), dateTimeOfAvailability.getDuration(), inputScanner);
                                        if (bookingResponse != null) {
                                            if (bookingResponse.isSuccess()) {
                                                System.out.println(Formatter.format(FORMAT.GREEN, bookingResponse.getMessage()));
                                            } else {
                                                System.out.println(Formatter.format(FORMAT.RED, bookingResponse.getMessage()));
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println(Formatter.format(FORMAT.RED, available.getMessage()));
                                }
                            }

                        } else if (tempInputString.equals("m")) {
                            break;
                        } else {
                            System.out.println("unknown input");
                        }
                    break;
                case "v"://---------------------------------------- find all available cars ------------------------------//
                    String[] dateTimeDuration = inputDateTimeDuration(inputScanner);
                    LocalDate date = DateTimeManager.getDateFromString(dateTimeDuration[0]);
                    LocalTime time = DateTimeManager.getTimeFromString(dateTimeDuration[1]);
                    int duration = Integer.parseInt(dateTimeDuration[2]);
                    ArrayList<Car> availableCars = carBookingBO.getAvailableCars(date, time, duration);
                    for (int i = 0; i < availableCars.size(); i++) {
                        System.out.println("[" + i + "]" + availableCars.get(i));
                    }
                    if (logSign.getCurrentClient() != null) {
                        System.out.println("To book a desired car type its " + Formatter.format(FORMAT.YELLOW, "number") + " to get back to the menu type " + Formatter.format(FORMAT.YELLOW, "m"));
                        boolean isBooked = false;
                        String bookingInput = inputScanner.next();
                        while (!isBooked) {
                            if (bookingInput.equals("m")) {
                                break;
                            } else if (isInteger(bookingInput)) {
                                int carNr = Integer.parseInt(bookingInput);
                                if (carNr >= 0 && carNr < availableCars.size()) {
                                    Response bookingResponse = bookCar(availableCars.get(carNr), date, time, duration, inputScanner);
                                    isBooked = true;
                                    if (bookingResponse != null) {
                                        FORMAT bookingFormat;
                                        if (bookingResponse.isSuccess()) {
                                            bookingFormat = FORMAT.GREEN;
                                        } else {
                                            bookingFormat = FORMAT.RED;
                                        }
                                        System.out.println(Formatter.format(bookingFormat, bookingResponse.getMessage()));
                                    }
                                }

                            }
                        }
                    } else {
                        System.out.println("to book a car please log in or sign up");
                    }
                    break;
                case "b"://----------------------------------------- show all bookings -----------------------------""
                    if (logSign.getCurrentClient() == null) {
                        System.out.println("unknown input");
                        break;
                    }
                    ArrayList<Booking> myBookings = carBookingBO.getClientsBookings(logSign.getCurrentClient().getUsername());
                    if (myBookings.size() == 0) {
                        System.out.println("this Account has Bookings yet");
                        break;
                    }

                    for (int i = 0; i < myBookings.size(); i++) {
                        Booking currentBooking = myBookings.get(i);
                        FORMAT titleFormat;
                        Car currentCar = carBookingBO.getCarById(currentBooking.getCarID());
                        if (LocalDate.now().isAfter(currentBooking.getDate()) || (LocalDate.now().isEqual(currentBooking.getDate()) && LocalTime.now().isAfter(currentBooking.getTime()))) {
                            titleFormat = FORMAT.UNDERSCORE;
                        } else {
                            titleFormat = FORMAT.BOLD;
                        }
                        System.out.println("[" + i + "]" + Formatter.format(titleFormat, FORMAT.BLUE, currentCar.getDesignation()));
                        System.out.println(DateTimeManager.DateToString(currentBooking.getDate()) + " " + DateTimeManager.timeToString(currentBooking.getTime()));
                        System.out.println(currentBooking.getDurationInMins() + "-min");
                        System.out.println(currentBooking.getTotalCost() + ",-");
                    }
                    System.out.println("\nhint: bookings in the past, have an " + Formatter.format(FORMAT.BLUE, FORMAT.UNDERSCORE, "underscore") +
                            ", bookings in the future are " + Formatter.format(FORMAT.BLUE, FORMAT.BOLD, "bold"));
                    break;
                case "c"://--------------------------------------- show cost Statistics -----------------------------------------//
                    if (logSign.getCurrentClient() == null) {
                        System.out.println("unknown input");
                        break;
                    }
                    ArrayList<Booking> clientBookings = carBookingBO.getClientsBookings(logSign.getCurrentClient().getUsername());
                    if (clientBookings.size() > 0) {
                        double totalcosts = 0.0;
                        double avgCostperBooking;
                        for (Booking clientBooking : clientBookings) {
                            totalcosts += clientBooking.getTotalCost();
                        }
                        avgCostperBooking = totalcosts / clientBookings.size();
                        System.out.println("Your total costs are: " + Formatter.format(FORMAT.BLUE, totalcosts + ",-"));
                        System.out.println("The average cost of your bookings is: " + Formatter.format(FORMAT.BLUE, avgCostperBooking + ",-"));
                    }
                    break;
                case "ac":// --------------------------------------- add Car----------------------------------//
                    if (logSign.getCurrentClient() == null) {
                        System.out.println("unkown input");
                        break;
                    } else if (!logSign.getCurrentClient().getUsername().equals("ADMIN")) {
                        System.out.println("unknown input");
                        break;
                    }
                    boolean notUniqueID = true;
                    String newCarID = "";
                    while (notUniqueID) {
                        System.out.println("ADD a unique Car-ID e.g: " + Formatter.format(FORMAT.YELLOW, "VWTP1") + " for the: " +
                                Formatter.format(FORMAT.BLUE, "VW Transporter"));
                        newCarID = inputScanner.next();
                        if (carBookingBO.isUniqueId(newCarID)) {
                            notUniqueID = false;
                        } else {
                            System.out.println("ID is already Taken");
                        }
                    }

                    System.out.println("Enter a car Designation (Brand + Car name) eg:" +
                            Formatter.format(FORMAT.BLUE, "VW Transporter"));
                    String newCarDesignation = inputScanner.next();
                    newCarDesignation += inputScanner.next();

                    boolean chosenDT = false;
                    DriveType newCarDT = null;
                    while (!chosenDT) {
                        System.out.println("choose a type of drive, type:" + Formatter.format(FORMAT.YELLOW, "e") + " for Electric and " +
                                Formatter.format(FORMAT.YELLOW, "c") + " for conventional");
                        String inputDTString = inputScanner.next();
                        if (inputDTString.equals("e")) {
                            newCarDT = DriveType.ELECTRIC;
                            chosenDT = true;
                        } else if (inputDTString.equals("c")) {
                            newCarDT = DriveType.CONVENTIONAL;
                            chosenDT = true;
                        } else {
                            System.out.println("unkown input, try again");
                        }
                    }

                    boolean correctTimeE = false;
                    LocalTime newCarEarliest = null;
                    while (!correctTimeE) {
                        System.out.println("Enter an earliest time the car can be booked at, in the format" +
                                Formatter.format(FORMAT.BLUE, "hh:mm"));
                        String timeString = inputScanner.next();
                        if (DateTimeManager.isTime(timeString)) {
                            newCarEarliest = DateTimeManager.getTimeFromString(timeString);
                            correctTimeE = true;
                        } else {
                            System.out.println("Please type in a correct time");
                        }
                    }

                    boolean correctTimeL = false;
                    LocalTime newCarLatest = null;
                    while (!correctTimeL) {
                        System.out.println("Enter a latest time, the car can be booked at");
                        String timeString = inputScanner.next();
                        if (DateTimeManager.isTime(timeString)) {
                            newCarLatest = DateTimeManager.getTimeFromString(timeString);
                            correctTimeL = true;
                        } else {
                            System.out.println("Please type in a correct time");
                        }
                    }

                    boolean correctDuration = false;
                    int newCarDuration = -1;
                    while (!correctDuration) {
                        System.out.println("input the maximum duration that the car can be booked for in minutes" +
                                "e.g:" + Formatter.format(FORMAT.BLUE, "120"));
                        String durationString = inputScanner.next();
                        if (isInteger(durationString)) {
                            int durationInput = Integer.parseInt(durationString);
                            if (durationInput < 30) {
                                System.out.println("max Duration must be at least 30 minutes");
                            } else {
                                newCarDuration = durationInput;
                                correctDuration = true;
                            }
                        } else {
                            System.out.println("please input correct duration");
                        }
                    }

                    boolean correctFee = false;
                    double fee = 0.0;
                    while (!correctFee) {
                        System.out.println("Type in initial car fee e.g:" + Formatter.format(FORMAT.BLUE, "35.5"));
                        String inputFeeString = inputScanner.next();
                        if (isDouble(inputFeeString)) {
                            double feeInput = Double.parseDouble(inputFeeString);
                            if (feeInput <= 0) {
                                System.out.println("fee must be at least more then 0,-");
                            } else {
                                fee = feeInput;
                                correctFee = true;
                            }
                        } else {
                            System.out.println("hint: fee must be a double");
                        }
                    }

                    boolean correctPPM = false;
                    double newCarpricePerMinute = 0.0;
                    while (!correctPPM) {
                        System.out.println("Type in price per minute e.g:" + Formatter.format(FORMAT.BLUE, "0.5"));
                        String inputPPMString = inputScanner.next();
                        if (isDouble(inputPPMString)) {
                            double ppmInput = Double.parseDouble(inputPPMString);
                            if (ppmInput <= 0) {
                                System.out.println("price per minute must be at least more then 0,-");
                            } else {
                                newCarpricePerMinute = ppmInput;
                                correctPPM = true;
                            }
                        } else {
                            System.out.println("hint: price must be a double");
                        }
                    }
                    Response carAddResponse = carBookingBO.addCar(newCarID, newCarDesignation, newCarDT, newCarEarliest, newCarDuration, newCarLatest, newCarpricePerMinute, fee);
                    if (carAddResponse.isSuccess()) {
                        System.out.println(Formatter.format(FORMAT.GREEN, carAddResponse.getMessage()));
                    } else {
                        System.out.println(Formatter.format(FORMAT.RED, carAddResponse.getMessage()));
                    }
                    break;
                default:
                    System.out.println("unknown input");
                    break;
            }
        }
    }
}
