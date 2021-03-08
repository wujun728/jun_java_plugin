package cn.com.bluemoon.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 
 * @Description: 日期工具类
 * @author Wujun
 * @date Nov 13, 2012
 * @version V1.0
 */
public class DateUtil {
	
	/**
	 * get current date
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1238226379012286690L;
    /**
     * AM/PM
     */
    public static final String AM_PM = "a";
    /**
     * 一个月里第几天
     */
    public static final String DAY_IN_MONTH = "dd";
    /**
     * 一年里第几天
     */
    public static final String DAY_IN_YEAR = "DD";
    /**
     * 一周里第几天(从Sunday开始)
     */
    public static final String DAY_OF_WEEK = "EEEE";
    /**
     * 以天为单位
     */
    public static final int DIFF_DAY = Calendar.DAY_OF_MONTH;
    /**
     * 以小时为单位
     */
    public static final int DIFF_HOUR = Calendar.HOUR_OF_DAY;
    /**
     * 以毫秒为单位
     */
    public static final int DIFF_MILLSECOND = Calendar.MILLISECOND;
    /**
     * 以分钟为单位
     */
    public static final int DIFF_MINUTE = Calendar.MINUTE;
    /**
     * 以月份为单位，按照每月30天计算
     */
    public static final int DIFF_MONTH = Calendar.MONTH;
    /**
     * 以秒为单位
     */
    public static final int DIFF_SECOND = Calendar.SECOND;
    /**
     * 以星期为单位，按照每星期7天计算
     */
    public static final int DIFF_WEEK = Calendar.WEEK_OF_MONTH;
    /**
     * 以年为单位，按照每年365天计算
     */
    public static final int DIFF_YEAR = Calendar.YEAR;
    /**
     * 半天内小时(0-11)
     */
    public static final String HOUR_IN_APM = "KK";
    /**
     * 一天内小时(0-23)
     */
    public static final String HOUR_IN_DAY = "HH";
    /**
     * 半天内小时(1-12)
     */
    public static final String HOUR_OF_APM = "hh";
    /**
     *  一天内小时(1-24)
     */
    public static final String HOUR_OF_DAY = "kk";

    /**
     * 年(四位)
     */
    public static final String LONG_YEAR = "yyyy";
    /**
     * 毫秒
     */
    public static final String MILL_SECOND = "SSS";
    /**
     * 分钟
     */
    public static final String MINUTE = "mm";
    /**
     * 月
     */
    public static final String MONTH = "MM";
    /**
     * 秒
     */
    public static final String SECOND = "ss";
    /**
     * 年(二位)
     */
    public static final String SHORT_YEAR = "yy";
    /**
     * 一个月里第几周
     */
    public static final String WEEK_IN_MONTH = "W";
    /**
     * 一年里第几周
     */
    public static final String WEEK_IN_YEAR = "ww";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     * 日期格式
     */
    private static final String[] PARSE_PATTERNS = {
        "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
        "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
        "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"
    };
    /**
     * 检查目的时间是否已超过源时间值加上时间段长度
     * <p>
     * 用于判别当前是否已经超时
     *
     * @param destDate 目的时间，一般为当前时间
     * @param sourceDate 源时间，一般为事件产生时间
     * @param type 时间计算单位，为分钟、小时等
     * @param elapse 持续时间长度
     * @return 是否超时
     * @throws RuntimeException
     */
    public static boolean compareElapsedTime(
            Date destDate,
            Date sourceDate,
            int type,
            int elapse)
            throws RuntimeException {
        if (destDate == null || sourceDate == null)
            throw new RuntimeException("compared date invalid");

        return destDate.getTime() > getRelativeDate(sourceDate, type, elapse).getTime();
    }

    /**
     * 取当前时间字符串
     * <p>
     * 时间字符串格式为：年(4位)-月份(2位)-日期(2位) 小时(2位):分钟(2位):秒(2位)
     * @return 时间字符串
     */
    public static String getCurrentDateString() {
        return getCurrentDateString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按格式取当前时间字符串
     * <p>
     * @param formatString 格式字符串
     * @return
     */
    public static String getCurrentDateString(String formatString) {
        Date currentDate = new Date();

        return getDateString(currentDate, formatString);
    }
    
    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     *
     * @param str the str
     * @return the date
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(str.toString(), PARSE_PATTERNS);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 取当天在一周的第几天
     * <p>
     * @return int CurrentDayOfWeek
     */
    public static int getCurrentDayOfWeek() {
        return getDayOfWeek(new Date());
    }

    public static Date getDate(Date date) {
        return getDateFromString(getDateString(date, "yyyy-MM-dd"), "yyyy-MM-dd");
    }

    /**
     * 根据时间字符串生成时间
     *
     * @param dateString 时间字符串格式
     * @return 时间
     * @throws RuntimeException
     */
    public static Date getDateFromString(String dateString)
            throws RuntimeException {
        return getDateFromString(dateString, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 根据时间字符串生成时间
     *
     * @param dateString 时间字符串       格式 yyyy-MM-dd
     * @return 时间
     * @throws RuntimeException
     */
    public static Date getDateFromString1(String dateString)
            throws RuntimeException {
        return getDateFromString(dateString, "yyyy-MM-dd");
    }
	/**
	 * 字符转换为日期。
	 * 
	 * @param source
	 * @param patterns日期格式串如yyyy
	 *            -MM-dd HH:mm:ss
	 * @return
	 */
	public static Date stringToDate(String source, String patterns) {
		return stringToDate(source, patterns, true);
	}
	/**
	 * 字符转换为日期。
	 * 
	 * @param source
	 * @param patterns日期格式串如yyyy
	 *            -MM-dd HH:mm:ss
	 * @param locate
	 *            true--转化为东八区时间
	 * @return
	 */
	public static Date stringToDate(String source, String patterns,
			boolean locate) {
		if (locate)
			return stringToDate(source, patterns, "GMT+8");
		else
			return stringToDate(source, patterns, "");
	}
	/**
	 * 字符串转换为指定时区时间
	 * 
	 * @param source
	 * @param patterns
	 * @param timeZone如东八区GMT
	 *            +8
	 * @return
	 */
	public static Date stringToDate(String source, String patterns,
			String timeZone) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patterns);
		Date date = null;
		if (source == null)
			return date;
		if (timeZone != null && !timeZone.trim().equals(""))
			dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		try {
			date = dateFormat.parse(source);
		} catch (java.text.ParseException e) {
			System.out.println("[string to date]" + e.getMessage());
		}

		return date;
	}
    /**
     * 根据字符串生成时间
     *
     * @param dateString 时间字符串
     * @param pattern 时间字符串格式定义
     * @return 时间
     * @throws RuntimeException
     */
    public static Date getDateFromString(String dateString, String pattern)
            throws RuntimeException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(
                    "parse date string '"
                            + dateString
                            + "' with pattern '"
                            + pattern
                            + "' failed: "
                            + e.getMessage());
        }

        return date;
    }

    /**
     * 取时间字符串
     *
     * @param date 时间
     * @return 时间字符串
     */
    public static String getDateString(Date date) {
        return getDateString(date, "yyyy-MM-dd");
    }

    /**
     * 取时间字符串
     *
     * @param date 时间
     * @param formatString 转换格式
     * @return 时间字符串
     */
    public static String getDateString(Date date, String formatString) {
        return getDateString(date, formatString, Locale.PRC);
    }

    /**
     * 取时间字符串
     *
     * @param date 时间
     * @param formatString 转换格式
     * @param locale 地区
     * @return 时间字符串
     */
    public static String getDateString(Date date, String formatString, Locale locale) {
        if (date == null)
            return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString, locale);

        return dateFormat.format(date);
    }


    /**
     * 取日期在一周的第几天
     *
     * @param date 日期
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    /**
     * 取日期在一周的星期几
     *
     * @param date 日期
     * @return
     */
    public static String getDayOfWeekStr(String date) {
    	int weeks=getDayOfWeek(getDateFromString1(date));
    	String weekStr="";
    	switch (weeks) {
		case 1:weekStr="星期日";break;
		case 2:weekStr="星期一";break;
		case 3:weekStr="星期二";break;
		case 4:weekStr="星期三";break;
		case 5:weekStr="星期四";break;
		case 6:weekStr="星期五";break;
		case 7:weekStr="星期六";break;
		}
        return weekStr;
    
    }
    
    /**
     * 取日期在一周的星期几
     *
     * @param date 日期
     * @return
     */
	 public static String getDayOfWeekStr(Date date) {
	    	int weeks=DateUtil.getDayOfWeek(date);
	    	String weekStr="";
	    	switch (weeks) {
			case 1:weekStr="周日";break;
			case 2:weekStr="周一";break;
			case 3:weekStr="周二";break;
			case 4:weekStr="周三";break;
			case 5:weekStr="周四";break;
			case 6:weekStr="周五";break;
			case 7:weekStr="周六";break;
			}
	        return weekStr;
	    
	    }
    /**
     * 取日期在一月的第几天
     *
     * @param date 日期
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取一个月的最大天数
     *
     * @param date 日期
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取日期所在月份的最大天数
     *
     * @param date 日期
     * @return
     */
    public static int getMaximumDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据源时间和时长计算目的时间
     *
     * @param date 源时间
     * @param type 时间单位
     * @param relate 时长
     * @return 目的时间
     */
    public static Date getRelativeDate(Date date, int type, int relate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, relate);

        return calendar.getTime();
    }

    /**
     * 根据当前时间和时长计算目的时间
     *
     * @param type 时间单位
     * @param relate 时长
     * @return 目的时间
     */
    public static Date getRelativeDate(int type, int relate) {
        Date current = new Date();

        return getRelativeDate(current, type, relate);
    }

    /**
     * 根据当前时间和时长生成目的时间字符串
     *
     * @param type 时间单位
     * @param relate 时长
     * @param formatString 时间格式
     * @return 时间字符串
     */
    public static String getRelativeDateString(
            int type,
            int relate,
            String formatString) {
        return getDateString(getRelativeDate(type, relate), formatString);
    }

    /**
     * 取时间戳字符串
     *
     * @param date 时间
     * @return 时间戳字符串
     */
    public static String getTimestampString(Date date) {
        return getDateString(date, "yyyyMMddHHmmssSSS");
    }

    /**
     * 取当天日期值
     *
     * @return 日期的整数值
     */
    public static int getToday() {
        return Integer.parseInt(getCurrentDateString("dd"));
    }

    public static long getTimeDiff(Date fromDate, Date toDate, int type) {
        fromDate = (fromDate == null) ? new Date() : fromDate;
        toDate = (toDate == null) ? new Date() : toDate;
        long diff = toDate.getTime() - fromDate.getTime();

        switch(type) {
            case DIFF_MILLSECOND:
                break;

            case DIFF_SECOND:
                diff /= 1000;
                break;

            case DIFF_MINUTE:
                diff /= 1000 * 60;
                break;

            case DIFF_HOUR:
                diff /= 1000 * 60 * 60;
                break;

            case DIFF_DAY:
                diff /= 1000 * 60 * 60 * 24;
                break;

            case DIFF_MONTH:
                diff /= 1000 * 60 * 60 * 24 * 30;
                break;

            case DIFF_YEAR:
                diff /= 1000 * 60 * 60 * 24 * 365;
                break;

            default:
                diff = 0;
                break;
        }

        return diff;
    }

    /**
     * 比较时间戳是否相同
     *
     * @param arg0 时间
     * @param arg1 时间
     * @return 是否相同
     */
    public static boolean isTimestampEqual(Date arg0, Date arg1) {
        return getTimestampString(arg0).compareTo(getTimestampString(arg1)) == 0;
    }

    /**
     * 判断给定日期是否超过参照时间
     * <br>
     * @param srcTime	准备操作处理的日期
     * @param refTime	作为标准的参考日期
     * @return	boolean
     */
    public static boolean isTimestampPassed(Date srcTime,Date refTime){
        boolean isPassed;
        int flag = srcTime.compareTo(refTime);
        if(flag >= 0){
            isPassed = true;
        }else{
            isPassed = false;
        }
        return isPassed;
    }

    /**
     * 将java.sql.Date时间装换为java.util.Date时间
     * @return java.util.Date
     */
    public static java.util.Date getUtilDate(java.sql.Timestamp timestamp){
        if(timestamp==null){
            return null;
        }
        java.util.Date utilDate = new java.util.Date(timestamp.getTime());
        return utilDate;
    }

    /**
     * 将java.util.Date时间装换为java.sql.Date时间
     * @param utilDate			java.util.Date
     * @return java.sql.Date	yyyy-MM-dd格式的日期，不带时间
     */
    public static java.sql.Date getSQLDate(java.util.Date utilDate){
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }

    /**
     * 将java.util.Date转换为java.sql.Timestamp时间
     * @param utilDate java.util.Date
     * @return java.sql.Timestamp
     */
    public static java.sql.Timestamp getSQLTimeStamp(java.util.Date utilDate){
        if(null == utilDate || "".equals(utilDate)){
            return null;
        }else{
            java.sql.Timestamp timestamp = new java.sql.Timestamp(utilDate.getTime());
            return timestamp;
        }
    }
	public static List<String[]> getDays(int i) {
		List<String[]> dates = new ArrayList<String[]>();
		//SimpleDateFormat format = new SimpleDateFormat("MM月dd日 E", Locale.CHINA);
		SimpleDateFormat format1 = new SimpleDateFormat("MM月dd日", Locale.CHINA);
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		for (int j = 0; j < i; j++) {
			String str = "";
			if (j == 0) {
				str = "今天  ";
				dates.add(new String[] { str+format1.format(calendar.getTime()).toString(), timeToString2(calendar.getTime()).toString() });
			} else {
				dates.add(new String[] { format1.format(calendar.getTime()).toString(), timeToString2(calendar.getTime()).toString() });
			}
			calendar.add(Calendar.DATE, 1);
		}
		//测试用
		//dates.add(new String[]{"今天","2013-10-17"});
		//dates.add(new String[]{"10月18日","2013-10-18"});
		//dates.add(new String[]{"10月19日","2013-10-19"});
		//dates.add(new String[]{"10月20日","2013-10-20"});
		return dates;
	}
	public static String timeToString2(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern("yyyy-MM-dd");
		String str = null;
		if (date == null)
			return null;
		str = simpleDateFormat.format(date);
		return str;
	}
    /**
     * 获得指定日期的前一天
     * 
    * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

       String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }

   /**
     * 获得指定日期的后一天
     * 
    * @param specifiedDay
     * @return
     */
   public static String getSpecifiedDayAfter(String specifiedDay) {
       Calendar c = Calendar.getInstance();
       Date date = null;
       try {
           date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
       } catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    }
       c.setTime(date);
       int day = c.get(Calendar.DATE);
       c.set(Calendar.DATE, day + 1);

       String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
       return dayAfter;
   }
    /**
    * 计算两个日期的时间差
    * @param formatTime1
    * @param formatTime2
    * @return
    */
    public static String getTimeDifference(Date formatTime1, Date formatTime2) {
    SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
    long t1 = 0L;
    long t2 = 0L;
    try {
		t1 = timeformat.parse(getTimeStampNumberFormat(formatTime1)).getTime();
	} catch (java.text.ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
		t2 = timeformat.parse(getTimeStampNumberFormat(formatTime2)).getTime();
	} catch (java.text.ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    //因为t1-t2得到的是毫秒级,所以要初3600000得出小时.算天数或秒同理
    int hours=(int) ((t1 - t2)/3600000);
    int minutes=(int) (((t1 - t2)/1000-hours*3600)/60);
    int second=(int) ((t1 - t2)/1000-hours*3600-minutes*60);
    long ms= (long)((t1 - t2)/1000-hours*3600-minutes*60*1000);
    return ""+hours+"小时"+minutes+"分"+second+"秒"+ms+"毫秒";
    }
    
    
    /**
     * 计算两个日期的时间差（毫秒）
     * @param formatTime1
     * @param formatTime2
     * @return
     */
     public static long getTimeDifferenceMS(Date formatTime1, Date formatTime2) {
     SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
     long t1 = 0L;
     long t2 = 0L;
     try {
 		t1 = timeformat.parse(getTimeStampNumberFormat(formatTime1)).getTime();
 	} catch (java.text.ParseException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
     try {
 		t2 = timeformat.parse(getTimeStampNumberFormat(formatTime2)).getTime();
 	} catch (java.text.ParseException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
    
     long ms= t1 - t2;
     return ms;
     }
    
    
    
    /**
    * 格式化时间
    * Locale是设置语言敏感操作
    * @param formatTime
    * @return
    */
    public static String getTimeStampNumberFormat(Date formatTime) {
    SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", new Locale("zh", "cn"));
    return m_format.format(formatTime);
    }
    
    /**
     * 对应获取时间
     * @param iParam
     * @return
     */
    public static String getAfterNDayStr(int iParam) {// iParam天后日期
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, iParam);
		String sMonth = (now.get(Calendar.MONTH) + 1) > 9 ? ""
				+ (now.get(Calendar.MONTH) + 1) : "0"
				+ (now.get(Calendar.MONTH) + 1);
		String sDay = now.get(Calendar.DATE) > 9 ? "" + now.get(Calendar.DATE)
				: "0" + now.get(Calendar.DATE);
		String sHH = now.get(Calendar.HOUR_OF_DAY) > 9 ? ""
				+ now.get(Calendar.HOUR_OF_DAY) : "0"
				+ now.get(Calendar.HOUR_OF_DAY);
		String sMM = now.get(Calendar.MINUTE) > 9 ? ""
				+ now.get(Calendar.MINUTE) : "0" + now.get(Calendar.MINUTE);
		String sAfterNDay = now.get(Calendar.YEAR) + "-" + sMonth + "-" + sDay
				+ "," + sHH + sMM;
		return sAfterNDay;
	}
    
    /**当前时间+n天
     * @param n
     * @return
     */
    public static String getDateByNowDate(int n){
    	String moreDate  = "";
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE,n);
        moreDate  =(new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
        return moreDate;
    }
    
    /**当前月份
     * @param n
     * @return
     */
    public static int getCurrMonthe(){
    	Date date = new Date();
    	int currMonthe=date.getMonth()+1;
        return currMonthe;
    }
    
    /**
     * 获取时间戳
     * @param date
     * @return
     */
    public static long getDateTimestamp(Date date){
    	Long time = date.getTime();
//    	String times = String.valueOf(time);
//    	times = times.substring(0, 10);
//    	time = Long.valueOf(times);
    	return time;
    }
    
    /**
     * 获取时间戳
     * @param date
     * @return
     */
    public static long getDateTimestamp2(Date date){
    	Long time = date.getTime();
    	String times = String.valueOf(time);
    	times = times.substring(0, 10);
    	time = Long.valueOf(times);
    	return time;
    }
    
    /**
     * 根据时间戳获取日期
     * @param timestamp
     * @return
     */
    public static Date getDateBylong(Long timestamp){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lcc_time = Long.valueOf(timestamp);
		String re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return getDateFromString(re_StrTime);
    }
    
    /**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
    
    /**
     * 获取日期起始时间
     * @param date
     * @return
     */
    public static Date getDayStartTime(Date date) {  
        Calendar dayStart = Calendar.getInstance();
        dayStart.setTime(date);
        dayStart.set(Calendar.HOUR, 0);  
        dayStart.set(Calendar.MINUTE, 0);  
        dayStart.set(Calendar.SECOND, 0);  
        dayStart.set(Calendar.MILLISECOND, 0);  
        return dayStart.getTime();  
    }

    /**
     * 获取日期起始时间
     * @param date
     * @return
     */
    public static Date getDayStartTimeBySecond(Date date) {
        Calendar dayStart = Calendar.getInstance();
        dayStart.setTime(date);
        dayStart.set(Calendar.SECOND, 0);
        dayStart.set(Calendar.MILLISECOND, 0);
        return dayStart.getTime();
    }

    /**
     * 获取日期终结时间
     * @param date
     * @return
     */
    public static Date getDayEndTime(Date date) {  
        Calendar dayEnd = Calendar.getInstance();
        dayEnd.setTime(date);
        dayEnd.set(Calendar.HOUR, 23);  
        dayEnd.set(Calendar.MINUTE, 59);  
        dayEnd.set(Calendar.SECOND, 59);  
        dayEnd.set(Calendar.MILLISECOND, 999);
        return dayEnd.getTime();  
    }

    public static Date getDayEndTimeBySecond(Date date) {
        Calendar dayEnd = Calendar.getInstance();
        dayEnd.setTime(date);
        dayEnd.set(Calendar.SECOND, 59);
        //dayEnd.set(Calendar.MILLISECOND, 999);
        return dayEnd.getTime();
    }

    public static Date getWeekStartDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }

    public static Date getMoonStartDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }

}

