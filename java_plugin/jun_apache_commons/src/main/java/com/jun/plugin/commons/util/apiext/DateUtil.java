package com.jun.plugin.commons.util.apiext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.commons.util.constant.MathConvertType;
import com.jun.plugin.commons.util.constant.SimpleDateFormatCase;
import com.jun.plugin.commons.util.constant.StrPattern;
import com.jun.plugin.commons.util.exception.ProjectException;

/**
 * @ClassName: DateUtil
 * @Description: 时间相关的常用用法
 * @author Wujun
 * @date 2010-10-29 下午01:37:06
 * 
 */
public abstract class DateUtil {
	public static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static Date objToDate(Object ojbDate, DateFormat formate) {
		if (ojbDate == null) {
			return null;
		}
		Date date = null;
		if (ojbDate instanceof Date) {
			date = (Date) ojbDate;
		} else if (ojbDate instanceof java.sql.Date) {
			java.sql.Date tempObj = (java.sql.Date) ojbDate;
			date = new java.util.Date(tempObj.getTime());
		} else if (ojbDate instanceof XMLGregorianCalendar) {
			date = xmlDateToDate((XMLGregorianCalendar) ojbDate);
		} else if (ojbDate instanceof String) {
			String strDate = String.valueOf(ojbDate);
			try {
				if (formate != null) {
					date = formate.parse(strDate);
				} else {
					if (StrPattern.date.checkStrFormat(strDate)) {
						strDate = strDate + " 00:00:00";
					}
					if (StrPattern.date_time.checkStrFormat(String
							.valueOf(strDate))
							&& !"0001-01-01 00:00:00".equals(ojbDate)
							&& !"1970-01-01 00:00:00".equals(ojbDate)) {
						date = SimpleDateFormatCase.YYYY_MM_DD_hhmmss
								.getInstanc().parse(strDate);
					}
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("String转换为Date类型错误");
			}
		}

		if (date == null) {
			throw new IllegalArgumentException("不支持的数据类型，不能转为Date类型");
		} else {
			return date;
		}
	}

	/****
	 * 把Bean的某个字段格式化为String
	 * 
	 * @param bean
	 *            要格式化的Bean
	 * @param path
	 *            　　　要格式化的字段所在路径
	 * @param format
	 *            要格式化的格式
	 * @param isTime
	 *            是不是时间，默认为日期
	 * @return
	 */
	public static String formatDate(Object bean, String path, String format,
			Boolean isTime) {
		if (StringUtil.isNull(path)) {
			return "";
		}
		try {
			Object obj = PropertyUtils.getProperty(bean, path);
			Date curDate = DateUtil.objToDate(obj);
			if (curDate == null) {
				return "";
			}
			return formatDate(curDate, format, isTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/***
	 * 把Date格式化为String ，时间：yyyy-MM-dd HH:mm:ss 日期：yyyy-MM-dd
	 * 
	 * @param oriDate
	 *            要格式化的时间
	 * @param format
	 *            要格式化的格式
	 * @param isTime
	 *            是不是时间，默认为日期
	 * @return
	 */
	public static String formatDate(Date oriDate, String format, Boolean isTime) {
		if (oriDate == null) {
			return "";
		}
		String formatStr = StringUtil.hasNull(format,
				(isTime != null && isTime) ? "yyyy-MM-dd HH:mm:ss"
						: "yyyy-MM-dd");
		SimpleDateFormat curFormat = new SimpleDateFormat(formatStr);
		return curFormat.format(oriDate);
	}

	/****
	 * 把对象转换成Date Formate为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param xmlDate
	 * @return
	 */
	public static Date objToDate(Object ojbDate) {
		return objToDate(ojbDate, null);
	}

	/**
	 * 今天的0时0分0秒0毫秒
	 * */
	public static Date setDayBeginTime(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	/***
	 * 指定时间的相隔一定天数的0时0分0秒0毫秒
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date setDayAfterBeginTime(Date date, int num) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DATE, num);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	/****
	 * 指定时间的相隔一定月份的　0时0分0秒0毫秒
	 * 
	 * @param date
	 * @param monthNum
	 * @return
	 */
	public static Date setDayAfterMonthTime(Date date, int monthNum) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MONTH, monthNum);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	/**
	 * 两个时间的相差天数
	 * */
	public static int differDays(Date beginData, Date endDate) {
		long resL = endDate.getTime() - beginData.getTime();
		long dayInt = resL / 1000 * 60 * 60 * 24;
		return Integer.parseInt(Long.toString(dayInt));
	}

	/**
	 * beginData+days是否比endDate大
	 * 
	 * @param beginData
	 *            基准时间
	 * @param days
	 *            相差天数
	 * @param endDate
	 *            比较时间
	 * @return 0等于 -1小于 1大于
	 * */
	public static int compareTo(Date beginData, int days, Date endDate) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(beginData);
		ca.add(Calendar.DATE, days);
		Calendar otherCal = Calendar.getInstance();
		otherCal.setTime(endDate);
		return ca.compareTo(otherCal);
	}

	/**
	 * beginData+month是否比endDate大
	 * 
	 * @param beginData
	 *            基准时间
	 * @param month
	 *            相差月份数
	 * @param endDate
	 *            比较时间
	 * @return 0等于 -1小于 1大于
	 * */
	public static int compareMonthTo(Date beginData, int month, Date endDate) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(beginData);
		ca.add(Calendar.MONTH, month);
		Calendar otherCal = Calendar.getInstance();
		otherCal.setTime(endDate);
		return ca.compareTo(otherCal);
	}

	/**
	 * 这个月的最后一天开始
	 * */
	public static Date getLastDayOfMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MONTH, 1);
		ca.set(Calendar.DATE, 1);
		ca.add(Calendar.DATE, -1);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	/**
	 * 通过两个ISO时间字符合并为一个时间，取第一个的日期，取第两个的时间
	 * 
	 * @param dateStr1
	 *            取天数， 如：2011-09-09T16:00:00Z
	 * @param dateStr2
	 *            取时间 如：1970-01-01T07:00:00Z
	 * @throws ProjectException
	 * */
	public static Date getDateByTwoIso(String dateStr1, String dateStr2) {
		try {
			int jIndex = dateStr1.indexOf("+");
			dateStr1 = dateStr1.substring(0, jIndex) + "GMT"
					+ dateStr1.substring(jIndex);
			int j2Index = dateStr2.indexOf("+");
			dateStr2 = dateStr2.substring(0, j2Index) + "GMT"
					+ dateStr2.substring(j2Index);

			Calendar returnDateTime = Calendar.getInstance();
			Date dateD = SimpleDateFormatCase.TyyyyMMddHHmmss.getInstanc()
					.parse(dateStr1);
			Date dateT = SimpleDateFormatCase.TyyyyMMddHHmmss.getInstanc()
					.parse(dateStr2);
			Calendar calDateT = Calendar.getInstance();
			returnDateTime.setTime(dateD);
			calDateT.setTime(dateT);
			returnDateTime.set(Calendar.HOUR, calDateT.get(Calendar.HOUR));
			returnDateTime.set(Calendar.MINUTE, calDateT.get(Calendar.MINUTE));
			returnDateTime.set(Calendar.SECOND, calDateT.get(Calendar.SECOND));
			return returnDateTime.getTime();
		} catch (ParseException e) {
			logger.error("解析时间错误", e);
			return null;
		}
	}

	/**
	 * 通过两个ISO时间字符合并为一个时间，取第一个的日期，取第两个的时间
	 * 
	 * @param dateStr1
	 *            取天数， 如：2011-09-09
	 * @param dateStr2
	 *            取时间 如：T16:00:00
	 * */
	public static Date getDateByStr(String dateStr1, String dateStr2) {
		if (StringUtil.isNull(dateStr1) || StringUtil.isNull(dateStr2)) {
			return null;
		}
		String tempDateStr = String.format("%s %s", dateStr1, dateStr2);
		try {
			Date dateT = SimpleDateFormatCase.TyyyyMMddHHmmssNoZ.getInstanc()
					.parse(tempDateStr);
			return dateT;
		} catch (ParseException e) {
			logger.error("解析时间错误", e);
			return null;
		}
	}

	/***
	 * XMLGregorianCalendar 转为Date
	 * 
	 * @param xmlDate
	 * @return
	 */
	public static Date xmlDateToDate(XMLGregorianCalendar xmlDate) {
		return xmlDate.toGregorianCalendar().getTime();
	}

	/***
	 * Dae　转为 XMLGregorianCalendar
	 * 
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar dateToXmlDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		DatatypeFactory dtf = null;
		try {
			dtf = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
		}
		XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
		dateType.setYear(cal.get(Calendar.YEAR));
		// 由于Calendar.MONTH取值范围为0~11,需要加1
		dateType.setMonth(cal.get(Calendar.MONTH) + 1);
		dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
		dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
		dateType.setMinute(cal.get(Calendar.MINUTE));
		dateType.setSecond(cal.get(Calendar.SECOND));
		return dateType;
	}

	/***
	 * 得到明天的０点时间
	 * 
	 * @param ojbDate
	 * @return
	 */
	public static Date getTomorrow(Object ojbDate) {
		Date ret = objToDate(ojbDate);
		ret = setDayAfterBeginTime(ret, 1);
		return ret;
	}

	/***
	 * 得到明天的０点时间
	 * 
	 * @param ojbDate
	 * @return
	 */
	public static String getTomorrowStr(Object ojbDate) {
		Date ori = getTomorrow(ojbDate);
		return SimpleDateFormatCase.YYYY_MM_DD.getInstanc().format(ori);
	}

	/***
	 * 把秒数转为分钟数
	 * 
	 * @param min
	 *            秒数
	 * @param conType
	 *            转换类型，分为trunc("取整"),round("四舍五入"),ceil("有值进1")
	 * @return 分钟数
	 */
	public static int convertMinuteToSecond(int min, MathConvertType conType) {
		int retValue = 0;
		switch (conType) {
		case trunc:
			retValue = (int) Math.floor((float) min / 60);
			break;
		case round:
			retValue = (int) Math.rint((float) min / 60);
			break;
		case ceil:
			retValue = (int) Math.ceil((float) min / 60);
			break;
		default:
			break;
		}
		return retValue;
	}

}
