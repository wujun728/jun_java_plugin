package com.jun.plugin.common.utils;

import java.text.*;
import java.util.Calendar;

public class TimeUuidUtil {
    /**
     * The FieldPosition.
     */
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

    /**
     * This Format for format the data to special format.
     */
    private final static Format dateFormat = new SimpleDateFormat("YYMMddHHmmss");
    private final static Format dateFormatS = new SimpleDateFormat("YYMMddHHmmssSSS");
    /**
     * This Format for format the number to special format.
     */
    private final static NumberFormat numberFormat = new DecimalFormat("0000");

    /**
     * This int is the sequence number ,the default value is 0.
     */
    private static int seq = 0;

    private static final Object lock = new Object();
    private static final Object lock2 = new Object();

    /**
     * 时间格式生成16位序列ID （注：1秒内最多调用一万次，否则会出现重复）
     * 性能十万次耗时约320ms
     *
     * @return Long
     */
    public static Long get16UUID() {
        Calendar rightNow = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        synchronized(lock){
            return getFormatNum(dateFormat,sb,rightNow);
        }
    }

    /**
     * 时间格式生成19位序列ID （注：1毫秒内最多调用一万次，否则会出现重复）
     * 注：到2092年时该数字会超出long的范围，会抛异常
     * @return Long
     */
    public static Long get19UUID(){
        Calendar rightNow = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        synchronized(lock2){
            return getFormatNum(dateFormatS,sb,rightNow);
        }
    }

    private static Long getFormatNum(Format dateFormat,StringBuffer sb,Calendar rightNow){
        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
        numberFormat.format(seq, sb, HELPER_POSITION);
        if (seq == 9999) {
            seq = 0;
        } else {
            seq++;
        }
        return Long.parseLong(sb.toString());
    }

}
