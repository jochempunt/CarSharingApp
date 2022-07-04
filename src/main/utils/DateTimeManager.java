package main.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateTimeManager {

    public final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public final static DateTimeFormatter dateFormatter =DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.GERMAN);

    public static LocalTime getTimeFromString(String time){
        return LocalTime.from(timeFormatter.parse(time));
    }

    public static LocalDate getDateFromString(String date){
        return LocalDate.from(dateFormatter.parse(date));
    }

    public static String timeToString(LocalTime localTime){
        return timeFormatter.format(localTime);
    }

    public static String DateToString(LocalDate localDate){
        return dateFormatter.format(localDate);
    }
}
