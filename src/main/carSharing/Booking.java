package main.carSharing;

import com.google.gson.JsonObject;
import main.utils.DateTimeManager;
import main.utils.jsonHandler;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private String id;
    private String carID;
    private String clientName;
    private String date;
    private String time;
    private int durationInMins;
    private double totalCost;

    public Booking(String carID, String clientName, LocalDate date, LocalTime time, int durationInMins, double totalCost, int count) {
        this.carID = carID;
        this.clientName = clientName;
        this.date = DateTimeManager.DateToString(date);
        this.time = DateTimeManager.timeToString(time);
        this.durationInMins = durationInMins;
        this.totalCost = totalCost;
        this.id = carID + "-" + count;
    }


    public double getTotalCost() {
        return totalCost;
    }


    public String getId() {
        return id;
    }

    public String getCarID() {
        return carID;
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
