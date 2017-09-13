package com.library.utils.date;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by linqs on 2017/9/11.
 */

public class DateUtils {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String DEFAULT_MILLISECOND_FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * 格式化日期格式（日期为当前时间）
     *
     * @param format
     * @return
     */
    public static String format(String format) {
        Date date = new Date();
        return format(date, format);
    }

    /**
     * 格式化日期格式
     *
     * @param timeStamp
     * @param format
     * @return
     */
    public static String format(long timeStamp, String format) {
        Date date = new Date(timeStamp);
        return format(date, format);
    }

    /**
     * 格式化日期格式
     *
     * @param timeStr
     * @param format
     * @return
     */
    public static String format(String timeStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format(date, format);
    }

    /**
     * 格式化日期格式
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        if (null == date) {
            return null;
        }
        if (TextUtils.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 判断是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            return false;
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DATE) == calendar2.get(Calendar.DATE)) {
            return true;
        }
        return false;
    }
}
