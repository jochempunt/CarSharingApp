package main.utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class ResponseWithDate extends Response {

    private LocalDate date;
    private LocalTime time;
    private int duration;

    public ResponseWithDate(Response r, LocalDate date, LocalTime time, int duration) {
        super(r.isSuccess(), r.getMessage());
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }
}
