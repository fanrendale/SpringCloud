package com.xjf.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xjf
 * @date 2020/2/14 18:14
 */
public class DateUtils {
    public static final SafeSimpleDateFormat YYYY_MM_DD = new SafeSimpleDateFormat("yyyy-MM-dd");
    public static final SafeSimpleDateFormat YYYY_MM_DD_HH = new SafeSimpleDateFormat("yyyy-MM-dd HH");
    public static final SafeSimpleDateFormat YYYY_MM_DD_HH_MI = new SafeSimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SafeSimpleDateFormat YYYY_MM_DD_HH_MI_SS = new SafeSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SafeSimpleDateFormat DEFAULT = new SafeSimpleDateFormat("yyyy-MM-dd HH:mm");

    public DateUtils() {
    }

    public static SafeSimpleDateFormat getDateFormat(String pattern) {
        if ("yyyy-MM-dd".equals(pattern)) {
            return YYYY_MM_DD;
        } else if ("yyyy-MM-dd HH".equals(pattern)) {
            return YYYY_MM_DD_HH;
        } else if ("yyyy-MM-dd HH:mm".equals(pattern)) {
            return YYYY_MM_DD_HH_MI;
        } else {
            return "yyyy-MM-dd HH:mm:ss".equals(pattern) ? YYYY_MM_DD_HH_MI_SS : new SafeSimpleDateFormat(pattern);
        }
    }

    public static String date2Str(Date date) {
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String date2Str(Date date, String pattern) {
        if (null == date) {
            return null;
        } else {
            String initDate = YYYY_MM_DD.format(new Date(0L));
            return initDate.equals(YYYY_MM_DD.format(date)) ? "" : getDateFormat(pattern).format(date);
        }
    }

    public static Date str2Date(String date, String pattern) throws ParseException {
        return getDateFormat(pattern).parse(date);
    }

    public static Date str2Date(String date) throws ParseException {
        if (date.length() < 12) {
            return YYYY_MM_DD.parse(date);
        } else if (date.length() < 15) {
            return YYYY_MM_DD_HH.parse(date);
        } else {
            return date.length() < 18 ? YYYY_MM_DD_HH_MI.parse(date) : YYYY_MM_DD_HH_MI_SS.parse(date);
        }
    }

    public static Date getDayBegin(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getDayEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 999);
        return cal.getTime();
    }

    public static Date stringToDate(String dateStr){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
