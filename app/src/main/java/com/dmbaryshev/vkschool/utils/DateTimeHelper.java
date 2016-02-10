package com.dmbaryshev.vkschool.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeHelper {
    private static final String TIME_FORMAT = "HH:mm";

    public static String convertMillisecToString(int millisec) {
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT, Locale.ROOT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisec);
        return formatter.format(calendar.getTime());
    }
}
