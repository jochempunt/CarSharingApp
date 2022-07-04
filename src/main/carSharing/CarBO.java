package main.carSharing;

import main.utils.Response;
import main.utils.jsonHandler;

import java.time.LocalTime;
import java.util.ArrayList;

public class CarBO {

    private static CarBO instance;
    private ArrayList<Car> Cars = new ArrayList<Car>();
    private Booking[] allBookings;

    private CarBO() {

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

        jsonHandler.getInstance().writeJsonfile("src/data/cars/", id, newCar.toJson());

        return new Response(true, "added Car" + id + " succsesfully");
    }

    public boolean isUniqueId(String id) {

        for (int i = 0; i < Cars.size(); i++) {
            if (id.equals(Cars.get(i).getId())) {
                return false;
            }
        }
        return true;

    }


}
