package main.carSharing;

import com.google.gson.JsonObject;
import main.utils.DateTimeManager;
import main.utils.jsonHandler;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private final String id;
    private final String CarID;
    private final String clientName;
    private final String date;
    private final String time;
    private final int durationInMins;



    private final double totalCost;

    public Booking(String carID, String clientName, LocalDate date, LocalTime time, int durationInMins, double totalCost,int count) {
        CarID = carID;
        this.clientName = clientName;
        this.date = DateTimeManager.DateToString(date);
        this.time = DateTimeManager.timeToString(time);
        this.durationInMins = durationInMins;
        this.totalCost = totalCost;
        id = carID+ "-" + count;
    }
    public Booking(String carID, String clientName, String date, String time, int durationInMins, double totalCost, String id) {
        CarID = carID;
        this.clientName = clientName;
        this.date = date;
        this.time =time;
        this.durationInMins = durationInMins;
        this.totalCost = totalCost;
        this.id = id;
    }






    @Override
    public String toString() {
        return "Booking{" +
                "bookingID='" + id + '\'' +
                ", CarID='" + CarID + '\'' +
                ", clientName='" + clientName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", DurationinMins=" + durationInMins +
                ", totalcosts="+ totalCost + ",-"+
                '}';
    }

    public String getId() {
        return id;
    }

    public String getCarID() {
        return CarID;
    }

    public String getClientName() {
        return clientName;
    }

    public LocalDate getDate() {
        return DateTimeManager.getDateFromString(date);
    }

    public LocalTime getTime() {
        return DateTimeManager.getTimeFromString(time);
    }

    public int getDurationInMins() {
        return durationInMins;
    }

    public JsonObject toJson() {
        return jsonHandler.getInstance().gson.toJsonTree(this, this.getClass()).getAsJsonObject();
    }


}
