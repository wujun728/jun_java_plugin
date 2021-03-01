package com.common.time;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * 功能描述：时间计算工具类
 * 
 * @author Administrator
 * @Date Jul 19, 2008
 * @Time 9:14:03 AM
 * @version 1.0
 */
public class Time {

	/**
	 * 时间字段常量，表示“秒”
	 */
	public final static int SECOND = 0;

	/**
	 * 时间字段常量，表示“分”
	 */
	public final static int MINUTE = 1;

	/**
	 * 时间字段常量，表示“时”
	 */
	public final static int HOUR = 2;

	/**
	 * 时间字段常量，表示“天”
	 */
	public final static int DAY = 3;

	/**
	 * 各常量允许的最大值
	 */
	private final int[] maxFields = { 59, 59, 23, Integer.MAX_VALUE - 1 };

	/**
	 * 各常量允许的最小值
	 */
	private final int[] minFields = { 0, 0, 0, Integer.MIN_VALUE };

	/**
	 * 默认的字符串格式时间分隔符
	 */
	private String timeSeparator = ":";

	/**
	 * 时间数据容器
	 */
	private int[] fields = new int[4];

	/**
	 * 无参构造，将各字段置为 0
	 */
	public Time() {
		this(0, 0, 0, 0);
	}

	/**
	 * 使用时、分构造一个时间
	 * 
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 */
	public Time(int hour, int minute) {
		this(0, hour, minute, 0);
	}

	/**
	 * 使用时、分、秒构造一个时间
	 * 
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 * @param second
	 *            秒
	 */
	public Time(int hour, int minute, int second) {
		this(0, hour, minute, second);
	}

	/**
	 * 使用一个字符串构造时间<br/> Time time = new Time("14:22:23");
	 * 
	 * @param time
	 *            字符串格式的时间，默认采用“:”作为分隔符
	 */
	public Time(String time) {
		this(time, null);
	}

	/**
	 * 使用天、时、分、秒构造时间，进行全字符的构造
	 * 
	 * @param day
	 *            天
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 */
	public Time(int day, int hour, int minute, int second) {
		set(DAY, day);
		set(HOUR, hour);
		set(MINUTE, minute);
		set(SECOND, second);
	}

	/**
	 * 使用一个字符串构造时间，指定分隔符<br/> Time time = new Time("14-22-23", "-");
	 * 
	 * @param time
	 *            字符串格式的时间
	 */
	public Time(String time, String timeSeparator) {
		if (timeSeparator != null) {
			setTimeSeparator(timeSeparator);
		}
		String pattern = patternQuote(this.timeSeparator);
		String matcher = new StringBuffer().append("\\d+?").append(pattern)
				.append("\\d+?").append(pattern).append("\\d+?").toString();
		if (!time.matches(matcher)) {
			throw new IllegalArgumentException(time + ", time format error, HH"
					+ this.timeSeparator + "mm" + this.timeSeparator + "ss");
		}
		String[] times = time.split(pattern);
		set(DAY, 0);
		set(HOUR, Integer.parseInt(times[0]));
		set(MINUTE, Integer.parseInt(times[1]));
		set(SECOND, Integer.parseInt(times[2]));
	}

	/**
	 * 设置时间字段的值
	 * 
	 * @param field
	 *            时间字段常量
	 * @param value
	 *            时间字段的值
	 */
	public void set(int field, int value) {
		if (value < minFields[field]) {
			throw new IllegalArgumentException(value
					+ ", time value must be positive.");
		}
		fields[field] = value % (maxFields[field] + 1);
		// 进行进位计算
		int carry = value / (maxFields[field] + 1);
		if (carry > 0) {
			int upFieldValue = get(field + 1);
			set(field + 1, upFieldValue + carry);
		}
	}

	/**
	 * 获得时间字段的值
	 * 
	 * @param field
	 *            时间字段常量
	 * @return 该时间字段的值
	 */
	public int get(int field) {
		if (field < 0 || field > fields.length - 1) {
			throw new IllegalArgumentException(field
					+ ", field value is error.");
		}
		return fields[field];
	}

	/**
	 * 将时间进行“加”运算，即加上一个时间
	 * 
	 * @param time
	 *            需要加的时间
	 * @return 运算后的时间
	 */
	public Time addTime(Time time) {
		Time result = new Time();
		int up = 0; // 进位标志
		for (int i = 0; i < fields.length; i++) {
			int sum = fields[i] + time.fields[i] + up;
			up = sum / (maxFields[i] + 1);
			result.fields[i] = sum % (maxFields[i] + 1);
		}
		return result;
	}

	/**
	 * 将时间进行“减”运算，即减去一个时间
	 * 
	 * @param time
	 *            需要减的时间
	 * @return 运算后的时间
	 */
	public Time subtractTime(Time time) {
		Time result = new Time();
		int down = 0; // 退位标志
		for (int i = 0, k = fields.length - 1; i < k; i++) {
			int difference = fields[i] + down;
			if (difference >= time.fields[i]) {
				difference -= time.fields[i];
				down = 0;
			} else {
				difference += maxFields[i] + 1 - time.fields[i];
				down = -1;
			}
			result.fields[i] = difference;
		}
		result.fields[DAY] = fields[DAY] - time.fields[DAY] + down;
		return result;
	}

	/**
	 * 获得时间字段的分隔符
	 * 
	 * @return
	 */
	public String getTimeSeparator() {
		return timeSeparator;
	}

	/**
	 * 设置时间字段的分隔符（用于字符串格式的时间）
	 * 
	 * @param timeSeparator
	 *            分隔符字符串
	 */
	public void setTimeSeparator(String timeSeparator) {
		this.timeSeparator = timeSeparator;
	}

	/**
	 * 正则表达式引用处理方法，源自 JDK
	 * 
	 * @link java.util.regex.Pattern#quote(String)
	 */
	private String patternQuote(String s) {
		int slashEIndex = s.indexOf("\\E");
		if (slashEIndex == -1)
			return "\\Q" + s + "\\E";

		StringBuilder sb = new StringBuilder(s.length() * 2);
		sb.append("\\Q");
		slashEIndex = 0;
		int current = 0;
		while ((slashEIndex = s.indexOf("\\E", current)) != -1) {
			sb.append(s.substring(current, slashEIndex));
			current = slashEIndex + 2;
			sb.append("\\E\\\\E\\Q");
		}
		sb.append(s.substring(current, s.length()));
		sb.append("\\E");
		return sb.toString();
	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("00");
		return new StringBuffer().append(fields[DAY]).append(", ").append(
				df.format(fields[HOUR])).append(timeSeparator).append(
				df.format(fields[MINUTE])).append(timeSeparator).append(
				df.format(fields[SECOND])).toString();
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + Arrays.hashCode(fields);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Time other = (Time) obj;
		if (!Arrays.equals(fields, other.fields)) {
			return false;
		}
		return true;
	}
}
