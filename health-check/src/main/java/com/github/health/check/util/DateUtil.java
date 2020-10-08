package com.github.health.check.util;

import java.util.Date;

public class DateUtil {

    public static long sub(Date date1, Date date2) {
        return (date1.getTime()-date2.getTime())/1000*60;
    }
}
