package com.dmbaryshev.vkschool.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeHelper {
    private static final String TIME_FORMAT = "hh:mm";

    public static String convertTimestampToString(int timestamp) {
        // TODO: 17.02.2016 check it
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000);
        return formatter.format(calendar.getTime());
    }

    public static String convertDurationToString(int sec) {
        return String.format("%02d:%02d",
                             TimeUnit.SECONDS.toMinutes(sec),
                             (sec - TimeUnit.SECONDS.toMinutes(sec) * 60));
    }
}
