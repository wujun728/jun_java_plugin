package com.jun.plugin.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 生成uuid时间
 * 
 * @author 王健
 * 
 */
public class BizUtils {
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String order(Object o) {
		return "" + o.hashCode();
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String date() {
		return sdf.format(new Date());
	}

	public static String getExceptionMessage(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * key:奖品ID value:中奖百分比
	 */
	private Map<String, String> map = new HashMap<String, String>();

	/**
	 * 设置参数
	 * 
	 * @param map
	 */
	public void setParam(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * 抽奖
	 * 
	 * @return
	 */
	public String lottery() {
		Iterator<String> it = this.map.keySet().iterator();
		java.util.Random ran = new java.util.Random();
		List<String> list = new ArrayList<String>();
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			double val = 0;
			if ("0.00".equals(value) || "0".equals(value)) {
				value = "0";
			} else {
				val = Double.parseDouble(value);
				if (val >= 50.00) {
					val *= 70;
				} else if (val >= 40 && val < 50) {
					val *= 50;
				} else if (val >= 30 && val < 40) {
					val *= 30;
				} else if (val >= 20 && val < 30) {
					val *= 10;
				} else if (val >= 10 && val < 20) {
					val *= 4;
				} else if (val >= 1 && val < 10) {
					val *= 2;
				}
			}
			for (double j = 0; j < val; j++) {
				list.add(key);
			}
		}
		// 打乱奖池数据
		int l = ran.nextInt(list.size());
		Collections.shuffle(list);
		return list.get(l);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "0.00");
		map.put("2", "5.00");
		map.put("3泉水", "13.00");
		map.put("5雨伞", "20.00");
		map.put("4打火机", "33.00");
		map.put("6指甲刀", "40.00");
		BizUtils gl = new BizUtils();
		gl.setParam(map);
		for (int i = 1; i <= 2000; i++) {
			System.out.println(gl.lottery());
		}
	}

	/** 金额为分的格式 */
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

	/**
	 * 将分为单位的转换为元并返回金额格式的字符串 （除100）
	 * 
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String changeF2Y(Long amount) throws Exception {
		if (!amount.toString().matches(CURRENCY_FEN_REGEX)) {
			throw new Exception("金额格式有误");
		}
		int flag = 0;
		String amString = amount.toString();
		if (amString.charAt(0) == '-') {
			flag = 1;
			amString = amString.substring(1);
		}
		StringBuffer result = new StringBuffer();
		if (amString.length() == 1) {
			result.append("0.0").append(amString);
		} else if (amString.length() == 2) {
			result.append("0.").append(amString);
		} else {
			String intString = amString.substring(0, amString.length() - 2);
			for (int i = 1; i <= intString.length(); i++) {
				if ((i - 1) % 3 == 0 && i != 1) {
					result.append(",");
				}
				result.append(intString.substring(intString.length() - i,
						intString.length() - i + 1));
			}
			result.reverse().append(".")
					.append(amString.substring(amString.length() - 2));
		}
		if (flag == 1) {
			return "-" + result.toString();
		} else {
			return result.toString();
		}
	}

	/**
	 * 将分为单位的转换为元 （除100）
	 * 
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String changeF2Y(String amount) throws Exception {
		if (StringUtil.isEmpty(amount)) {
			return "0.00";
		}
		if (!amount.matches(CURRENCY_FEN_REGEX)) {
			throw new Exception("金额格式有误");
		}
		return BigDecimal.valueOf(Long.valueOf(amount))
				.divide(new BigDecimal(100)).toString();
	}

	/**
	 * 将元为单位的转换为分 （乘100）
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(Long amount) {
		return BigDecimal.valueOf(amount).multiply(new BigDecimal(100))
				.toString();
	}

	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(String amount) {
		if (StringUtil.isEmpty(amount)) {
			return "0.00";
		}
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
																// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(
					".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(
					".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(
					".", "") + "00");
		}
		return amLong.toString();
	}

//	@Test
	public void main() {
		try {
			System.out.println("结果：" + changeF2Y("-000a00"));
		} catch (Exception e) {
			System.out.println("----------->>>" + e.getMessage());
			// return e.getErrorCode();
		}
		// System.out.println("结果："+changeY2F("1.00000000001E10"));
		System.out.println(BizUtils.changeY2F("1000000000000000"));
		System.out.println(Long.parseLong(BizUtils
				.changeY2F("1000000000000000")));
		System.out.println(Integer.parseInt(BizUtils.changeY2F("10000000")));
		System.out.println(Integer.MIN_VALUE);
		long a = 0;
		System.out.println(a);
	}
	
	private static final String WINDOWS = "WINDOWS";

	/**
	 * 判断当前操作系统的类型
	 * 
	 * @return false means window system ,true means unix like system
	 */
	public static boolean isUnixLikeSystem() {
		String name = System.getProperty("os.name");
		if (name != null) {
			if (name.toUpperCase().indexOf(WINDOWS) == -1) { // it means it's unix like system
				return true;
			}
		}
		return false;
	}
	
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
	public BizUtils() {
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
	public BizUtils(int hour, int minute) {
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
	public BizUtils(int hour, int minute, int second) {
		this(0, hour, minute, second);
	}

	/**
	 * 功能描述：使用一个字符串构造时间<br/> TimeTools time = new TimeTools("14:22:23");
	 * 
	 * @param time
	 *            字符串格式的时间，默认采用“:”作为分隔符
	 */
	public BizUtils(String time) {
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
	public BizUtils(int day, int hour, int minute, int second) {
		set(DAY, day);
		set(HOUR, hour);
		set(MINUTE, minute);
		set(SECOND, second);
	}

	/**
	 * 功能描述：使用一个字符串构造时间，指定分隔符, TimeTools time = new TimeTools("14-22-23", "-");
	 * 
	 * @param time
	 *            字符串格式的时间
	 * @param timeSeparator
	 *            时间段分隔符
	 */
	public BizUtils(String time, String timeSeparator) {
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
	 * 功能描述：设置时间字段的值
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
	 * 功能描述：获得时间字段的值
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
	 * 功能描述：将时间进行“加”运算，即加上一个时间
	 * 
	 * @param time
	 *            需要加的时间
	 * @return 运算后的时间
	 */
	public BizUtils addTime(BizUtils time) {
		BizUtils result = new BizUtils();
		int up = 0; // 进位标志
		for (int i = 0; i < fields.length; i++) {
			int sum = fields[i] + time.fields[i] + up;
			up = sum / (maxFields[i] + 1);
			result.fields[i] = sum % (maxFields[i] + 1);
		}
		return result;
	}

	/**
	 * 功能描述：将时间进行“减”运算，即减去一个时间
	 * 
	 * @param time
	 *            需要减的时间
	 * @return 运算后的时间
	 */
	public BizUtils subtractTime(BizUtils time) {
		BizUtils result = new BizUtils();
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
	 * 功能描述：获得时间字段的分隔符
	 * 
	 * @return
	 */
	public String getTimeSeparator() {
		return timeSeparator;
	}

	/**
	 * 功能描述：设置时间字段的分隔符（用于字符串格式的时间）
	 * 
	 * @param timeSeparator
	 *            分隔符字符串
	 */
	public void setTimeSeparator(String timeSeparator) {
		this.timeSeparator = timeSeparator;
	}

	/**
	 * 功能描述：正则表达式引用处理方法，源自 JDK
	 * 
	 * @link java.util.regex.Pattern#quote(String)
	 * @param s
	 * @return
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
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final BizUtils other = (BizUtils) obj;
		if (!Arrays.equals(fields, other.fields)) {
			return false;
		}
		return true;
	}

 
	
}
