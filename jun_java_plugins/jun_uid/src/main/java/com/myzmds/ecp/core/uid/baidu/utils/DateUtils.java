package com.myzmds.ecp.core.uid.baidu.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ DateUtils.java
 * @ <pre>ʱ丨(SimpleDateFormat̲߳ȫģ˾̬)</pre>
 * linhuaichuan1989@126.com
 * @ʱ 201978 11:15:29
 * @汾 1.0.0
 *
 * @޸ļ¼
 * <pre>
 *     汾                       ޸ 		޸ 		 ޸
 *     ----------------------------------------------
 *     1.0.0 	 	201978             
 *     ----------------------------------------------
 * </pre>
 */
public class DateUtils {
    /**
     * -ʽ
     */
    public static final String DAY_PATTERN = "yyyy-MM-dd";
    
    /**
     * ʱ-ʽ
     */
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * ʱ()-ʽ
     */
    public static final String DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    
    /**
     * df
     */
    private static ThreadLocal<Map<String, DateFormat>> dfMap = new ThreadLocal<Map<String, DateFormat>>() {
        @Override
        protected Map<String, DateFormat> initialValue() {
            return new HashMap<String, DateFormat>(10);
        }
    };
    
    /**
     * @ getDateFormat
     * @ <pre>һDateFormat,ÿֻ߳newһpatternӦsdf</pre>
     * @param pattern ʽ ʽ
     * @return DateFormat
     */
    private static DateFormat getDateFormat(final String pattern) {
        Map<String, DateFormat> tl = dfMap.get();
        DateFormat df = tl.get(pattern);
        if (df == null) {
            df = new SimpleDateFormat(pattern);
            tl.put(pattern, df);
        }
        return df;
    }
    
    /**
     * @ formatByDateTimePattern
     * @ <pre>ȡ'yyyy-MM-dd HH:mm:ss'ʽʱַ</pre>
     * @param date ʱ
     * @return ʱַ
     */
    public static String formatByDateTimePattern(Date date) {
        return getDateFormat(DATETIME_PATTERN).format(date);
    }
    
    /**
     * @ parseByDayPattern
     * @ <pre>'yyyy-MM-dd'ʽʱ</pre>
     * @param str ʱַ
     * @return ʱ
     */
    public static Date parseByDayPattern(String str) {
        return parseDate(str, DAY_PATTERN);
    }
    
    /**
     * @ parseDate
     * @ <pre>ָʽʱ</pre>
     * @param str ʱַ
     * @param pattern ʽʽ
     * @return ʱ
     */
    public static Date parseDate(String str, String pattern) {
        try {
            return getDateFormat(pattern).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
