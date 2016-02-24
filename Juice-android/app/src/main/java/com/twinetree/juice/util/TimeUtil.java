package com.twinetree.juice.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public long getTimeDiffFromNow(String date1Text) {
        Date date1 = null;
        Date date2 = new Date();
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            date1 = format.parse(date1Text);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        long diff = date2.getTime() - date1.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(diff);
    }
}
