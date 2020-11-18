package com.project.chat.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTimeYMDHMSS() {
        return getDataTime("yyyy-MM-dd HH:mm:ss:sss");
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTimeYMDHMS() {
        return getDataTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTimeYMD() {
        return getDataTime("yyyy-MM-dd");
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime() {
        return getDataTime("HH:mm");
    }


}
