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

    // checks if String is in this Format: "dd-MM-yyyy hh:mm"
    public static boolean isDateTime(String s){
        return s.matches("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-\\d{4} (0[1-9]|1[0-9]|2[0-4]):(0[0-9]|[12345][0-9])$");
    }
    // checks if time is in Format: "hh:mm"
    public static boolean isTime(String s){
        return s.matches("(0[1-9]|1[0-9]|2[0-4]):(0[0-9]|[12345][0-9])$");
    }
}
