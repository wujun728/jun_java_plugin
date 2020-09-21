/*   
 * Project: OSMP
 * FileName: CustomJsonFieldDesc.java
 * version: V1.0
 */
package com.osmp.http.client.util;

import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;

public class DateUtil {
    public static String format(Date date,String datePattern){
        if(date == null) return null;
        return DateFormatUtils.format(date, datePattern,Locale.CHINA);
    }
    public static String format(Long date,String datePattern){
        if(date == null) return null;
        return DateFormatUtils.format(date, datePattern,Locale.CHINA);
    }
}
