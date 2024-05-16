package com.cailei.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：蔡磊
 * @date ：2022/10/15 15:39
 * @description：时间格式工具
 */
public class DateUtil {
    /**
     * 对指定的date对象进行格式化: yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 对指定的date对象进行格式化: yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 对指定的date对象进行格式化: HH:mm:ss
     * @param date
     * @return
     */
    public static String formatTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}
