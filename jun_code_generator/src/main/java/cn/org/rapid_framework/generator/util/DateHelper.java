package cn.org.rapid_framework.generator.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class DateHelper {

    public static Date parseDate(String value,Class targetType,String... formats) {
        for(String format : formats) {
            try {
                long v = new SimpleDateFormat(format).parse(value).getTime();
                return (Date)targetType.getConstructor(long.class).newInstance(v);
            }catch(ParseException e) {
            }catch(Exception e) {
                throw new RuntimeException(e);
            }
            try {
                return (Date)targetType.getConstructor(String.class).newInstance(value);
            }catch(Exception e) {
            }
        }
        throw new IllegalArgumentException("cannot parse:"+value+" for date by formats:"+Arrays.asList(formats));
    }

    public static boolean isDateType(Class<?> targetType) {
        if(targetType == null) return false;
        return targetType == java.util.Date.class || targetType == java.sql.Timestamp.class || targetType == java.sql.Date.class || targetType == java.sql.Time.class;
    }
    
}
