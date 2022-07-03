package main.carSharing;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private final String bookingID;
    private final String CarID;
    private final String clientName;
    private final LocalDate date;
    private final LocalTime time;
    private final int durationInMins;

    private final double totalCost;

    public Booking(String carID, String clientName, LocalDate date, LocalTime time, int durationInMins, double totalCost) {
        CarID = carID;
        this.clientName = clientName;
        this.date = date;
        this.time = time;
        this.durationInMins = durationInMins;
        this.totalCost = totalCost;
        bookingID = carID+ "-" + date.getYear() + "-"+date.getDayOfYear()+"-" + time.toString();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID='" + bookingID + '\'' +
                ", CarID='" + CarID + '\'' +
                ", clientName='" + clientName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", DurationinMins=" + durationInMins +
                ", totalcosts="+ totalCost + ",-"+
                '}';
    }

    public String getBookingID() {
        return bookingID;
    }

    public String getCarID() {
        return CarID;
    }

    public String getClientName() {
        return clientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getDurationInMins() {
        return durationInMins;
    }
}
