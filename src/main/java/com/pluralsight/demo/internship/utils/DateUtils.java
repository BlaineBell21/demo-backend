package com.pluralsight.demo.internship.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String currentDateAndTime(){
        // used to get current, local date and time when making a new transaction
        LocalDateTime dateAndTime = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy-hh:mm:ss");
        // formatted as shown in capstone example
        return dateAndTime.format(fmt);
        // returns current, local date and time
    }
}
