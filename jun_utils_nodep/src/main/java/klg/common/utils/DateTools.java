package klg.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 线程安全,thread-safe<br>
 * Calendar.getInstance() is thread-safe because each call creates and returns a new instance
 * @author klguang
 *
 */
public class DateTools {

	public static final String DEF_DATE_FORMAT = DatePartten.DATE;
	public static final String DEF_TIME_FORMAT = DatePartten.TIME;
	public static final String DEF_DATETIME_FORMAT = DatePartten.DATE_TIME;



	//-------------------date parse -----------------------

	public static Date parseDate(String dateStr, String format) throws ParseException {
		return DateUtils.parseDate(dateStr, format);

	}

	public static Date parseDate(String dateStr) throws ParseException {
		return parseDate(dateStr, DEF_DATE_FORMAT);
	}

	public static Date parseDatetime(String dateStr) throws ParseException {
		return parseDate(dateStr, DEF_DATETIME_FORMAT);
	}
	
	//----------------date format --------------------------
	public static String formatDate(Date date) {
		return DateFormatUtils.format(date, DEF_DATE_FORMAT);
	}

	public static String formatDatetime(Date date) {
		return DateFormatUtils.format(date, DEF_DATETIME_FORMAT);
	}

	public static String formatTime(Date date) {
		return DateFormatUtils.format(date, DEF_TIME_FORMAT);
	}
	
	
	//-----------------date calculate------------------------
	

	public static Date getNextWeekFirstDay() {
		Calendar cal = Calendar.getInstance();
		////在今天的基础上加一周
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		//获取当前设置的一周第一天
		//每个星期的第一天根据locale不同而不同,一般默认1,周日
		int initDay = cal.getFirstDayOfWeek();
		System.out.println("initDay:"+initDay);
		//下周的第一天
		cal.set(Calendar.DAY_OF_WEEK, initDay);
		return cal.getTime();
	}

	public static Date getNextWeekLastDay() {
		Calendar cal = Calendar.getInstance();
		////在今天的基础上加一周
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		//获取当前设置的一周第一天
		//每个星期的第一天根据locale不同而不同,一般默认1,周日
		int initDay = cal.getFirstDayOfWeek();
		//下周的最后一天
		cal.set(Calendar.DAY_OF_WEEK, initDay+6);
		return cal.getTime();
	}
	
	public static Date[] getWeekDays(int yyyy,int weekOfYear){
		Date[] weekDays=new Date[7];
		Calendar cal=Calendar.getInstance();
		//设置一年中的第几周
		cal.set(Calendar.YEAR, yyyy);
		cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
		for(int i=0;i<7;i++){
			//设置周几
			cal.set(Calendar.DAY_OF_WEEK, i+1);
			weekDays[i]=cal.getTime();
		}		
		return weekDays;
	}
	
	public static Date[] getWeekDays(Date date){
		Date[] weekDays=new Date[7];
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for(int i=0;i<7;i++){
			//设置周几
			cal.set(Calendar.DAY_OF_WEEK, i+1);
			weekDays[i]=cal.getTime();
		}		
		return weekDays;
	}
	
	/**
	 * { "日", "一", "二", "三", "四", "五", "六" }
	 * 
	 * @param date
	 * @return
	 */
	public static String getCNDayofWeek(Date date) {
		String[] weekDays = { "日", "一", "二", "三", "四", "五", "六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	
	
	
	/**
	 * 字符串的日期格式的计算 相差天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 相差天数 eg：daysBetween("2012-09-08", "2012-09-09") 返回1
	 * @throws ParseException
	 */
	public static int daysBetween(Date startDate, Date endDate) {
		long time1 = startDate.getTime();
		long time2 = endDate.getTime();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	} 
	
	/**
	 * 取得当天凌晨00.00.00
	 * @return
	 */
	public static Date getTodayStartTime() {  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime();  
    }  
	
	/**
	 * 获取明天
	 * @return
	 */
	public static Date getTomorrowDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
}
