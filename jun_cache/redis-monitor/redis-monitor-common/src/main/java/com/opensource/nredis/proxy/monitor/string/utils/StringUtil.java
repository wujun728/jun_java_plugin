package com.opensource.nredis.proxy.monitor.string.utils;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author liubing
 *
 */
public class StringUtil {

	/**

	 * 在展示的时候，可能需要去掉字符串中的html标签，只展示无格式的字符，并且在字符的长度超一定值时使用......代替超出部分

	 * 

	 * @param input

	 *            : 输入需要展示的字符串

	 * @param length

	 *            : 该字符串的最大长度，如果超过长度时，添加.....

	 * @return

	 */
	public static String splitAndFilterString(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		String str = input.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll(
				"</[a-zA-Z]+[1-9]?>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}

	/**

	 * 若字符串为空（参见isEmpty方法），返回""；<br>

	 * 若不为空，则返回自身

	 * 

	 * @param str

	 * @return

	 */
	public static String killNull(String str) {
		if (isEmpty(str)) {
			return "";
		}
		return str;
	}

	/**

	 * 处理空值

	 * 

	 * @param s

	 *            字符类型

	 * @return String

	 */
	public static String trimNull(String s) {
		if (null == s) {
			return "";
		} else {
			return s.trim();
		}
	}

	/**

	 * 处理空值

	 * 

	 * @param o

	 *            任意对象

	 * @return String

	 */
	public static String trimNull(Object o) {
		if (null == o) {
			return "";
		} else {
			return o.toString();
		}
	}

	/**

	 * 字符串第一个字符大写

	 * 

	 * @param str

	 * @return

	 */
	public static String firstCharToUpperCase(String str) {
		if (!isEmpty(str)) {
			char firstChar = str.charAt(0);
			return String.valueOf(firstChar).toUpperCase(Locale.ENGLISH) + str.substring(1);
		}
		return str;
	}

	/**

	 * 字符串第一个字符转为小写

	 * 

	 * @param str

	 * @return

	 */
	public static String firstCharToLowerCase(String str) {
		if (!isEmpty(str)) {
			char firstChar = str.charAt(0);
			return String.valueOf(firstChar).toLowerCase(Locale.ENGLISH) + str.substring(1);
		}
		return str;
	}

	public static byte[] getBytesByCharset(String str, Charset charset) {
		if (!isEmpty(str)) {
			return str.getBytes(charset);
		}
		return null;
	}

	/**

	 * 获取findStr在src中出现的次数

	 * 

	 * @param src

	 * @param findStr

	 * @return

	 */
	public static int getTimes(String src, String findStr) {
		int times = 0;
		if (isEmpty(src) || isEmpty(findStr)) {
			return times;
		}
		while (src.indexOf(findStr) >= 0) {
			int findStrLength = findStr.length();
			int index = src.indexOf(findStr);
			src = src.substring(index + findStrLength);
			times++;
		}
		return times;
	}

	/**

	 * 获取换行符

	 * 

	 * @return

	 */
	public static String getNewLine() {
		return System.getProperty("line.separator");
	}

	/**

	 * 获取UTF8编码的字节数组

	 * 

	 * @param str

	 * @return

	 */
	public static byte[] getUTF8Bytes(String str) {
		return getBytesByCharset(str, Charset.forName("UTF-8"));
	}

	/**

	 * 获取GBK编码的字节数组

	 * 

	 * @param str

	 * @return

	 */
	public static byte[] getGBKBytes(String str) {
		return getBytesByCharset(str, Charset.forName("GBK"));
	}

	/**

	 * 获取ISO-8859-1编码的字节数组

	 * 

	 * @param str

	 * @return

	 */
	public static byte[] getISO8859Bytes(String str) {
		return getBytesByCharset(str, Charset.forName("ISO-8859-1"));
	}
	
	/**

     * 字符串逆序

     * @param str

     * @return

     */
    public static String reverse(String str) {
        if (str == null)
            return null;
        return new StringBuilder(str).reverse().toString();
    }
    
    /**
	 * 将str将多个分隔符进行切分，
	 * 
	 * 示例：StringTokenizerUtils.split("1,2;3 4"," ,;"); 返回: ["1","2","3","4"]
	 * 
	 * @param str
	 * @param seperators
	 * @return
	 */
	public static Object[] split(String str, String seperators) {
		StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
		List<Object> result = new ArrayList<Object>();

		while (tokenlizer.hasMoreElements()) {
			Object s = tokenlizer.nextElement();
			if (s instanceof Integer) {
				result.add((Integer) s);
			} else if (s instanceof String) {
				result.add((String) s);
			} else if (s instanceof Double) {
				result.add((Double) s);
			} else if (s instanceof Float) {
				result.add((Float) s);
			} else if (s instanceof Long) {
				result.add((Long) s);
			} else if (s instanceof Boolean) {
				result.add((Boolean) s);
			}
		}
		return (Object[]) result.toArray(new Object[result.size()]);
	}

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null) = true</li>
	 * <li>SysUtils.isEmpty("") = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * 
	 * @param value
	 *            待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(String... strings) {
		if (strings == null) {
			return true;
		}
		for (String t : strings) {
			if (isEmpty(t)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNotEmpty(String... strings) {
		for (String t : strings) {
			if (isEmpty(t)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 非空字符串
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value) {
		if (!isEmpty(value)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 校验为int型
	 * 
	 * @param args
	 * @return true/flase
	 */
	public static boolean isInt(String... args) {
		for (String a : args) {
			try {
				Integer.valueOf(a);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验非int型
	 * 
	 * @param args
	 * @return true/flase
	 */
	public static boolean isNotInt(String... args) {
		if (!isInt(args)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 转化为int
	 * 
	 * @param value
	 * @return 0
	 */
	public static int toInt(String value) {
		int num = 0;
		if (StringUtil.isEmpty(value)) {
			return num;
		}
		try {
			num = Integer.parseInt(value);
		} catch (Exception e) {
			num = 0;
		}
		return num;
	}

	/**
	 * 转化为long
	 * 
	 * @param value
	 * @return
	 */
	public static long toLong(String value) {
		long num = 0;
		if (StringUtil.isEmpty(value)) {
			return num;
		}
		try {
			num = Long.valueOf(value);
		} catch (Exception e) {
			num = 0;
		}
		return num;
	}

	/**
	 * 防止sql注入
	 * 
	 * @param sql
	 * @return
	 **/
	public static String filterSql(String sql) {
		// .* 0个以上任意'或;字符
		return sql.replaceAll(".*([';]+|(--)+).*", " ");
	}

	/**
	 * 生成随机数 （激活码）
	 * 
	 * @param length
	 *            生成多少位
	 * @return
	 */
	public static String createRandom(int length) {
		String code = "";
		char[] a = new char[length];
		for (int i = 0; i < a.length; ++i) {
			int rand = getRandom();
			rand = a[i] = (char) (rand + 48);
		}
		code = new String(a).substring(0, length);
		return code;
	}

	private static int getRandom() {
		Set<Integer> filter = new HashSet<Integer>();
		for (int i = 58; i < 97; i++) {
			filter.add(i - 48);
		}
		Random r = new Random();
		int i = r.nextInt(122 - 48);
		if (filter.contains(i))
			i = getRandom();
		return i;
	}

	/**
	 * LIST 去重
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List removeDuplicateWithOrder(List list) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		return newList;
	}

	/**
	 * 保留两位小数,已自带%
	 * 
	 * @param num
	 * @return
	 */
	public static String NumFormat(float num) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(num * 100) + "%";
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 * @return 半角字符串
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}

	/**
	 * String转MAP工具 String格式为：file=task`t=2014-04-03
	 * 08:59:50`rid=6958`lv=23`server_id=1`activity_id=1040513`action=3
	 * 
	 * @param params
	 * @param split
	 *            分隔符
	 * @return
	 */
	public static Map<String, Object> stringToMap(String params, String split) {
		String[] rs = params.split(split);
		Map<String, Object> map = new HashMap<String, Object>(2);
		for (String ss : rs) {
			String[] s1 = ss.split("=");
			if (s1.length < 2) {
				map.put(s1[0], "");
			} else {
				map.put(s1[0], s1[1]);
			}
		}
		return map;
	}

	/**
	 * [{key,value}]转换格式
	 * 
	 * @param str
	 *            [{stren_exp,0},{stren_lv,0}]
	 * @return map
	 */
	public static Map<String, String> toMapValue(String str) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtil.isNotEmpty(str)) {
			String regex = "\\{.*?\\}";
			Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
			Matcher ma = pa.matcher(str);
			String[] param = null;
			while (ma.find()) {
				param = ma.group().replace("{", "").replace("}", "").split(",");
				map.put(param[0], param[1]);
			}
		}
		return map;
	}

	/**
	 * 正则表达式比较
	 * 
	 * @param regex
	 * @param value
	 * @return
	 */
	public static boolean matcher(String regex, String value) {
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(value);
		return matcher.find();
	}

	/**
	 * 去掉换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * a ÷ b保留两位小数
	 * 
	 * @param a
	 * @param b
	 * @return string
	 */
	public static String division(long a, long b) {
		if (b == 0) {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format((double) a / b);
	}

	
	/**
	 * 转list数组
	 * 
	 * @param str
	 *            "1,2,3,4,5"
	 * @param seperators
	 * @return list[1,2,3,4,5]
	 */
	public static List<Integer> toList(String str, String seperators) {
		List<Integer> list = new ArrayList<Integer>();
		String[] strs = str.split(",");
		for (String s : strs) {
			list.add(toInt(s));
		}
		return list;
	}
	
	/**
	 * Object 空值赋值-1
	 * 
	 * @param params
	 * @return object[]
	 */
	public static Object[] replateNullDefault(Object[] params) {
		Object obj = null;
		for (int i = 0; i < params.length; i++) {
			obj = params[i];
			if (obj == null || isEmpty(obj.toString())) {
				params[i] = "-1";
			} else { // 去掉首尾空格
				params[i] = obj.toString().trim();
			}
		}
		return params;
	}
	
	/**
	 * 判断是否是指定进制的数字字符串
	 * @param numberString  目标字符串
	 * @param radix			进制
	 * @return
	 */
	public static boolean isNumber(String numberString,int radix){
		try{
			Integer.parseInt(numberString, radix);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 判断是否是整形数
	 * @param integerString
	 * @return
	 */
	public static boolean isInteger(String integerString){
		if(searchByRegex(integerString, "^-?[0-9]\\d*$").length>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否是浮点数
	 * @param floadString
	 * @return
	 */
	public static boolean isFloat(String floadString){
		if(searchByRegex(floadString, "^-?[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$").length>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 正则表达式查找,匹配的被提取出来做数组
	 * @param source 目标字符串
	 * @param regex 正则表达式
	 * @return  匹配的字符串数组
	 */
	public static String[] searchByRegex(String source,String regex){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		Vector<String> result = new Vector<String>();
		while(matcher.find()){
			result.add(matcher.group());
		}
		return result.toArray(new String[0]);
	}
}
