package main.carSharing;

import main.utils.Response;
import main.utils.jsonHandler;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;

public class CarBO {

    private static CarBO instance;
    private static String carPath = "src/data/cars/";
    private ArrayList<Car> allCars = new ArrayList<Car>();
    private Booking[] allBookings;

    private CarBO() {
        allCars = getAllCars();
    }

    public static CarBO getInstance() {
        if (instance == null) {
            return new CarBO();
        }
        return instance;
    }

    public Response addCar(String id, String designation, DriveType driveType, LocalTime earliest, LocalTime latest, double ppm, double fee) {
        if (!isUniqueId(id)) {
            return new Response(false, "Car id is already taken");
        }
        Car newCar = new Car(id, designation, driveType, earliest, latest, ppm, fee);

        jsonHandler.getInstance().writeJsonfile(carPath, id, newCar.toJson());

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

    public ArrayList<Car> getAllCars() {
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
            return tempCars;
        }
        return null;

    }

}
