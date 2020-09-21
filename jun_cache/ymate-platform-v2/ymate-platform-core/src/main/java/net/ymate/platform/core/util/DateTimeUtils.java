/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.core.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期时间数据处理工具类
 *
 * @author 刘镇 (suninformation@163.com) on 2010-4-18 上午02:40:41
 * @version 1.0
 */
public final class DateTimeUtils {

    public static final long SECOND = 1000;         // 1秒

    public static final long MINUTE = SECOND * 60;  // 1分钟

    public static final long HOUR = MINUTE * 60;    // 1小时

    public static final long DAY = HOUR * 24;       // 1天

    public static final long WEEK = DAY * 7;        // 1周

    public static final long YEAR = DAY * 365;      // 1年（or 366 ???）

    /**
     * 日期格式化字符串：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 日期格式化字符串：yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化字符串：yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式化字符串：yyyy-MM
     */
    public static final String YYYY_MM = "yyyy-MM";

    /**
     * 日期格式化字符串：yyyy-MM-dd HH:mm
     */
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final Map<String, String[]> TIME_ZONES;

    static {
        Map<String, String[]> _timeZonesMap = new LinkedHashMap<String, String[]>(32);
        //
        _timeZonesMap.put("-12", new String[]{"GMT-12:00", "(GMT -12:00) Eniwetok, Kwajalein"});
        _timeZonesMap.put("-11", new String[]{"GMT-11:00", "(GMT -11:00) Midway Island, Samoa"});
        _timeZonesMap.put("-10", new String[]{"GMT-10:00", "(GMT -10:00) Hawaii"});
        _timeZonesMap.put("-9", new String[]{"GMT-09:00", "(GMT -09:00) Alaska"});
        _timeZonesMap.put("-8", new String[]{"GMT-08:00", "(GMT -08:00) Pacific Time (US &amp; Canada), Tijuana"});
        _timeZonesMap.put("-7", new String[]{"GMT-07:00", "(GMT -07:00) Mountain Time (US &amp; Canada), Arizona"});
        _timeZonesMap.put("-6", new String[]{"GMT-06:00", "(GMT -06:00) Central Time (US &amp; Canada), Mexico City"});
        _timeZonesMap.put("-5", new String[]{"GMT-05:00", "(GMT -05:00) Eastern Time (US &amp; Canada), Bogota, Lima, Quito"});
        _timeZonesMap.put("-4", new String[]{"GMT-04:00", "(GMT -04:00) Atlantic Time (Canada), Caracas, La Paz"});
        _timeZonesMap.put("-3.5", new String[]{"GMT-03:30", "(GMT -03:30) Newfoundland"});
        _timeZonesMap.put("-3", new String[]{"GMT-03:00", "(GMT -03:00) Brassila, Buenos Aires, Georgetown, Falkland Is"});
        _timeZonesMap.put("-2", new String[]{"GMT-02:00", "(GMT -02:00) Mid-Atlantic, Ascension Is., St. Helena"});
        _timeZonesMap.put("-1", new String[]{"GMT-01:00", "(GMT -01:00) Azores, Cape Verde Islands"});
        _timeZonesMap.put("0", new String[]{"GMT", "(GMT) Casablanca, Dublin, Edinburgh, London, Lisbon, Monrovia"});
        _timeZonesMap.put("1", new String[]{"GMT+01:00", "(GMT +01:00) Amsterdam, Berlin, Brussels, Madrid, Paris, Rome"});
        _timeZonesMap.put("2", new String[]{"GMT+02:00", "(GMT +02:00) Cairo, Helsinki, Kaliningrad, South Africa"});
        _timeZonesMap.put("3", new String[]{"GMT+03:00", "(GMT +03:00) Baghdad, Riyadh, Moscow, Nairobi"});
        _timeZonesMap.put("3.5", new String[]{"GMT+03:30", "(GMT +03:30) Tehran"});
        _timeZonesMap.put("4", new String[]{"GMT+04:00", "(GMT +04:00) Abu Dhabi, Baku, Muscat, Tbilisi"});
        _timeZonesMap.put("4.5", new String[]{"GMT+04:30", "(GMT +04:30) Kabul"});
        _timeZonesMap.put("5", new String[]{"GMT+05:00", "(GMT +05:00) Ekaterinburg, Islamabad, Karachi, Tashkent"});
        _timeZonesMap.put("5.5", new String[]{"GMT+05:30", "(GMT +05:30) Bombay, Calcutta, Madras, New Delhi"});
        _timeZonesMap.put("5.75", new String[]{"GMT+05:45", "(GMT +05:45) Katmandu"});
        _timeZonesMap.put("6", new String[]{"GMT+06:00", "(GMT +06:00) Almaty, Colombo, Dhaka, Novosibirsk"});
        _timeZonesMap.put("6.5", new String[]{"GMT+06:30", "(GMT +06:30) Rangoon"});
        _timeZonesMap.put("7", new String[]{"GMT+07:00", "(GMT +07:00) Bangkok, Hanoi, Jakarta"});
        _timeZonesMap.put("8", new String[]{"GMT+08:00", "(GMT +08:00) Beijing, Hong Kong, Perth, Singapore, Taipei"});
        _timeZonesMap.put("9", new String[]{"GMT+09:00", "(GMT +09:00) Osaka, Sapporo, Seoul, Tokyo, Yakutsk"});
        _timeZonesMap.put("9.5", new String[]{"GMT+09:30", "(GMT +09:30) Adelaide, Darwin"});
        _timeZonesMap.put("10", new String[]{"GMT+10:00", "(GMT +10:00) Canberra, Guam, Melbourne, Sydney, Vladivostok"});
        _timeZonesMap.put("11", new String[]{"GMT+11:00", "(GMT +11:00) Magadan, New Caledonia, Solomon Islands"});
        _timeZonesMap.put("12", new String[]{"GMT+12:00", "(GMT +12:00) Auckland, Wellington, Fiji, Marshall Island"});
        //
        TIME_ZONES = Collections.unmodifiableMap(_timeZonesMap);
    }

    public static SimpleDateFormat getSimpleDateFormat(String format, String timeOffset) {
        SimpleDateFormat _format = new SimpleDateFormat(format, Locale.ENGLISH);
        if (StringUtils.isNotBlank(timeOffset) && TIME_ZONES.containsKey(timeOffset)) {
            _format.setTimeZone(getTimeZone(timeOffset));
        }
        return _format;
    }

    public static TimeZone getTimeZone(String timeOffset) {
        return TimeZone.getTimeZone(TIME_ZONES.get(timeOffset)[0]);
    }

    /**
     * 时间修正偏移量
     */
    public static String TIMEZONE_OFFSET;

    /**
     * 私有构造器， 防止被实例化
     */
    private DateTimeUtils() {
    }

    /**
     * @return 获取当前时间
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * @return 获取当前UTC时间
     */
    public static long currentTimeUTC() {
        return currentTimeMillis() / 1000L;
    }

    /**
     * @return 获得当前时间
     */
    public static Date currentTime() {
        return new Date();
    }

    /**
     * @return 获取系统UTC时间
     */
    public static int systemTimeUTC() {
        return (int) currentTimeUTC();
    }

    /**
     * @param time    日期时间值(若为UTC时间，方法内将自动乘以1000)
     * @param pattern 日期时间输出模式，若为空则使用yyyy-MM-dd HH:mm:ss.SSS作为默认
     * @return 格式化日期时间输出字符串
     */
    public static String formatTime(long time, String pattern) {
        return formatTime(time, pattern, TIMEZONE_OFFSET);
    }

    public static String formatTime(long time, String pattern, String timeOffset) {
        if (String.valueOf(time).length() <= 10) {
            time *= 1000;
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = YYYY_MM_DD_HH_MM_SS;
        }
        return getSimpleDateFormat(pattern, timeOffset).format(new Date(time));
    }

    public static Date parseDateTime(String dateTime, String pattern) throws ParseException {
        return parseDateTime(dateTime, pattern, TIMEZONE_OFFSET);
    }

    public static Date parseDateTime(String dateTime, String pattern, String timeOffset) throws ParseException {
        if (StringUtils.isBlank(pattern)) {
            pattern = YYYY_MM_DD_HH_MM_SS;
        }
        return getSimpleDateFormat(pattern, timeOffset).parse(dateTime);
    }

}
