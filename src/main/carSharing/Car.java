package main.carSharing;

import com.google.gson.JsonObject;
import main.utils.DateTimeManager;
import main.utils.jsonHandler;

import java.sql.Time;
import java.time.LocalTime;

public class Car {

    private final String id;
    private String designation;
    private DriveType driveType;
    private String earliestTime;
    private String latestTime;
    private double pricePerMinute;
    private double initialFee;

    public Car(String id, String designation, DriveType driveType, LocalTime earliestTime, LocalTime latestTime, double pricePerMinute, double initialFee) {
        this.id = id;
        this.designation = designation;
        this.driveType = driveType;
        this.earliestTime = DateTimeManager.timeToString(earliestTime);
        this.latestTime = DateTimeManager.timeToString(latestTime);
        this.pricePerMinute = pricePerMinute;
        this.initialFee = initialFee;
    }









// -----------------------------Getters--------------------------------//
    public String getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public DriveType getDriveType() {
        return driveType;
    }

    public LocalTime getEarliestTime() {
        return DateTimeManager.getTimeFromString(this.earliestTime);
    }

    public LocalTime getLatestTime() {
        return DateTimeManager.getTimeFromString(this.latestTime);
    }

    public double getPricePerMinute() {
        return pricePerMinute;
    }

    public double getInitialFee() {
        return initialFee;
    }

    public JsonObject toJson(){
         return jsonHandler.getInstance().gson.toJsonTree(this,this.getClass()).getAsJsonObject();
    }
}
