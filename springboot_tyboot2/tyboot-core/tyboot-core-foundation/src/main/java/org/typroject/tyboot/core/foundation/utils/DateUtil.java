package org.typroject.tyboot.core.foundation.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: DateUtil.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: DateUtil.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public class DateUtil
{

	public static final String Y_M_D = "yyyy-MM-dd";

	public static final String Y_M_D_HM = "yyyy-MM-dd HH:mm";

	public static final String Y_M_D_HMS = "yyyy-MM-dd HH:mm:ss";

	public static final String YMD = "yyyyMMdd";

	public static final String YMDHM = "yyyyMMddHHmm";

	public static final String YMDHMS = "yyyyMMddHHmmss";

	public static final String ymd = "yyyy/MM/dd";

	public static final String ymd_HM = "yyyy/MM/dd HH:mm";

	public static final String ymd_HMS = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 智能转换日期
	 *
	 * @param date
	 * @return
	 */
	public static String smartFormat(Date date)
	{
		String dateStr = null;
		if (date == null)
		{
			dateStr = "";
		}
		else
		{
			try
			{
				dateStr = formatDate(date, Y_M_D_HMS);
				// 时分秒
				if (dateStr.endsWith(" 00:00:00"))
				{
					dateStr = dateStr.substring(0, 10);
				}
				// 时分
				else if (dateStr.endsWith("00:00"))
				{
					dateStr = dateStr.substring(0, 16);
				}
				// 秒
				else if (dateStr.endsWith(":00"))
				{
					dateStr = dateStr.substring(0, 16);
				}
			}
			catch (Exception ex)
			{
				throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);
			}
		}
		return dateStr;
	}

	/**
	 * 智能转换日期
	 *
	 * @param text
	 * @return
	 */
	public static Date smartFormat(String text)
	{
		Date date = null;
		try
		{
			if (text == null || text.length() == 0)
			{
				date = null;
			}
			else if (text.length() == 10)
			{
				date = formatStringToDate(text, Y_M_D);
			}
			else if (text.length() == 13)
			{
				date = new Date(Long.parseLong(text));
			}
			else if (text.length() == 16)
			{
				date = formatStringToDate(text, Y_M_D_HM);
			}
			else if (text.length() == 19)
			{
				date = formatStringToDate(text, Y_M_D_HMS);
			}
			else
			{
				throw new IllegalArgumentException("日期长度不符合要求!");
			}
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("日期转换失败!");
		}
		return date;
	}

	/**
	 * 获取当前日期
	 * 
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static String getNow(String format) throws Exception
	{
		return formatDate(new Date(), format);
	}

	/**
	 * 格式化日期格式
	 *
	 * @param argDate
	 * @param argFormat
	 * @return 格式化后的日期字符串
	 */
	public static String formatDate(Date argDate, String argFormat) throws Exception
	{
		if (argDate == null)
		{
			throw new Exception("参数[日期]不能为空!");
		}
		if (ValidationUtil.isEmpty(argFormat))
		{
			argFormat = Y_M_D;
		}
		SimpleDateFormat sdfFrom = new SimpleDateFormat(argFormat);
		return sdfFrom.format(argDate).toString();
	}

	/**
	 * 把字符串格式化成日期
	 *
	 * @param argDateStr
	 * @param argFormat
	 * @return
	 */
	public static Date formatStringToDate(String argDateStr, String argFormat) throws Exception
	{
		if (argDateStr == null || argDateStr.trim().length() < 1)
		{
			throw new Exception("参数[日期]不能为空!");
		}
		String strFormat = argFormat;
		if (ValidationUtil.isEmpty(strFormat))
		{
			strFormat = Y_M_D;
			if (argDateStr.length() > 16)
			{
				strFormat = Y_M_D_HMS;
			}
			else if (argDateStr.length() > 10)
			{
				strFormat = Y_M_D_HM;
			}
		}
		SimpleDateFormat sdfFormat = new SimpleDateFormat(strFormat);
		// 严格模式
		sdfFormat.setLenient(false);
		try
		{
			return sdfFormat.parse(argDateStr);
		}
		catch (ParseException e)
		{
			throw new Exception(e);
		}
	}
}

/*
 * $Log: av-env.bat,v $
 */