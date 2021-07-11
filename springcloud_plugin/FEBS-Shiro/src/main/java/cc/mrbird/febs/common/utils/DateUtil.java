package cc.mrbird.febs.common.utils;

import cc.mrbird.febs.common.entity.Strings;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author MrBird
 */
public abstract class DateUtil {

    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String CST_TIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static String formatFullTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
    }

    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatType, Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String formatCstTime(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CST_TIME_PATTERN, Locale.US);
        Date usDate = simpleDateFormat.parse(date);
        return DateUtil.getDateFormat(usDate, format);
    }

    public static String formatInstant(Instant instant, String format) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static String formatUtcTime(String utcTime) {
        try {
            String t = StringUtils.replace(utcTime, "T", Strings.SPACE);
            String z = StringUtils.replace(t, "Z", Strings.EMPTY);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FULL_TIME_SPLIT_PATTERN);
            LocalDateTime localDateTime = LocalDateTime.parse(z, formatter);
            LocalDateTime now = localDateTime.plusHours(8);
            return formatFullTime(now, FULL_TIME_SPLIT_PATTERN);
        } catch (Exception ignore) {
            return Strings.EMPTY;
        }
    }
}
