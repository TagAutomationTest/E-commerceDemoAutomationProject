package com.swaglabs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtils {
    //test_" timestamp "@gmail.com  test_2025-2-28-17-20@gmail.com
    public static String getTimestamp() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return formatter.format(date);
    }


}

