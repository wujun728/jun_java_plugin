package com.jun.admin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil {

	/**
	 * 左对齐方式
	 */
	public static final int FORMAT_LEFT = 0;

	/**
	 * 右对齐方式
	 */
	public static final int FORMAT_RIGHT = 1;

	public static String getAllWapPushid(List<?> list) {
		StringBuffer wapPushMessageids = new StringBuffer(128);

		if (list.size() > 0) {
			Object wapPushMessageid = list.get(0);
			wapPushMessageids.append(wapPushMessageid);
		}
		for (int i = 1; i < list.size(); i++) {
			Object wapPushMessageid = list.get(i);
			wapPushMessageids.append(",");
			wapPushMessageids.append(wapPushMessageid);
		}
		return wapPushMessageids.toString();
	}

	/**
	 * 判断一个字符串是否是null或者空格或者空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str != null && str.trim().length() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 如果一个字符串为null，则转化为“”；否则，返回本身
	 * 
	 * @param str
	 * @return
	 */
	public static String nullToStr(String str) {
		return str == null ? "" : str;
	}

	/**
	 * 
	 * @param str
	 *            原字符
	 * @param prefix
	 *            前几位(0,表示不做替换）
	 * @param postfix
	 *            后几位(0,表示不做替换）
	 * @param character
	 *            替换字符(若替换字符为一位，则保持长度不变)
	 * @return 替换后的字符
	 */
	public static String stringSwitch(String str, int prefix, int postfix, String character) {
		if (prefix < 0 || postfix < 0) {
			return str;
		}
		if (prefix == 0 && postfix == 0) {
			return str;
		}
		if (str != null && str.trim().length() > 0) {
			StringBuffer buf = new StringBuffer();
			int argsLength = str.length();
			// 保证被替换的长度大于原字符长度
			if (argsLength > prefix + postfix) {
				if (prefix != 0) {
					String stringPrefix = str.substring(0, prefix);
					buf.append(stringPrefix);
				}
				for (int i = prefix; i < argsLength - postfix; i++) {
					buf.append(character);
				}
				if (postfix != 0) {
					String stringPostfix = str.substring(argsLength - postfix);
					buf.append(stringPostfix);
				}
				return buf.toString();
			} else {
				return str;
			}
		}
		return null;
	}

	private static final SecureRandom sr = new SecureRandom();

	/**
	 * 
	 * 产生任意给定长度的随纯机数字字符串。
	 * 
	 * 
	 * 
	 * @param iLength
	 *            产生随机数的位数
	 * @return 给定位数的随机数
	 */
	public static String getRandomIntNum(int iLength) {
		sr.setSeed(System.currentTimeMillis());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < iLength; i++) {
			sb.append(Math.abs(sr.nextInt(10)));
		}
		return sb.toString();
	}

	public static int getRandomIntNumByMax(int max) {
		sr.setSeed(System.currentTimeMillis());
		return sr.nextInt(max);
	}

	/**
	 * 按字节长度截取字符串
	 * 
	 * @param str
	 *            将要截取的字符串参数
	 * @param toCount
	 *            截取的字节长度
	 * @param more
	 *            字符串末尾补上的字符串
	 * @return 返回截取后的字符串
	 */
	public static String substring(String str, int toCount, String more) {
		int reInt = 0;
		String reStr = "";
		if (str == null)
			return "";
		char[] tempChar = str.toCharArray();
		for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes();
			reInt += b.length;
			reStr += tempChar[kk];
		}
		if (toCount == reInt || (toCount == reInt - 1))
			reStr += more;
		return reStr;
	}

	/**
	 * 将对象转换为字符串，如果对象为null，则返回null
	 * 
	 * @param obj
	 * @return
	 */
	public static String Object2Str(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	/**
	 * filtertoHTML
	 * 
	 * @author Wujun
	 * @param input
	 *            String
	 * @return input String
	 */
	public static String filterToHtml(String input) {
		if (input == null || input.equals("")) {
			return input;
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case ' ':
				filtered.append("&#160;");
				break;
			// case '|':
			// filtered.append("&brvbar;");
			// break;
			case 13:
				filtered.append("<br/>");
				break;
			default:
				filtered.append(c);
			}
		}
		return (filtered.toString());
	}

	/**
	 * 判断一个IP地址是否合法
	 * 
	 * @param ip
	 * @return
	 * @author Wujun
	 */
	public static boolean isIPAddressCorrect(String ip) {
		String[] ips = ip.split("\\.");
		if (ips.length != 4) {
			return false;
		}
		for (int i = 0; i < ips.length; i++) {
			try {
				if (Integer.parseInt(ips[i]) > 255) {
					return false;
				}
			} catch (NumberFormatException ex) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查传入的手机号码 是否 属于 中国移动的手机号码
	 * 
	 * @return true 是中国移动手机号码
	 * @return false 不是中国移动手机号码
	 * @throws Exception
	 */
	public static boolean isCMCCmobile(String destmsisdn) {
		// 中国移动手机号码 段正则表达式
		// 1((3[3-9])|(4[7])|(5[0-3|6-9])|(8[2378]))[0-9]{8}$
		String regEx = "^1((3[3-9])|(4[7])|(5[0-3|6-9])|(8[2378]))[0-9]{8}$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher m = pattern.matcher(destmsisdn); // 拿当前的手机号码与正则表达式去匹配
		boolean CMCCmobile = m.find(); // 匹配成功 的为中国移动手机号码
		return CMCCmobile;
	}

	/**
	 * 将数组中重复的字符串 去除
	 * 
	 * @param destmsisdns
	 * @return
	 */
	public static String removeSameDestmsisdn(String destmsisdns) {
		String tmp = null;
		StringBuffer sb_destmsisdn = new StringBuffer();
		String[] destmsisdnArray = destmsisdns.split(",");
		if (destmsisdnArray == null || destmsisdnArray.length == 0) {
			return null;
		}
		ArrayList<String> resultList = new ArrayList<String>();
		for (int i = 0; i < destmsisdnArray.length; i++) {
			if (resultList.contains(destmsisdnArray[i])) {
				continue;
			} else {
				resultList.add(destmsisdnArray[i]);
			}
		}
		for (int i = 0; i < resultList.size(); i++) {
			sb_destmsisdn.append(resultList.get(i));
			sb_destmsisdn.append(",");
		}
		if (sb_destmsisdn.length() > 0) {
			tmp = sb_destmsisdn.substring(0, sb_destmsisdn.length() - 1);
		} else {
			tmp = sb_destmsisdn.toString();
		}
		return tmp;
	}

	/**
	 * 字符串的转换
	 * 
	 * @param str
	 * @return
	 */
	public static String StringGBK(String str) {
		String s = "";
		try {
			if (str == null) {
				return null;
			}
			s = new String(str.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 输入字符串是否中文
	 * 
	 * @author Wujun
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {

		for (int i = 0; i < str.length(); i++) {
			if (!str.substring(i, i + 1).matches("[\\u4e00-\\u9fa5]+"))
				return false;
		}
		return true;
	}

	/**
	 * 输入字符串是否有英文
	 * 
	 * @author Wujun
	 * @param str
	 * @return
	 */
	public static boolean hasEnglish(String str) {

		for (int i = 0; i < str.length(); i++) {
			if (!str.substring(i, i + 1).matches("[a-zA-Z]+"))
				return false;
		}
		return true;
	}

	/**
	 * 输入字符串是否有数字
	 * 
	 * @author Wujun
	 * @param str
	 * @return
	 */
	public static boolean hasNumeral(String str) {

		for (int i = 0; i < str.length(); i++) {
			if (!str.substring(i, i + 1).matches("\\d+"))
				return false;
		}
		return true;
	}

	/**
	 * 拆分字符串
	 * 
	 * @param src
	 * @param splitter
	 * @return
	 */
	public static List<String> split2List(String src, String splitter) {
		List<String> results = new ArrayList<String>();

		String[] temp = src.split(splitter);
		for (int i = 0; i < temp.length; i++) {
			if (!temp[i].trim().equals("")) {
				results.add(temp[i].trim());
			}
		}

		return results;
	}

	/**
	 * 取得文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 
	 * <P>
	 * 追加空格，将字符串按指定的对齐格式进行齐 建议使用：padString()方法
	 * </P>
	 * 
	 * @param target
	 *            目标对象
	 * @param maxLength
	 *            最大的长度
	 * @param format
	 *            对齐模式
	 * @return
	 */
	@Deprecated
	public static String padSpace(String target, int maxLength, int format) {
		// 如果目标对象为空或者 最大的长度小于当前目标对象的长度，是错误的
		if (StringUtil.isEmpty(target) || maxLength < target.length()) {
			return null;
		}

		StringBuffer where = new StringBuffer();

		int wLength = target.length();
		switch (format) { // 根据对齐模式，选择空格的合适的位置
		case FORMAT_RIGHT: // 右对齐，将空格字符放在字符串左边
			addSpace(where, maxLength - wLength);
			where.append(target);
			break;
		case FORMAT_LEFT: // 左对齐，将空格字符放在字符串右边
			where.append(target);
			addSpace(where, maxLength - wLength);
			break;
		}

		return where.toString();
	}

	/**
	 * 
	 * <P>
	 * 追加零，将字符串按指定的对齐格式进行齐 建议使用：padString()方法
	 * </P>
	 * 
	 * @param target
	 *            目标对象
	 * @param maxLength
	 *            最大的长度
	 * @param format
	 *            对齐模式
	 * @return
	 */
	@Deprecated
	public static String padZero(String target, int maxLength, int format) {
		// 如果目标对象为空或者 最大的长度小于当前目标对象的长度，是错误的
		if (StringUtil.isEmpty(target) || maxLength < target.length()) {
			return null;
		}

		StringBuffer where = new StringBuffer();

		int wLength = target.length();
		switch (format) { // 根据对齐模式，选择空格的合适的位置
		case FORMAT_RIGHT: // 右对齐，将零字符放在字符串左边
			addZero(where, maxLength - wLength);
			where.append(target);
			break;
		case FORMAT_LEFT: // 左对齐，将零字符放在字符串右边
			where.append(target);
			addZero(where, maxLength - wLength);
			break;
		}

		return where.toString();
	}

	/** 追加空格字符。 */
	protected static void addSpace(StringBuffer to, int howMany) {
		for (int i = 0; i < howMany; i++)
			to.append(" ");
	}

	/** 追加零 */
	protected static void addZero(StringBuffer to, int howMany) {
		for (int i = 0; i < howMany; i++)
			to.append("\0");
	}

	/**
	 * 二进制转16进制字符串
	 * 
	 * @param b
	 *            待转化的二进制
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}

		}
		return sb.toString();
	}

	/**
	 * 16进制字符串转二进制
	 * 
	 * @param str
	 *            待转化的16进制字符串
	 * @return
	 */
	public static byte[] hex2byte(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把普通字符串转换成16进制字符串
	 * 
	 * @param str
	 * @return hexstring
	 */
	public static String stringToHexStr(String str) {
		String result = "";
		if (str != null && str.length() > 0) {
			try {
				byte[] b = str.getBytes("UTF-8");
				result = buf2hex(b);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String buf2hex(byte abyte0[]) {
		String s = new String();
		for (int i = 0; i < abyte0.length; i++)
			s = s.concat(b2hex(abyte0[i]));

		s = s.toUpperCase();

		return s;
	}

	public static String b2hex(byte byte0) {
		String s = Integer.toHexString(byte0 & 0xff);
		String s1;
		if (s.length() == 1)
			s1 = "0".concat(s);
		else
			s1 = s;
		return s1.toUpperCase();
	}

	public StringUtil() {
	}

	public static String zeroToStr(String fStr, String lStr) {
		if (fStr.equals("0.00") || fStr.equals("0"))
			fStr = lStr;
		return fStr;
	}

	public static String toString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static String toZero(Object obj) {
		String str = obj == null ? "" : obj.toString();
		if (str.trim().equals(""))
			str = "0.00";
		return str;
	}

	public static String toString(Object obj, String str) {
		return obj == null ? str : obj.toString();
	}

	public static String decodeToUtf(Object obj) {
		String reStr = obj == null ? "" : obj.toString();
		String regEx = "[+]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(reStr);
		reStr = m.replaceAll("��SPSOFT������SPSOFT��");
		try {
			reStr = URLDecoder.decode(reStr, "UTF-8");
			reStr = reStr.replaceAll("��SPSOFT������SPSOFT��", "+");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return reStr;
	}

	public static String decodeToUtf(Object obj, String str) {
		String reStr = obj == null ? str : obj.toString();
		String regEx = "[+]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(reStr);
		reStr = m.replaceAll("��SPSOFT������SPSOFT��");
		try {
			reStr = URLDecoder.decode(reStr, "UTF-8");
			reStr = reStr.replaceAll("��SPSOFT������SPSOFT��", "+");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return reStr;
	}

	public static int toInt(Object obj) {
		return toInt(obj, 0);
	}

	public static int toInt(Object obj, int intStr) {
		String str = obj == null ? "" : obj.toString().trim();
		return "".equals(str) ? intStr : Integer.parseInt(str);
	}

	public static double toDouble(Object obj) {
		return toDouble(obj, 0.0D);
	}

	public static double toDouble(Object obj, double doubleStr) {
		String str = obj == null ? "" : obj.toString().trim();
		return "".equals(str) ? doubleStr : Double.parseDouble(str.replaceAll(",", ""));
	}

	public static String getISOToGBK(String str) {
		String strName = "";
		try {
			if (str != null)
				strName = new String(str.getBytes("ISO8859_1"), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strName;
	}

	public static String getISOToUTF8(String str) {
		String strName = "";
		try {
			if (str != null)
				strName = new String(str.getBytes("ISO8859_1"), "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strName;
	}

	public static String getRandom(int num) {
		return (new StringBuilder()).append(Math.random()).append("").toString().substring(2, num + 2);
	}

	public static String showTrace() {
		StackTraceElement ste[] = (new Throwable()).getStackTrace();
		StringBuffer CallStack = new StringBuffer();
		int i = 1;
		do {
			if (i >= ste.length)
				break;
			CallStack.append((new StringBuilder()).append(ste[i].toString()).append("\n").toString());
			if (i > 4)
				break;
			i++;
		} while (true);
		return CallStack.toString();
	}

	public static String checkTableDefKey(String key[], String value[], String name) {
		String str = "";
		int i = 0;
		do {
			if (i >= key.length)
				break;
			if (name.equals(key[i])) {
				str = value[i];
				break;
			}
			i++;
		} while (true);
		return str;
	}

	public static boolean isChinese2(String str) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	public static String getStrToGbk(String str) {
		String strName = "";
		try {
			if (str != null)
				strName = new String(str.getBytes("UTF-8"), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strName;
	}

	public static String getGBKToUTF8(String str) {
		String strName = "";
		try {
			if (str != null)
				strName = new String(str.getBytes("GBK"), "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strName;
	}

	public static int getRowsPan(String reStr, String reName, List list) {
		int rowsPan = 0;
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if (reStr.equals(map.get(reName).toString()))
				rowsPan++;
		}

		return rowsPan;
	}

	public static int getRowsPan_twoElement(String reStr1, String reName1, String reStr2, String reName2, List list) {
		int rowsPan = 0;
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if (reStr1.equals(map.get(reName1).toString()) && reStr2.equals(map.get(reName2).toString()))
				rowsPan++;
		}

		return rowsPan;
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		label0: for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= '\377') {
				sb.append(c);
				continue;
			}
			byte b[];
			try {
				b = Character.toString(c).getBytes("utf-8");
			} catch (Exception ex) {
				b = new byte[0];
			}
			int j = 0;
			do {
				if (j >= b.length)
					continue label0;
				int k = b[j];
				if (k < 0)
					k += 256;
				sb.append((new StringBuilder()).append("%").append(Integer.toHexString(k).toUpperCase()).toString());
				j++;
			} while (true);
		}

		return sb.toString();
	}

	public static String affluxFilter(Object obj) {
		String str = obj == null ? "" : obj.toString();
		return str.replaceAll("'", "��");
	}

	public static String affluxFilter(Object obj, String defaultValue) {
		String str = obj == null ? defaultValue : obj.toString();
		return str.replaceAll("'", "��");
	}

	public static String StringFilter(String str) throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������\"]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static String toAccountantFormat(String str, int scale) {
		if (str != null) {
			String temp = "#,###,###,###,##0.";
			for (int i = 0; i < scale; i++)
				temp = (new StringBuilder()).append(temp).append("0").toString();

			return (new DecimalFormat(temp)).format(Double.valueOf(str.replaceAll(",", "")));
		} else {
			return "0.00";
		}
	}

	public static void main(String args[]) {
		String reStr = "+";
		System.out.println((new StringBuilder()).append("AAA").append(reStr).toString());
		String regEx = "[+]";
	}
}
