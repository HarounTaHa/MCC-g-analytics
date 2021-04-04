package com.example.googleanalyticsassignment.utility;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Service {
    public Date calendarDate() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return c.getTime();
    }
}
