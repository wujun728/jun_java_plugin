package com.chentongwei.common.util;

import com.chentongwei.common.enums.constant.DateTimeFormatEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 日期工具类
 */
public final class DateUtil {
    private DateUtil() {}

    private static Map<String, SimpleDateFormat> dateFormatMap = new ConcurrentHashMap<>();

    /**
     * 计算开始时间与结束时间相差几小时
     *
     * @param date1：开始时间
     * @param date2：结束时间
     * @return
     * @throws ParseException
     */
    public static final int hourDiff(Date date1, Date date2) throws ParseException {
        long from = date1.getTime();
        long to = date2.getTime();
        return (int) ((to - from) / (1000 * 60 * 60));
    }

    public static final String getYearMonth(Date date) {
        return getDateFormat(DateTimeFormatEnum.YEAR_MONTH.parttern()).format(date);
    }

    /**
     * 获取SimpleDateFormat
     *
     * @param parttern：日期格式
     * @return
     */
    private static final SimpleDateFormat getDateFormat(String parttern) {
        SimpleDateFormat simpleDateFormat = dateFormatMap.get(parttern);
        if (simpleDateFormat != null) {
            return simpleDateFormat;
        } else {
            dateFormatMap.put(parttern, new SimpleDateFormat(parttern));
            return dateFormatMap.get(parttern);
        }
    }

    public static void main(String[] args) {
        String format = getDateFormat(DateTimeFormatEnum.YEAR_MONTH.parttern()).format(new Date());
        System.out.println(format);
    }
}
