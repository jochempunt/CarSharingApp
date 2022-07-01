package main.carSharing;

import java.sql.Time;

public class Car {

    private final String id;
    private String designation;
    private DriveType driveType;
    private Time earliestTime;
    private Time latestTime;
    private double pricePerMinute;
    private double initialFee;

    Car(String id, String designation, DriveType driveType, Time earliestTime, Time latestTime, double pricePerMinute, double initialFee) {
        this.id = id;
        this.designation = designation;
        this.driveType = driveType;
        this.earliestTime = earliestTime;
        this.latestTime = latestTime;
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

    public Time getEarliestTime() {
        return earliestTime;
    }

    public Time getLatestTime() {
        return latestTime;
    }

    public double getPricePerMinute() {
        return pricePerMinute;
    }

    public double getInitialFee() {
        return initialFee;
    }
}
