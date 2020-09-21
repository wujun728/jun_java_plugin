package com.yisin.dbc.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * <pre>
 * 日期时间工具类
 * </pre>
 * <ul>
 * <li>将时间日期字符串转换为Date类型 <br>
 * public static Date parse(String dateStr); <br>
 * public static Date parse(String dateStr, String pattern)</li>
 * <li>将Date格式化为正规日期时间字符串 <br>
 * public static String format(Date date); <br>
 * public static String format(Date date, String pattern);</li>
 * </ul>
 * 
 * @author yisin
 * @date 2012-11-29 下午06:57:18
 * 
 */
public final class DateUtil {
    protected static final Logger log = Logger.getLogger(DateUtil.class);

    /**
     * SimpleDateFormat日期+时间格式yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat SDF_DATETIME = new SimpleDateFormat(Constants.PATTERN_DATE_TIME);
    /**
     * SimpleDateFormat日期格式yyyy-MM-dd
     */
    public static final SimpleDateFormat SDF_DATE = new SimpleDateFormat(Constants.PATTERN_DATE);

    /**
     * 
     * 将时间日期字符串转换为Date类型 <br>
     * 
     * @author yisin
     * @date 2012-11-30 上午09:28:04
     * @param dateStr
     * @return
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, null);
    }

    /**
     * 
     * 将时间日期字符串转换为指定格式的Date类型 <br>
     * 
     * @author yisin
     * @date 2012-11-30 上午09:28:04
     * @param dateStr
     * @return
     */
    public static Date parse(String dateStr, String pattern) {
        Date date = null;
        if (null != dateStr) {
            try {
                if (dateStr.length() < 12) {
                    dateStr += " 00:00:00";
                }
                if (pattern != null) {
                    SimpleDateFormat sim = new SimpleDateFormat(pattern);
                    date = sim.parse(dateStr);
                } else {
                    date = SDF_DATETIME.parse(dateStr);
                }
            } catch (ParseException e) {
                log.error("时间格式转换失败！", e);
            }
        } else {
            log.error("字符串为空：dateStr=" + dateStr);
        }
        return date;
    }

    /**
     * 将Date格式化为正规日期时间字符串
     * 
     * @author yisin
     * @date 2012-11-30 上午09:59:10
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, null);
    }

    /**
     * 将Date格式化为指定格式日期时间字符串
     * 
     * @author yisin
     * @date 2012-11-30 上午09:59:10
     * @param date
     * @return
     */
    public static String format(Date date, String pattern) {
        String dateStr = null;
        if (date != null) {
            try {
                if (pattern != null) {
                    SimpleDateFormat sim = new SimpleDateFormat(pattern);
                    dateStr = sim.format(date);
                } else {
                    dateStr = SDF_DATETIME.format(date);
                }
            } catch (Exception e) {
                log.error("时间格式化字符串失败！", e);
            }
        } else {
            log.error("Date参数为空：date=" + date);
        }
        return dateStr;
    }

    // 将时间字符串转换成指定时间格式的时戳
    public static Timestamp parseToTimestamp(String dateStr, String pattern) {
        Timestamp tmp = null;
        // 判断字符串是否为空
        if (CommonUtils.isEmpty(dateStr) || CommonUtils.isEmpty(pattern)) {
            throw new IllegalArgumentException("The parameter is null.");
        }
        // 构造SimpleDateFormat对象
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            tmp = new Timestamp(sdf.parse(dateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /**
     * 将时间字符串转换成指定时间格式的时戳
     * 
     * @author yisin
     * @date 2013-1-24 下午06:38:16
     * @param dateStr
     * @return
     */
    public static Timestamp parseToTimestamp(String dateStr) {
        return parseToTimestamp(dateStr, Constants.PATTERN_DATE_TIME);
    }

    /**
     * 根据出生日期计算标准年龄
     * 
     * @author yisin
     * @date 2013-1-24 下午06:38:07
     * @param birthDate
     * @return
     */
    public static int getAgeByDate(Date birthDate) {
        Date day = new Date();
        int age = day.getYear() - birthDate.getYear();
        birthDate.setYear(birthDate.getYear() + age);
        age = birthDate.getTime() - day.getTime() > 0 ? age - 1 : age;
        return age;
    }

    /**
     * 计算当前日期在当前年份中是第几周
     * 
     * @author yisin
     * @date 2013-4-2 下午01:57:39
     * @return
     */
    public static int getWeekByDate() {
        return getWeekByDate(new Date());
    }

    /**
     * 计算指定日期在当前年份中是第几周
     * 
     * @author yisin
     * @date 2013-4-2 下午01:56:50
     * @param date
     * @return
     */
    public static int getWeekByDate(Date date) {
        SimpleDateFormat sim = new SimpleDateFormat("w");
        String wk = sim.format(date);
        return CommonUtils.stringToInt(wk, -1);
    }

    /**
     * 根据出生日期字符串计算标准年龄
     * 
     * @author yisin
     * @date 2013-1-24 下午06:38:53
     * @param birthDateStr
     * @return
     */
    public static int getAgeByDateStr(String birthDateStr) {
        Date birthDate = parse(birthDateStr, "yyyy-MM-dd");
        return getAgeByDate(birthDate);
    }

    /**
     * 获取当前日期前后N天日期Date
     * 
     * @author yisin
     * @date 2013-3-6 上午09:27:21
     * @param dayNumber
     * @return
     */
    public static Date getBeforeOrAfterDateByDayNumber(int dayNumber) {
        return getBeforeOrAfterDateByDayNumber(new Date(), dayNumber);
    }

    /**
     * 获取指定日期前后N天日期Date
     * 
     * @author yisin
     * @date 2013-3-6 上午09:26:38
     * @param date
     * @param dayNumber
     * @return
     */
    public static Date getBeforeOrAfterDateByDayNumber(Date date, int dayNumber) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, dayNumber);
        return c.getTime();
    }

    /**
     * 获取当前日期的前后N月的日期Date
     * 
     * @author yisin
     * @date 2013-3-6 上午09:39:36
     * @param monthNumber
     * @return
     */
    public static Date getBeforeOrAfterDateByMonthNumber(int monthNumber) {
        return getBeforeOrAfterDateByMonthNumber(new Date(), monthNumber);
    }

    /**
     * 获取指定日期前后N月的日期Date
     * 
     * @author yisin
     * @date 2013-3-6 上午09:38:49
     * @param date
     * @param monthNumber
     * @return
     */
    public static Date getBeforeOrAfterDateByMonthNumber(Date date, int monthNumber) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, monthNumber);
        return c.getTime();
    }

    /**
     * 拿date与当前日期比较大小
     * 
     * @author yisin
     * @date 2013-4-2 下午03:36:57
     * @param date
     * @param genre
     *            比较类型 1:< , 2:> , 3: <= , 4: >= , 5: ==
     * @return
     */
    public static boolean compareDateOrTime(Date date, int genre) {
        return compareDateOrTime(date, new Date(), genre);
    }

    /**
     * 两个日期比较大小
     * 
     * @author yisin
     * @date 2013-4-2 下午03:36:57
     * @param date1
     * @param date2
     * @param genre
     *            比较类型 1:< , 2:> , 3: <= , 4: >= , 5: ==
     * @return
     */
    public static boolean compareDateOrTime(Date date1, Date date2, int genre) {
        boolean bool = false;
        long times1 = date1.getTime();
        long times2 = date2.getTime();
        if (genre == 1) {
            bool = times1 < times2;
        } else if (genre == 2) {
            bool = times1 > times2;
        } else if (genre == 3) {
            bool = times1 <= times2;
        } else if (genre == 4) {
            bool = times1 >= times2;
        } else if (genre == 5) {
            bool = times1 == times2;
        }
        return bool;
    }

    /**
     * 拿date字符串与当前日期比较大小
     * 
     * @author yisin
     * @date 2013-4-2 下午03:36:57
     * @param date
     * @param genre
     *            比较类型 1:< , 2:> , 3: <= , 4: >= , 5: ==
     * @return
     */
    public static boolean compareDateOrTime(String date, int genre) {
        Date day = new Date();
        if (date != null && date.length() < 12) {
            day = parse(SDF_DATE.format(day));
        }
        return compareDateOrTime(parse(date), day, genre);
    }

    /**
     * 两个日期字符串比较大小
     * 
     * @author yisin
     * @date 2013-4-2 下午03:36:57
     * @param date1
     * @param date2
     * @param genre
     *            比较类型 1:< , 2:> , 3: <= , 4: >= , 5: ==
     * @return
     */
    public static boolean compareDateOrTime(String date1, String date2, int genre) {
        return compareDateOrTime(parse(date1), parse(date2), genre);
    }

    /**
     * 得到当前月最后一天
     * 
     * @author yisin
     * @date 2013-5-30 下午5:59:54
     * @return
     */
    public static String getLastMonDay() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, maxDay);
        return DateUtil.format(cal.getTime(), Constants.PATTERN_DATE);
    }

    /**
     * 获得某个周的第一天
     * 
     * @author zhaoqc
     * @param year
     *            年份
     * @param week
     *            周
     * @return String yyyy-MM-dd字符串
     */
    public static String getFirstOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, week);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(c.getTime());
    }

    /**
     * 获得某个周的最后一天
     * 
     * @author zhaoqc
     * @param year
     *            年份
     * @param week
     *            周
     * @return String yyyy-MM-dd字符串
     */
    public static String getLastOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, week);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.DAY_OF_WEEK, (c.getFirstDayOfWeek() + 6));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(c.getTime());
    }

    /**
     * 主函数
     */
    public static void main(String[] args) {
        System.out.println(compareDateOrTime("2013-04-02", 1));
        Date day = new Date();
        Calendar cc = Calendar.getInstance();
        cc.setTime(day);
        cc.set(Calendar.MONTH, cc.get(Calendar.MONTH) + 8);
        cc.set(Calendar.DATE, 28);
        System.out.println(format(day));
        System.out.println(format(cc.getTime()));
        int i = new Double(Math.ceil(2.001)).intValue();
        System.out.println(i);

        Calendar xx = Calendar.getInstance();
        System.out.println(getFirstOfWeek(xx.get(Calendar.YEAR), xx.get(Calendar.WEEK_OF_YEAR)));
        System.out.println(getLastOfWeek(xx.get(Calendar.YEAR), xx.get(Calendar.WEEK_OF_YEAR)));
    }

}
