package org.typroject.tyboot.core.foundation.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 
 * <pre>
 *  Tyrest
 *  File: DateTimeUtil.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: DateTimeUtil.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public class DateTimeUtil
{
    /**
     * log
     */

	/**
	 * Time AM 
	 */
	public static final String TIME_AM 						= "AM";
	/**
	 * Time PM 
	 */
	public static final String TIME_PM 						= "PM";


	//1 Standard DATE formatter
	public final static String YYYYMM 		= "YYYYMM";
	public final static String YYYYMMDD_HOUR24_MIN_SEC 		= "YYYYMMDD HH:mm:ss";
	public final static String DATE_HOUR_MIN_SEC_AMPM 		= "MM/dd/yyyy hh:mm:ss aa";
	public final static String DATE_HOUR_MIN_AMPM 			= "MM/dd/yyyy hh:mm aa";
	public final static String DATE_AMPM 					= "MM/dd/yyyy aa";
	public final static String DATE_HOUR_MIN_SEC 			= "MM/dd/yyyy hh:mm:ss";
	public final static String DATE_HOUR_MIN 				= "MM/dd/yyyy hh:mm";
	public final static String DEFAULT_DATE 				= "MM/dd/yyyy";
	public final static String DATE_HOUR24_MIN_SEC 			= "MM/dd/yyyy HH:mm:ss";
	public final static String DATE_HOUR24_MIN 				= "MM/dd/yyyy HH:mm";
	
	//2 ISO DATE formatter
	public final static String ISO_DATE_HOUR_MIN_SEC_AMPM 	= "yyyy-MM-dd hh:mm:ss aa";
	public final static String ISO_DATE_HOUR_MIN_AMPM 		= "yyyy-MM-dd hh:mm aa";
	public final static String ISO_DATE_AMPM 				= "yyyy-MM-dd aa";
	public final static String ISO_DATE_HOUR_MIN_SEC 		= "yyyy-MM-dd hh:mm:ss";
	public final static String ISO_DATE_HOUR_MIN 			= "yyyy-MM-dd hh:mm";
	public final static String ISO_DATE 					= "yyyy-MM-dd";
	public final static String ISO_DATE_HOUR24_MIN_SEC 		= "yyyy-MM-dd HH:mm:ss";
	public final static String ISO_DATE_HOUR24_MIN 			= "yyyy-MM-dd HH:mm";
	
	//3 ISO8601 DATE formatter
	public final static String ISO8601_DATE_HOUR_MIN_SEC 	= "yyyy-MM-dd'T'hh:mm:ss";
	public final static String ISO8601_DATE_HOUR24_MIN_SEC 	= "yyyy-MM-dd'T'HH:mm:ss";
	
	public final static String HOUR_MIN_SEC_AMPM			= "hh:mm:ss aa";
	public final static String HOUR_MIN_SEC					= "hh:mm:ss";
	public final static String HOUR_MIN_AMPM				= "hh:mm aa";
	public final static String HOUR24_MIN_SEC				= "HH:mm:ss";
	public final static String HOUR24_MIN					= "HH:mm";
	public final static String HOUR_MIN						= "hh:mm";
	public final static String AMPM							= "aa";
	public final static String ISO8601						= "T";
	
	
	public final static int MINUTE_MILLION 					= 1000 * 60;
	public final static int HOUR_MILLION 					= 60 * MINUTE_MILLION;
	public final static int DAY_MILLION 					= 24 * HOUR_MILLION;
	
	private static Calendar calendar = Calendar.getInstance();
	

	/**
	 * Gets the current agency date (e.g. right now).
	 * 
	 * @return current agency time of Date type
	 **/
	@Deprecated
	public static Date getCurrentDate()
	{
		
		return new Date(calendar.getTime().getTime());
		
	}




	/**
	 * 
	 * Returns a new Date initialized to the value represented by the following specified string' formatter:
	 * 			1. "yyyy-MM-dd'T'hh:mm:ss"
	 * 			2. "yyyy-MM-dd"
	 * 
	 * @param strDate String date
	 * @return a formatter Date for yyyy-MM-dd'T'hh:mm:ss or yyyy-MM-dd
	 * @throws ParseException
	 */
	public static Date parseISODate(String strDate) throws ParseException
	{
		if (ValidationUtil.isEmpty(strDate))
		{
			return null;
		}
		
		String dateFormat;
		if (strDate.length() > 12)
		{
			dateFormat = ISO8601_DATE_HOUR_MIN_SEC;
		}
		else
		{
			dateFormat = ISO_DATE;
		}

		return format(strDate, dateFormat);
	}

	/**
	 * Returns a new Date initialized to the value represented by the the specified string
	 * It accepts formats described by the following syntax: 
	 * 		yyyy-MM-dd
	 * 		yyyy-MM-dd(T)aa
	 * 		yyyy-MM-dd(T)hh:mm aa
	 * 		yyyy-MM-dd(T)hh:mm:ss aa
	 * 		MM/dd/yyyy
	 * 		MM/dd/yyyy(T)aa
	 * 		MM/dd/yyyy(T)hh:mm aa
	 * 		MM/dd/yyyy(T)hh:mm:ss aa
	 * 
	 *  It don't recommend to invoke the method to parsed string date any more
	 *  it should be known the formatter of the string date and directly format it with known formatter by other method
	 *  
	 * @param strDate the string date to be parsed
	 * @return a Date value represented by the string argument.
	 */
	public static Date parseDate(String strDate) 
	{
	
		if (ValidationUtil.isEmpty(strDate))
		{
			return null;
		}

		String dateFormat = null;
		
		// when ADS call back the App server, the Date String in XML include the char "T"
		if (strDate.indexOf(ISO8601) > 0)
		{
        	strDate = strDate.replaceAll(ISO8601, " ");
        }

        strDate = strDate.trim().toUpperCase();
        if (ValidationUtil.isEmpty(strDate))
		{
			return null;
		}
        
		if (strDate.indexOf(TIME_AM) > 0 || strDate.indexOf(TIME_PM) > 0)
		{
			if (strDate.indexOf("-") > 0)
			{
				if (strDate.length() <= 13)
				{
					dateFormat =ISO_DATE_AMPM;// "yyyy-MM-dd aa";
				}
				else if (strDate.length() <= 19)
				{
					dateFormat = ISO_DATE_HOUR_MIN_AMPM;// "yyyy-MM-dd hh:mm aa";
				}
				else
				{
					dateFormat = ISO_DATE_HOUR_MIN_SEC_AMPM;//"yyyy-MM-dd hh:mm:ss aa";
				}
			}
			else
			{
				if (strDate.length() <= 13)
				{
					dateFormat = DATE_AMPM;//"MM/dd/yyyy aa";
				}
				else if (strDate.length() <= 19)
				{
					dateFormat = DATE_HOUR_MIN_AMPM;//"MM/dd/yyyy hh:mm aa";
				}
				else
				{
					dateFormat = DATE_HOUR_MIN_SEC_AMPM;//"MM/dd/yyyy hh:mm:ss aa";
				}
			}
		}
		else
		{
			if (strDate.indexOf("-") > 0)
			{
				if (strDate.length() <= 10)
				{
					dateFormat = ISO_DATE;//"yyyy-MM-dd";
				}
				else if (strDate.length() <= 16)
				{
					dateFormat = ISO_DATE_HOUR24_MIN;//"yyyy-MM-dd HH:mm";
				}
				else
				{
					dateFormat = ISO_DATE_HOUR24_MIN_SEC;//"yyyy-MM-dd HH:mm:ss";
				}
			}
			else
			{
				if (strDate.length() <= 10)
				{
					dateFormat = DEFAULT_DATE;// "MM/dd/yyyy";
				}
				else if (strDate.length() <= 16)
				{
					dateFormat = DATE_HOUR24_MIN;//"MM/dd/yyyy HH:mm";
				}
				else
				{
					dateFormat = DATE_HOUR24_MIN_SEC;//"MM/dd/yyyy HH:mm:ss";
				}
			}
		}

		return format(strDate,dateFormat);
	}
	
	/**
	 * 
	 * Returns a string representing the specified format date.
	 * 
	 * @param date the Date to be parsed
	 * @return a format string for yyyy-MM-dd'T'hh:mm:ss
	 */
	public static String convertToISO8601Date(Date date)
	{
		  return format(date,ISO8601_DATE_HOUR24_MIN_SEC);
	}
	
	/**
	 * Returns a string representing the specified format date.
	 *
	 * @param date the Date to be parsed
	 * @return a format string for yyyy-MM-dd HH:mm:ss
	 */
	public static String convertToISODate(Date date)
	{
		return format(date,ISO_DATE_HOUR24_MIN_SEC);
	}

	/**
	 * Returns a string representing the specified format date.
	 * 
	 * @param date the Date to be parsed
	 * @return a format string for yyyy-MM-dd
	 */
	public static String convertToXMLDate(Date date)
	{
		return format(date,ISO_DATE);
		
	}
	
	/**
	 * Convert a util date to a sql Timestamp
	 * 
	 * @param date the Date to be parsed
	 * @return java.sql.Timestamp
	 */
	public static Timestamp toTimestamp(Date date)
	{
		return date == null ? null : new Timestamp(date.getTime());
	}


	/**
	 * Convert a util Date to a sql Date
	 * 
	 * @param date the Date to be parsed
	 * @return java.sql.Date
	 */
	public static java.sql.Date toSqlDate(Date date)
	{
		return date == null ? null : new java.sql.Date(date.getTime());
	}

	/**
	 * Convert sql Date to util Date
	 * 
	 * @param date the Date to be parsed
	 * @return java.util.Date
	 */
	public static Date toUtilDate(java.sql.Date date)
	{
		return date == null ? null : new Date(date.getTime());
	}

	
	/**
	 * Convert java.sql.Timestamp to java.util.Date
	 * 
	 * @param timestamp the Timestamp to be parsed
	 * @return java.util.Date
	 */
	public static Date toUtilDate(Timestamp timestamp)
	{
		return timestamp == null ? null : new Date(timestamp.getTime());
	}

	/**
	 * Determines if these two given GregorianCalendars are in the same day
	 * 
	 * @param cal1 GregorianCalendar
	 * @param cal2 GregorianCalendar
	 * @return True means they are in the same day
	 */
	public static boolean isSameDate(GregorianCalendar cal1, GregorianCalendar cal2)
	{
		if (cal1.get(GregorianCalendar.YEAR) == cal2.get(GregorianCalendar.YEAR)
				&& cal1.get(GregorianCalendar.MONTH) == cal2.get(GregorianCalendar.MONTH)
				&& cal1.get(GregorianCalendar.DAY_OF_MONTH) == cal2.get(GregorianCalendar.DAY_OF_MONTH))
		{
			return true;
		}
		else
			return false;
	}

	/**
	 * Determines if these two given Dates are in the same day
	 * 
	 * @param date1 Date
	 * @param date2 Date
	 * @return ture means they are in the same date, otherwise false
	 */
	public static boolean isSameDate(Date date1, Date date2)
	{
		GregorianCalendar cal1 = newCalendar(date1);
		GregorianCalendar cal2 = newCalendar(date2);

		return isSameDate(cal1,cal2);
	}

	/**
	 * Adds the specified amount of days to the given Date 
	 * 
	 * @param date the specified Date
	 * @param amount the amount of date to be added to the Date.
	 * @return result date
	 */
	public static Date addDays(Date date, int amount)
	{
		GregorianCalendar tmpDate = newCalendar(date);
		tmpDate.add(GregorianCalendar.DAY_OF_MONTH, amount);
		return tmpDate.getTime();
	}

	/**
	 * Returns a GregorianCalendar representing the specified date
	 * 
	 * @param date the specified date
	 * @return the GregorianCalendar
	 */
	public static GregorianCalendar newCalendar(Date date)
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc;
	}

	/**
	 * Returns the maximum value for the specified date,The maximum value is defined as the last millisecond in the day
	 * 
	 * @param date the specified date
	 * @return the maximum value for the given day.
	 */
	public static Date getMaxValueOfOneDay(Date date)
	{
		GregorianCalendar gc = newCalendar(date);
		gc.set(GregorianCalendar.HOUR_OF_DAY, 23);
		gc.set(GregorianCalendar.MINUTE, 59);
		gc.set(GregorianCalendar.SECOND, 59);
		
		/*Donny Chen modified for fix bug 620: The problem of searching by date in MSSQL DB
		 * 
		 * At MSSQL 2000/2005, the 23:59:999 regards as next day 0:00 
		 * */
		//gc.set(GregorianCalendar.MILLISECOND, 999);
		gc.set(GregorianCalendar.MILLISECOND, 998);
		
		return gc.getTime();
	}

	/**
	 * Returns the maximum value for the specified GregorianCalendar,The maximum value is defined as the last millisecond in the day
	 * 
	 * @param gc the specified GregorianCalendar
	 * @return the maximum value for the given GregorianCalendar.
	 */
	public static Date getMaxValueOfOneDay(GregorianCalendar gc)
	{
		gc.set(GregorianCalendar.HOUR_OF_DAY, 23);
		gc.set(GregorianCalendar.MINUTE, 59);
		gc.set(GregorianCalendar.SECOND, 59);
		gc.set(GregorianCalendar.MILLISECOND, 999);
		return gc.getTime();
	}

	/**
	 * Returns the minimum value for the specified date,The minimum value is defined as the first millisecond in the day
	 * 
	 * @param date the specified date
	 * @return the minimum value for the given date
	 */
	public static Date getMinValueOfOneDay(Date date)
	{
		GregorianCalendar gc =newCalendar(date);
		return getMinValueOfOneDay(gc);
	}

	/**
	 * Returns the minimum value for the specified GregorianCalendar,The minimum value is defined as the first millisecond in the day
	 * 
	 * @param gc the specified GregorianCalendar
	 * @return the minimum value for the given GregorianCalendar
	 */
	public static Date getMinValueOfOneDay(GregorianCalendar gc)
	{
		if(gc == null)
		{
			return null;
		}
		
		gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
		gc.set(GregorianCalendar.MINUTE, 0);
		gc.set(GregorianCalendar.SECOND, 0);
		gc.set(GregorianCalendar.MILLISECOND,0);
		return gc.getTime();
	}

	/**
	 *  Returns whether this date is represents a time of whose YEAR && MONTH is equivlent to the given year && month
	 *  
	 * @param date the specified Date
	 * @param year the specified YEAR
	 * @param month the specified MONTH
	 * @return True if the given year and month is happened in the Date
	 */
	public static boolean isDayOfMonth(Date date, int year, int month)
	{
		GregorianCalendar gc = newCalendar(date);
		return (gc.get(GregorianCalendar.YEAR) == year && gc.get(GregorianCalendar.MONTH) == month);
	}

	/**
	 * Returns a number indicating the day number within the given month 
	 * 
	 * @param date the given date
	 * @return day number within the given month 
	 */
	public static int getDay(Date date)
	{
		GregorianCalendar gc = newCalendar(date);
		return gc.get(GregorianCalendar.DAY_OF_MONTH);
	}

	/**
	 * Returns a number indicating the number of days between these two dates
	 * 
	 * @param date1 the given date
	 * @param date2 the given date
	 * @return the number of days between two dates
	 */
	public static int getDiffDay(Date date1, Date date2)
	{
		long diff = getDiffMilliseconds (date1, date2);
		return new Long(diff / DAY_MILLION).intValue();
	}
	
	/**
	 * Returns whether date1 is before the time represented by the date2. 
	 * 
	 * @param date1 the given date
	 * @param date2 the given date
	 * @return True if date1 is before date2 
	 */
	public static boolean isBeforeDay(Date date1, Date date2)
	{
		return getDiffDay(date1, date2) < 0;
	}
	
	/**
	 * Checks if is all day. 00:00AM - 23:59 or 00:00AM - 24:00
	 * 
	 * @param startDateTime the start date time
	 * @param endDateTime the end date time
	 * 
	 * @return True if is all day
	 */
	public static boolean isAllDay(Date startDateTime, Date endDateTime)
	{
		boolean isAllDay = false;
		GregorianCalendar startDate = newCalendar(startDateTime);
		GregorianCalendar endDate = newCalendar(endDateTime);
		if (startDate.get(GregorianCalendar.HOUR_OF_DAY) == 0
				&& startDate.get(GregorianCalendar.MINUTE) == 0
				&& ((endDate.get(GregorianCalendar.HOUR_OF_DAY) == 23 && endDate.get(GregorianCalendar.MINUTE) == 59) || endDate
						.get(GregorianCalendar.HOUR_OF_DAY) == 24))
		{
			isAllDay = true;
		}

		return isAllDay;
	}
	
	/**
	 * This function to calculate the interval millis of two date.
	 * @param date1
	 * @param date2
	 * @return the difference of the two date in milliseconds 
	 */
	public static long getDiffMilliseconds (Date date1,Date date2)
	{
		if (date1 == null || date2 == null)
		{
			return 0;
		}
		else
		{
			Calendar c, d;
			c = new GregorianCalendar();

			c.setTime(date1);
			d = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
			long l1 = d.getTimeInMillis();

			c.setTime(date2);
			d = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
			long l2 = d.getTimeInMillis();

			return l1-l2;
		}		
	}
	
	
	/**
	 * This function to validate two date is same date.
	 * @param date1
	 * @param date2
	 * @return true -- same date, fale -- difference date
	 */
	public static boolean isSameTime(Date date1, Date date2)
	{
		if (date1 == null || date2 == null)
		{
			return false;
		}
		else
		{
			Calendar c = new GregorianCalendar();
			c.setTime(date1);
			long l1 = c.getTimeInMillis();

			Calendar d = new GregorianCalendar();
			d.setTime(date2);
			long l2 = d.getTimeInMillis();

			return l1 == l2;
		}
	}
	
	public static Date getBaselineDate()
	{
		String BASELINE_DATE = "11/04/2006";
		
		return format(BASELINE_DATE,DEFAULT_DATE);
	}
	

	
	/**
	 * Return the converted date time.
	 * @param processDate What date time will be converted.
	 * @param sourceTimeZone The timeZone of the processDate
	 * @param targetTimeZone The timeZone of converting. 
	 * @return
	 */
	public static Date covertDateToSpecificTimeZone(Date processDate,String sourceTimeZone, String targetTimeZone)
	{
		try
		{
			if(sourceTimeZone==null || "".equals(sourceTimeZone.trim()))
			{
				sourceTimeZone = "PST";
			}
			if(targetTimeZone==null || "".equals(targetTimeZone.trim()))
			{
				targetTimeZone = "PST";
			}
			
			TimeZone sourceZone = TimeZone.getTimeZone(sourceTimeZone);
			TimeZone targetZone = TimeZone.getTimeZone(targetTimeZone);
			
			Calendar cal = Calendar.getInstance(targetZone);
			cal.setTime(processDate);
	        Calendar targetCal = (Calendar)cal.clone();
	        targetCal.setTimeZone(sourceZone);
	        //Calculate the raw offset between the source Time Zone and target Time Zone.
	        //long diff = cal.get(Calendar.ZONE_OFFSET)-targetCal.get(Calendar.ZONE_OFFSET); // not include daysaving
	        // include daysaving
	        long diff = targetZone.getOffset(processDate.getTime()) - sourceZone.getOffset(processDate.getTime());
	        
	        int diffHour = Integer.parseInt(String.valueOf(diff/(60*60*1000)));
	        //Calculate the expect date time.
	        cal.add(Calendar.HOUR_OF_DAY, diffHour);
	        return new Date(cal.getTime().getTime());
		}
		catch(Exception e)
		{
			return processDate;
		}
	}
	
	
	/**
	 * Format time from 12 hours to 24 hours or reverse.
	 * 
	 * @param time
	 * @param to24Hours
	 *            true-12 hours to 24 hours format; false-24 hours to 12 hours
	 *            format
	 * @return
	 */
	public static String formatTime(String time, boolean to24Hours)
	{
		return formatTime(time, to24Hours, Locale.US);
	}

	/**
	 * Format time from 12 hours to 24 hours or reverse.
	 * 
	 * @param time
	 * @param to24Hours
	 *            true-12 hours to 24 hours format; false-24 hours to 12 hours
	 *            format
	 * @param locale Locale.      
	 * @return String.
	 */
	public static String formatTime(String time, boolean to24Hours, Locale locale)
	{
		if(ValidationUtil.isEmpty(time))
		{			
			return time;
		}
		String newTime = null;
		DateFormat SDF_12_HOURS = new SimpleDateFormat(HOUR_MIN_AMPM, locale);
		DateFormat SDF_24_HOURS = new SimpleDateFormat(HOUR24_MIN, locale);
		try
		{
			if (to24Hours)
			{
				Date date = SDF_12_HOURS.parse(time);
				newTime = SDF_24_HOURS.format(date);
			} else
			{
				Date date = SDF_24_HOURS.parse(time);
				newTime = SDF_12_HOURS.format(date);
			}
		} catch (ParseException pe)
		{
			//logger.error("formatTime:", pe);
		}
		return newTime;
	}


	



	
	/**
	 * Returns a number of days between two string dates
	 * 
	 * @param startDay start date
	 * @param endDay end date
	 * @return a number of days
	 */
	public static long getDiffDay(String startDay, String endDay)
	{
		Date startDate = format(startDay,DEFAULT_DATE);;
		Date endDate = format(endDay,DEFAULT_DATE);
		return getDiffDay(startDate,endDate);
	}
	
	/**
	 * 
	 * Returns a string date of which have been added the given amount base on such formatter:"MM/dd/yyyy"
	 *
	 * @param startDate a string date
	 * @param days a number of day
	 * @return a string date with default date formatter
	 * @throws Exception
	 */
	public static String addDays(String startDate, long days) throws Exception
	{
		Date theStartDate =format(startDate,DEFAULT_DATE);
		Date endDate = addDays(theStartDate, days);
		return format(endDate,DEFAULT_DATE);
	}
	
	/**
	 * 
	 * Adds the specified amount of day to the given date 
	 *
	 * @param startDate the given date
	 * @param days a number of day
	 * @return a new added date
	 */
	public static Date addDays(Date startDate, long days)
	{
		long addedTime = startDate.getTime() + days * DAY_MILLION;
		startDate.setTime(addedTime);
		return startDate;
	}
	
	/**
	 *  Adds the specified amount of day to the given date 
	 *
	 * @param startDate the given date
	 * @param days a number of day
	 * @return a new added date
	 */
	public static Date addDays(Date startDate, Double days)
	{
		long addedTime = startDate.getTime() + (long)(days * DAY_MILLION);
		startDate.setTime(addedTime);
		return startDate;
	}
	
	
	/**
	 * Adds the specified amount of hours to the given date 
	 *
	 * @param startDate Date
	 * @param hours a number of hours
	 * @return date
	 */
	public static Date addHours(Date startDate,long hours)
	{
		long addedTime = startDate.getTime() + hours * HOUR_MILLION;
		startDate.setTime(addedTime);
		return startDate;
	}
	
	/**
	 * Adds the specified amount of minutes to the given date 
	 *
	 * @param startDate Date
	 * @param minutes a number of minutes
	 * @return 
	 */
	public static Date addMinutes(Date startDate,long minutes)
	{
		long addedTime = startDate.getTime() + minutes * MINUTE_MILLION;
		startDate.setTime(addedTime);
		
		return startDate;
	}
	
	/**
	 * Formats a Date into a date/time string with the given formatter
	 * 
	 * @param date the Date to be parsed
	 * @param pattern the given date formatter
	 * @return a date/time string
	 */
	public static String format(Date date, String pattern)
	{
		String strDate = "";
		if (date != null && pattern!=null && !pattern.equals(""))
		{
			try
			{
				SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.US);
				strDate = formatter.format(date);				
			}
			catch (Exception e)
			{	
				//logger.error(e.toString());
			}
		}
		return strDate;
	}
	
	/**
	 * Formats a date String into a date with the given formatter
	 * 
	 * @param strDate the date string to be parsed.
	 * @param pattern the given date formatter
	 * @return a date with the given formatter
	 */
	public static Date format(String strDate, String pattern)
	{
		Date date = null;
		if (strDate != null && !strDate.equals("") && pattern!=null && !pattern.equals(""))
		{
			try
			{
				SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.US);
				date = formatter.parse(strDate);				
			}
			catch (Exception e)
			{	
				//logger.error(e.toString());
			}
		}
		return date;
	}
	
	/**
	 * check whether the string date is valid or not 
	 * 
	 * @param strDate the date string to be formatted into a date.
	 * @param pattern - the new date format
	 * @return
	 */
	public static boolean isValidDate(String strDate, String pattern)
	{
		Date toDate = format(strDate, pattern);
		if(toDate != null)
		{
			String toString = format(toDate, pattern);
			if(toString != null)
			{
				if(toString.equals(strDate))
				{
					return true;
				}
			}	
		}	
		return false;
	}
	
	/**
	 * For the date time from .net webservice client, 
	 * the null date time would assing a default value 0001-01-01,
	 * we need convert this value to null. 
	 * 
	 * @param date - date
	 */
	public static Date parseEmptyDate(Date date)
	{
		if (date != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (cal.get(Calendar.YEAR) <= 1)
			{
				return null;
			}
		}
		
		return date;
	}
	
	/**
	 * Takes a string formatted as a date and converts it into a Date object.
	 * The date string should be in one of the following formats: yyyy-MM-dd
	 * HH:mm:ss or it substring
	 * @param strDate
	 * @param pattern
	 * @return Date
	 */
	public static Date parseDate(String strDate, String pattern) 
	{
		String dateFormat = null;
		String defaultFormat = "MM/dd/yyyy";
		if (pattern != null && pattern.toUpperCase().startsWith("DD"))
		{
			defaultFormat = "dd/MM/yyyy";
		}
		if (null==strDate)
		{
			return null;
		}
		String dateString = strDate.trim().toUpperCase();
		if (dateString.length() < 1)
		{
			return null;
		}

		if (dateString.indexOf("T") > 0) //when ADS call back the Appserver, the Date String in XML includs the char "T"
		{
			if ("T".equalsIgnoreCase(dateString.substring(dateString.length() - 1, dateString.length())))
			{
				dateString = dateString.replaceAll("T", "");
			}
			else
			{
				dateString = dateString.replaceAll("T", " ");
			}
		}

		if (dateString.indexOf("AM") > 0 || dateString.indexOf("PM") > 0)
		{
			if (dateString.indexOf("-") > 0)
			{
				if (dateString.length() <= 13)
				{
					dateFormat = "yyyy-MM-dd aa";
				}
				else if (dateString.length() <= 19)
				{
					dateFormat = "yyyy-MM-dd hh:mm aa";
					//System.out.println(dateFormat + dateString);
				}
				else
				{
					dateFormat = "yyyy-MM-dd hh:mm:ss aa";
				}
			}
			else
			{
				if (dateString.length() <= 13)
				{
					dateFormat = defaultFormat + " aa";
				}
				else if (dateString.length() <= 19)
				{
					dateFormat = defaultFormat + " hh:mm aa";
					//System.out.println(dateFormat + dateString);
				}
				else
				{
					dateFormat = defaultFormat + " hh:mm:ss aa";
				}
			}
		}
		else
		{
			if (dateString.indexOf("-") > 0)
			{
				if (dateString.length() <= 10)
				{
					dateFormat = "yyyy-MM-dd";
				}
				else if (dateString.length() <= 16)
				{
					dateFormat = "yyyy-MM-dd HH:mm";
					//System.out.println(dateFormat + dateString);
				}
				else
				{
					dateFormat = "yyyy-MM-dd HH:mm:ss";
				}
			}
			else
			{
				if (dateString.length() <= 10)
				{
					dateFormat = defaultFormat;
				}
				else if (dateString.length() <= 16)
				{
					dateFormat = defaultFormat + " HH:mm";
					//System.out.println(dateFormat + dateString);
				}
				else
				{
					dateFormat = defaultFormat + " HH:mm:ss";
				}
			}
		}
		return format(dateString,dateFormat);
	}
	
	/**
	 * Formats the given Date into a given format string.
	 * 
	 * @param date the date value to be formatted into a string.
	 * 
	 * @return the string
	 */
	public static String format(Date date)
	{
		return format(date, DateTimeUtil.DEFAULT_DATE);
	}
	
	
	/**
	 * The method combine Date and Time
	 * @param date
	 * @param time
	 * @param formatStr
	 * @param local
	 * @return
	 */
	public static Date combineDateTime(Date date, String time, String formatStr, Locale local)
	{
		Date newDate = date;
		if(date!=null && time!=null && time.length()>0)
		{	
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			String s_date = sdf.format(date);
			StringBuilder sbDateTmp = new StringBuilder(30);
			sbDateTmp.append(s_date);
			sbDateTmp.append(" ");
			sbDateTmp.append(time);
			s_date = sbDateTmp.toString();
			
			StringBuilder dtFormat = new StringBuilder(30);
			dtFormat.append(formatStr);
			dtFormat.append(" ");
			
			if(time.toUpperCase().endsWith(TIME_AM) || time.toUpperCase().endsWith(TIME_PM) || time.indexOf(" ") > 0)
			{
				//if the time format is "hh:mm aa"
				dtFormat.append(HOUR_MIN_AMPM);
			}
			else
			{
				//if the time format is "HH:mm"
				dtFormat.append(HOUR_MIN);
			}
			
			try
			{
				if(local == null)
				{
					sdf = new SimpleDateFormat(dtFormat.toString());					
				}
				else
				{
					sdf = new SimpleDateFormat(dtFormat.toString(),local);
				}
				
				newDate = sdf.parse(s_date);
			}
			catch(Exception e)
			{	
				//Catch the exception, do nothing, the original Date will be returned.
			}
		}
		return newDate;
	}
	
	/**
	 * Gets the hours.
	 * 
	 * @param startDay the start day
	 * @param endDay the end day
	 * 
	 * @return the hours
	 */
	public static double getHours(Date startDay, Date endDay)
	{
		double theHours = (endDay.getTime() - startDay.getTime()) / (double) HOUR_MILLION;
		BigDecimal bg = new BigDecimal(theHours);
		BigDecimal result = bg.setScale(2, BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
	
	/**
	 * Converts a global string date to a specific formatter'string date
	 * 
	 * @param strDate global string date
	 * @param dateFormat the given date formatter
	 * @return a string date with specific formatter
	 */
	public static String parseGlobalDate(String strDate, String dateFormat)
	{
	    if (ValidationUtil.isEmpty(strDate) || ValidationUtil.isEmpty(dateFormat) )
        {
            return strDate;
        }
	    //Convert a global string date to be a Date with default date formatter
	    Date date = format(strDate, DEFAULT_DATE);
	    
	    String retDate=null;
	    //Convert the date to a string with date given formatter
        if (date != null)
        {
        	retDate = format(date,dateFormat);
        }
        
        return retDate;
	}
	
	/**
	 * get smaller date 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date getSmallerDate(Date date1, Date date2)
	{
		return date1.before(date2) ? date1 : date2;
	}
	
	
	/**
	 * get bigger date
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date getBiggerDate(Date date1, Date date2)
	{
		return date1.after(date2) ? date1 : date2;
	}

	/**
	 * Compare time.
	 * 
	 * @param time1 the time1
	 * @param time2 the time2
	 * 
	 * @return true, if they are equal or time1 and time2 both are null
	 */
	
	public static boolean compareDate(Date time1, Date time2) throws Exception
	{
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try
		{
			c1.setTime(time1);
			c2.setTime(time2);
		}
		catch (Exception e)
		{
			System.err.println("格式不正确");
		}
		int result = c1.compareTo(c2);
		if (result <= 0)
			return true;
			//System.out.println("c1小相等c2");
		else
			return false;
			//System.out.println("c1大于c2");
			
	}
	
	
	public final static DateFormat SDF_24_HOURS = new SimpleDateFormat(HOUR24_MIN, Locale.US);
	
	/**
	 * 
	 * TODO.
	 *
	 * @param date1
	 * @param date2
	 * @param compareType have Calendar.YEAR,Calendar.MONTH,Calendar.DATE,Calendar.HOUR_OF_DAY,Calendar.MINUTE,Calendar.SECOND. we will compare two date according the compare type.
	 * @return if date1 before date2, return -1, if they equal each other, return 0, else if return 1 . 
	 */
	public static int compareTime(Date date1, Date date2, int compareType)
	{
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		c1.clear(Calendar.MILLISECOND);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		c2.clear(Calendar.MILLISECOND);
		switch (compareType)
		{
			case Calendar.YEAR:
				c1.set(c1.get(Calendar.YEAR), 0, 1, 0, 0, 0);
				c2.set(c2.get(Calendar.YEAR), 0, 1, 0, 0, 0);
				break;
			case Calendar.MONTH:
				c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), 1, 0, 0, 0);
				c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), 1, 0, 0, 0);
				break;
			case Calendar.DATE:
				c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DATE), 0, 0, 0);
				c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DATE), 0, 0, 0);
				break;
			case Calendar.HOUR:
				c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DATE), c1.get(Calendar.HOUR_OF_DAY), 0, 0);
				c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DATE), c2.get(Calendar.HOUR_OF_DAY), 0, 0);
				break;
			case Calendar.MINUTE:
				c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DATE), c1.get(Calendar.HOUR_OF_DAY), c1.get(Calendar.MINUTE), 0);
				c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DATE), c2.get(Calendar.HOUR_OF_DAY), c2.get(Calendar.MINUTE), 0);
				break;
			case Calendar.SECOND:
				c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DATE), c1.get(Calendar.HOUR_OF_DAY), c1.get(Calendar.MINUTE), c1.get(Calendar.SECOND));
				c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DATE), c2.get(Calendar.HOUR_OF_DAY), c2.get(Calendar.MINUTE), c2.get(Calendar.SECOND));
				break;
			default:
				c1.setTime(date1);
				c2.setTime(date2);
				break;
		}
		
		return c1.compareTo(c2);
	}
	

	
	
	public static Date addYears(Date date, int years)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, years);
		
		return calendar.getTime();
	}


	public static Date addMonths(Date date, int months)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);

		return calendar.getTime();
	}

}
