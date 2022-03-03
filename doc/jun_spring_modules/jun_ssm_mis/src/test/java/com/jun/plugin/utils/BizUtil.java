package com.jun.plugin.utils;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.rowset.serial.SerialClob;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.jun.mis.entity.Department;

/**
 * 生成uuid时间
 * 
 * @author 王健
 * 
 */
public class BizUtil {
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
		BizUtil gl = new BizUtil();
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

	@Test
	public void main() {
		try {
			System.out.println("结果：" + changeF2Y("-000a00"));
		} catch (Exception e) {
			System.out.println("----------->>>" + e.getMessage());
			// return e.getErrorCode();
		}
		// System.out.println("结果："+changeY2F("1.00000000001E10"));
		System.out.println(BizUtil.changeY2F("1000000000000000"));
		System.out.println(Long.parseLong(BizUtil
				.changeY2F("1000000000000000")));
		System.out.println(Integer.parseInt(BizUtil.changeY2F("10000000")));
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
	public BizUtil() {
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
	public BizUtil(int hour, int minute) {
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
	public BizUtil(int hour, int minute, int second) {
		this(0, hour, minute, second);
	}

	/**
	 * 功能描述：使用一个字符串构造时间<br/> TimeTools time = new TimeTools("14:22:23");
	 * 
	 * @param time
	 *            字符串格式的时间，默认采用“:”作为分隔符
	 */
	public BizUtil(String time) {
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
	public BizUtil(int day, int hour, int minute, int second) {
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
	public BizUtil(String time, String timeSeparator) {
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
	public BizUtil addTime(BizUtil time) {
		BizUtil result = new BizUtil();
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
	public BizUtil subtractTime(BizUtil time) {
		BizUtil result = new BizUtil();
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
		final BizUtil other = (BizUtil) obj;
		if (!Arrays.equals(fields, other.fields)) {
			return false;
		}
		return true;
	}

	/**********************************************************************************************/
	/**********************************************************************************************/

	/**
	 * 返回部门树形字符串
	 * @param topDepartmentCollection  顶级部门集合
	 * @param prefix                   输出部门名称的前缀
	 * @parem list                     部门输出集合
	 * @return
	 */
	private static void getDepartmentTreesString(Collection<Department> topDepartmentCollection,String prefix,List<Department> list){
		for (Department department : topDepartmentCollection) {
			Department copy = new Department();
			copy.setId(department.getId());
			copy.setDeptName(prefix + "┝" + department.getDeptName());
			list.add(copy);
			getDepartmentTreesString(department.getChildren(),prefix + "　",list);
		}
	}
	
	/**
	 * 根据顶级部门获取所有部门(部门名称经过修改)
	 */
	public static List<Department> getAllDepartmentList(List<Department> topDepartmentList){
		List<Department> list = new ArrayList<Department>();
		getDepartmentTreesString(topDepartmentList,"",list);
		return list;
	}
	
	/**
	 * 给定集合中移除指定部门及其子部门
	 */
	public static void removeSomeDepartmentsAndChildren(List<Department> departmentList,Department department){
		departmentList.remove(department);
		for (Department dept : department.getChildren()) {
			removeSomeDepartmentsAndChildren(departmentList,dept);
		}
	}
	
	/**
	 * 给定集合中移除指定部门的子部门
	 */
	public static void removeSomeDepartmentsAndChildren(List<Department> departmentList,Department department,boolean isfirst){
		if(!isfirst){
			departmentList.remove(department);
		}
		for (Department dept : department.getChildren()) {
			isfirst = false;
			removeSomeDepartmentsAndChildren(departmentList,dept);
		}
	}
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/

	private static final Log log = LogFactory.getLog(BizUtil.class);


	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends
	 * GenricManager&lt;Book&gt;
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends
	 * GenricManager&lt;Book&gt;
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz, int index) {

		// Type getGenericSuperclass():返回本类的父类,包含泛型参数信息
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			log.warn(clazz.getSimpleName()
					+ "'s superclass not ParameterizedType");
			return Object.class;
		}

		// 返回表示此类型实际类型参数的 Type 对象的数组，获得超类的泛型参数的实际类型。。
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
					+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			log.warn(clazz.getSimpleName()
					+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * for batch delete
	 * 
	 * @param ids
	 * @return
	 */
	public static String createBlock(Object[] ids) {
		if (ids == null || ids.length == 0)
			return "('')";
		String blockStr = "";
		for (int i = 0; i < ids.length - 1; i++) {
			blockStr += "'" + ids[i] + "',";
		}
		blockStr += "'" + ids[ids.length - 1] + "'";
		return blockStr;
	}
	//****************************************************************************************
	//****************************************************************************************
	//****************************************************************************************

	protected static int count = 0;

	/**
	 * 生成24位UUID
	 * 
	 * @return UUID 24bit string
	 */
	public static synchronized String getUUID() {
		count++;
		long time = System.currentTimeMillis();

		String uuid = "G" + Long.toHexString(time) + Integer.toHexString(count)
				+ Long.toHexString(Double.doubleToLongBits(Math.random()));

		uuid = uuid.substring(0, 24).toUpperCase();

		return uuid;
	}

	/**
	 * String处理
	 * 
	 * @param in
	 *            指定字符串
	 * @return 如果指定字符串为null,返回"",否则去空格返回
	 */
	public static String checkNull(String in) {
		String out = null;
		out = in;
		if (out == null || (out.trim()).equals("null")) {
			return "";
		} else {
			return out.trim();
		}
	}

	/**
	 * double类型取小数点后面几位
	 * 
	 * @param val
	 *            指定double型数字
	 * @param precision
	 *            取前几位
	 * @return 转换后的double数字
	 */
	public static Double roundDouble(double val, int precision) {
		Double ret = null;
		try {
			double factor = Math.pow(10, precision);
			ret = Math.floor(val * factor + 0.5) / factor;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 检测传入字符串是否为null或者空字符串
	 */
	public static boolean isNULLOrEmptyOfProperty(String source) {
		return null == source || source.length() == 0 || source.trim().equals("");
	}

	/**
	 * 将Clob对象转换成字符串
	 */
	public static String clobToString(Clob clob) {
		if (null == clob) {
			return "";
		}
		StringBuffer sb = new StringBuffer(65535);  //64K
		Reader clobStream = null;
		try {
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];//每次读取60K
			int i = 0;
			while ((i = clobStream.read(b)) != -1) {
				sb.append(b,0,i);
			}
		} catch (Exception ex) {
			sb = new StringBuffer();
		} finally {
			try {
				if (null != clobStream){
					clobStream.close();
				}
			} catch (Exception e) {
				throw new RuntimeException("Reader关闭异常");
			}
		}
		if (null == sb) {
			return "";
		} else {
			return sb.toString();
		}
	}
	/**
	 * 
	 * 将String转成Clob
	 * 
	 * @param str
	 * @return clob对象，如果出现错误，返回 null
	 * 
	 */
	public static Clob stringToClob(String str) {
		if (null == str) {
			return null;
		} else {
			try {
				Clob c = new SerialClob(str.toCharArray());
				return c;
			} catch (Exception e) {
				return null;
			}
		}
	}
	//****************************************************************************************
	
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	
}
