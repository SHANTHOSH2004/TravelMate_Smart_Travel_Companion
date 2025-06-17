package com.example.smarttravelcompanion.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TripCountdown {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public static String getDaysUntilTrip(String startDate) {
        try {
            Date tripDate = dateFormat.parse(startDate);
            Date today = new Date();
            
            if (tripDate != null) {
                long diffInMillis = tripDate.getTime() - today.getTime();
                long days = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                
                if (days < 0) {
                    return "Trip has already started!";
                } else if (days == 0) {
                    return "Your trip starts today!";
                } else {
                    return days + " days until your trip!";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Invalid date format";
    }

    public static String getTripDuration(String startDate, String endDate) {
        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            
            if (start != null && end != null) {
                long diffInMillis = end.getTime() - start.getTime();
                long days = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                return days + " days";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Invalid date format";
    }
} 