package main.carSharing;

import main.Users.LoginSignup;
import main.utils.Response;
import main.utils.jsonHandler;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CarBO {

    private final static String carPath = "src/data/cars/";
    private final static String bookingPath = "src/data/bookings/";
    private static CarBO instance;
    private ArrayList<Car> allCars;
    private ArrayList<Booking> allBookings;


    private CarBO() {
        allCars = getCarsFromFiles();
        allBookings = getBookingsFromFiles();
    }

    public static CarBO getInstance() {
        if (instance == null) {
            return new CarBO();
        }
        return instance;
    }

    public ArrayList<Car> getAllCars() {
        return allCars;
    }

    public Response addCar(String id, String designation, DriveType driveType, LocalTime earliest,int maxDuration, LocalTime latest, double ppm, double fee) {
        if (!isUniqueId(id)) {
            return new Response(false, "Car id is already taken");
        }
        Car newCar = new Car(id, designation, driveType, earliest, latest, maxDuration, ppm, fee);

        jsonHandler.getInstance().writeJsonfile(carPath, id, newCar.toJson());
        allCars.add(newCar);
        return new Response(true, "added Car" + id + " succsesfully");
    }

    public boolean isUniqueId(String id) {

        for (int i = 0; i < allCars.size(); i++) {
            if (id.equals(allCars.get(i).getId())) {
                return false;
            }
        }
        return true;

    }

    public ArrayList<Car> getCarsFromFiles() {
        ArrayList<Car> tempCars = new ArrayList<>();
        File folder = new File(carPath);
        File[] files = folder.listFiles();
        if (files.length > 0) {
            for (File f : files) {
                if (f.getName().endsWith(".json")) {
                    String fileName = f.getName().replaceFirst("[.][.json]+$", "");
                    Car tempcar = jsonHandler.getInstance().readJsonFileToObject(carPath, fileName, Car.class);
                    tempCars.add(tempcar);
                }

            }

        }
        return tempCars;
    }


    public ArrayList<Booking> getBookingsFromFiles() {
        ArrayList<Booking> bookings = new ArrayList<>();
        File folder = new File(bookingPath);
        File[] files = folder.listFiles();
        if (files.length > 0) {
            for (File f : files) {
                if (f.getName().endsWith(".json")) {
                    String fileName = f.getName().replaceFirst("[.][.json]+$", "");
                    Booking tempBooking = jsonHandler.getInstance().readJsonFileToObject(bookingPath, fileName, Booking.class);
                    bookings.add(tempBooking);
                }

            }

        }
        return bookings;

    }


    public Car getCarById(String carID) {
        if (allCars.size() > 0) {
            for (Car c : allCars) {
                if (c.getId().equals(carID)) {
                    return c;
                }
            }
        }
        System.err.println("Couldnt find car by Id:" + carID);
        return null;
    }


    public Response bookCar(String carID, LocalDate date, LocalTime time, int duration) {
        if (duration < 10) {
            return new Response(false, "duration must be longer then 10 minutes");
        }
        Car c = getCarById(carID);
        int bookingCount = allBookings.size();
        double cost = c.getInitialFee() + c.getPricePerMinute() * duration;
        String currUser = LoginSignup.getInstance().getCurrentClient().getUsername();
        Booking b = new Booking(carID, currUser, date, time, duration, cost, bookingCount);
        System.out.println(b.toString());
        jsonHandler.getInstance().writeJsonfile(bookingPath, b.getId(), b.toJson());
        return new Response(true, "created new Booking");

    }

    public boolean timeIsAvailable(String carID, LocalDate date, LocalTime startTime, int duration) {

        LocalTime endTime = startTime.plusMinutes(duration);

        for (int i = 0; i < allBookings.size(); i++) {
            if (allBookings.get(i).getCarID().equals(carID)) {
                Booking currentBooking = allBookings.get(i);
                if (currentBooking.getDate().equals(date)) {
                    LocalTime beforeTime = currentBooking.getTime().minusMinutes(1);
                    LocalTime afterTime = currentBooking.getTime().plusMinutes(duration + 1);
                    if (startTime.isBefore(afterTime) && endTime.isAfter(beforeTime)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public Response checkIfCarAvailable(String carID, LocalDate date, LocalTime time, int duration) {
        Car car = getCarById(carID);

        if (duration > car.getMaxDuration()) {
            return new Response(false, "duration is longer then allowed maximum duration for this car");
        }

        if (time.isBefore(car.getEarliestTime()) || time.isAfter(car.getLatestTime())) {
            return new Response(false, "Desired time period is outside of the Cars Service-Time");
        }
        LocalTime timeEnd = time.plusMinutes(duration);
        if (timeEnd.isAfter(car.getLatestTime()) || timeEnd.isBefore(car.getEarliestTime())) {
            return new Response(false, "Desired time period is outside of the Cars Service-Time");
        }

        if (!timeIsAvailable(carID,date,time,duration)){
            return new Response(false,"Car is already Booked in this time period");
        }

        return new Response(true,"Car is available");
    }


}