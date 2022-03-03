package com.jun.plugin.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.lang.StringUtils;

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

	/**
	 * 如果一个字符串为null，则转化为“”；否则，返回本身
	 * 
	 * @param str
	 * @return
	 */

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
	 * @author chenjiang
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
	 * @author liufengyu
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
	 * @author zhoulei
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
	 * @author zhoulei
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
	 * @author zhoulei
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

	public static void main1141(String args[]) {
		String reStr = "+";
		System.out.println((new StringBuilder()).append("AAA").append(reStr).toString());
		String regEx = "[+]";
	}
	
	//***********************************************************************************************
	//***********************************************************************************************
	public static final String UTF8 = "UTF-8";

    public static final String UTF16 = "UTF-16";

    public static final String GB2312 = "GB2312";

    public static final String GBK = "GBK";

    public static final String ISO8859_1 = "ISO-8859-1";

    public static final String ISO8859_2 = "ISO-8859-2";
    
    private static final String QUOTE = "&quot;";
    
    private static final String AMP = "&amp;";
    
    private static final String LT = "&lt;";
    
    private static final String GT = "&gt;";
    
    private static final int FILLCHAR = '=';
    
    private static final String CVT = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    
    private static final String NUMBER_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    private static Random randGen = new Random();
    
    /**
     * 编码转换
     * @param str
     * @param oldCharset 转换前编码 
     * @param newCharset 转换后编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String swichCharset(String str, String oldCharset,String newCharset) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return new String(str.getBytes(oldCharset), newCharset);
    }
    
/*	public static boolean isEmpty(String str) {
		if (str != null && str.trim().length() > 0) {
			return false;
		}
		return true;
	}*/
	
    /**
     * 字符串数组转换成List集合
     * @param array
     * @return
     */
    public static List arrayToList(String[] array) {
        List list = new ArrayList();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }
    
    /**
     * 字符串替换
     * @param source    源字符串
     * @param oldString 待替换字符串
     * @param newString 替换字符串
     * @return
     */
    public static final String replace(String source, String oldString,String newString) {
        if (source == null) {
            return null;
        }
        int i = 0;
        if ((i = source.indexOf(oldString, i)) >= 0) {
            char[] line2 = source.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = source.indexOf(oldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return source;
    }
    
    /**
     * 字符串替换(忽略大小写)
     * @param source    源字符串
     * @param oldString 待替换字符串
     * @param newString 替换字符串
     * @return
     */
    public static final String replaceIgnoreCase(String source, String oldString,
            String newString) {
        if (source == null) {
            return null;
        }
        String lcsource = source.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcsource.indexOf(lcOldString, i)) >= 0) {
            char[] source2 = source.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(source2.length);
            buf.append(source2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = lcsource.indexOf(lcOldString, i)) > 0) {
                buf.append(source2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(source2, j, source2.length - j);
            return buf.toString();
        }
        return source;
    }
    /**
     * 字符串替换(忽略大小写)并且返回被替换元素的个数
     * @param source    源字符串
     * @param oldString 待替换字符串
     * @param newString 替换字符串
     * @return count   被替换元素个数
     */
    public static final String replaceIgnoreCase(String source, String oldString,String newString, int[] count) {
    	if (source == null) {
            return null;
        }
        String lcsource = source.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        int counter = 0;
        if ((i = lcsource.indexOf(lcOldString, i)) < 0) {
        	return source;
        }
        char[] source2 = source.toCharArray();
        char[] newString2 = newString.toCharArray();
        int oLength = oldString.length();
        StringBuffer buf = new StringBuffer(source2.length);
        buf.append(source2, 0, i).append(newString2);
        i += oLength;
        int j = i;
        while ((i = lcsource.indexOf(lcOldString, i)) > 0) {
            counter++;
            buf.append(source2, j, i - j).append(newString2);
            i += oLength;
            j = i;
        }
        buf.append(source2, j, source2.length - j);
        count[0] = counter+1;
        return buf.toString();
    }
    
    /**
     * 对传入的字符串进行Base64编码
     * @param data
     * @return
     */
    public static String encodeBase64(String data) {
        return encodeBase64(data.getBytes());
    }
    
    private static String encodeBase64(byte[] data) {
        int c;
        int len = data.length;
        StringBuffer ret = new StringBuffer(((len / 3) + 1) * 4);
        for (int i = 0; i < len; ++i) {
            c = (data[i] >> 2) & 0x3f;
            ret.append(CVT.charAt(c));
            c = (data[i] << 4) & 0x3f;
            if (++i < len){
                c |= (data[i] >> 4) & 0x0f;
            }
            ret.append(CVT.charAt(c));
            if (i < len) {
                c = (data[i] << 2) & 0x3f;
                if (++i < len){
                    c |= (data[i] >> 6) & 0x03;
                }
                ret.append(CVT.charAt(c));
            } else {
                ++i;
                ret.append((char)FILLCHAR);
            }
            if (i < len) {
                c = data[i] & 0x3f;
                ret.append(CVT.charAt(c));
            } else {
                ret.append((char)FILLCHAR);
            }
        }
        return ret.toString();
    }
    
    /**
     * 生成指定长度的随机字符串
     * length如果小于1，则返回null
     * @param length 返回字符串长度
     * @return String 指定长度的随机字符串
     */
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = NUMBER_LETTERS.toCharArray()[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }
    /**
     * 对HTML字符串<>转义
     * @param in HTML字符串
     * @return
     */
    public static final String escapeHTMLTags(String in) {
        if (in == null) {
            return null;
        }
        char ch;
        int i = 0;
        int last = 0;
        char[] input = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) (len * 1.3));
        for (; i < len; i++) {
            ch = input[i];
            if (ch > '>') {
                continue;
            } else if (ch == '<') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(LT.toCharArray());
            } else if (ch == '>') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(GT.toCharArray());
            }
        }
        if (last == 0) {
            return in;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();
    }
    
    /**
     * 对XML转义< > & "" 
     * @param string
     * @return
     */
    public static final String escapeXML(String string) {
        if (string == null) {
            return null;
        }
        char ch;
        int i = 0;
        int last = 0;
        char[] input = string.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) (len * 1.3));
        for (; i < len; i++) {
            ch = input[i];
            if (ch > '>') {
                continue;
            } else if (ch == '<') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(LT.toCharArray());
            } else if (ch == '&') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(AMP.toCharArray());
            } else if (ch == '"') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(QUOTE.toCharArray());
            }
        }
        if (last == 0) {
            return string;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();
    }
    
    /**
     * 数字0-9转换成汉字显示
     * 如果数字大于9，则返回空字符串
     * @param i 数字0-9
     * @return
     */
    public static String getCapitalization(int i) {
		String str = "";
		if (i == 1) {
			str = "一";
		} else if (i == 2) {
			str = "二";
		} else if (i == 3) {
			str = "三";
		} else if (i == 4) {
			str = "四";
		} else if (i == 5) {
			str = "五";
		} else if (i == 6) {
			str = "六";
		} else if (i == 7) {
			str = "七";
		} else if (i == 8) {
			str = "八";
		} else if (i == 9) {
			str = "九";
		} else {
			str = "零";
		}
		return str;
	}
    
    /**
     * 按指定的分隔符分割字符串
     * @param source 源字符串
     * @param spliter 分隔符
     * @return 字符串数组
     */
    public static String[] split(String source, String spliter)
    {
    	StringTokenizer st = new StringTokenizer(source,spliter);
    	String ret[] = new String[st.countTokens()];
    	
    	int i=0;
    	while(st.hasMoreTokens()) {
    	   ret[i++]=st.nextElement()+"";
    	}
    	return ret;
    }
    
    
    /**
     * org.apache.commons.lang.StringUtils使用说明
     * public static void TestStr(){
	//null 和 ""操作~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//判断是否Null 或者 ""
	//System.out.println(StringUtils.isEmpty(null));
	//System.out.println(StringUtils.isNotEmpty(null));
	//判断是否null 或者 "" 去空格~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//System.out.println(StringUtils.isBlank("  "));
	//System.out.println(StringUtils.isNotBlank(null));
	//去空格.Null返回null~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//System.out.println(StringUtils.trim(null));
	//去空格，将Null和"" 转换为Null
	//System.out.println(StringUtils.trimToNull(""));
	//去空格，将NULL 和 "" 转换为""
	//System.out.println(StringUtils.trimToEmpty(null));
	//可能是对特殊空格符号去除？？
	//System.out.println(StringUtils.strip("大家好  啊  \t"));
	//同上，将""和null转换为Null
	//System.out.println(StringUtils.stripToNull(" \t"));
	//同上，将""和null转换为""
	//System.out.println(StringUtils.stripToEmpty(null));
	//将""或者Null 转换为 ""
	//System.out.println(StringUtils.defaultString(null));
	//仅当字符串为Null时 转换为指定的字符串(二参数)
	//System.out.println(StringUtils.defaultString("", "df"));
	//当字符串为null或者""时，转换为指定的字符串(二参数)
	//System.out.println(StringUtils.defaultIfEmpty(null, "sos"));
	//去空格.去字符~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//如果第二个参数为null去空格(否则去掉字符串2边一样的字符，到不一样为止)
	//System.out.println(StringUtils.strip("fsfsdf", "f"));
	//如果第二个参数为null只去前面空格(否则去掉字符串前面一样的字符，到不一样为止)
	//System.out.println(StringUtils.stripStart("ddsuuu ", "d"));
	//如果第二个参数为null只去后面空格，(否则去掉字符串后面一样的字符，到不一样为止)
	//System.out.println(StringUtils.stripEnd("dabads", "das"));
	//对数组没个字符串进行去空格。
	//ArrayToList(StringUtils.stripAll(new String[]{" 中华 ", "民 国 ", "共和 "}));
	//如果第二个参数为null.对数组每个字符串进行去空格。(否则去掉数组每个元素开始和结尾一样的字符)
	//ArrayToList(StringUtils.stripAll(new String[]{" 中华 ", "民 国", "国共和国"}, "国"));
	//查找,判断~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//判断2个字符串是否相等相等,Null也相等
	//System.out.println(StringUtils.equals(null, null));
	//不区分大小写比较
	//System.out.println(StringUtils.equalsIgnoreCase("abc", "ABc"));
	//查找，不知道怎么弄这么多查找，很多不知道区别在哪？费劲~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//普通查找字符，如果一参数为null或者""返回-1
	//System.out.println(StringUtils.indexOf(null, "a"));
	//从指定位置(三参数)开始查找，本例从第2个字符开始查找k字符
	//System.out.println(StringUtils.indexOf("akfekcd中华", "k", 2));
	//未发现不同之处
	//System.out.println(StringUtils.ordinalIndexOf("akfekcd中华", "k", 2));
	//查找,不区分大小写
	//System.out.println(StringUtils.indexOfIgnoreCase("adfs", "D"));
	//从指定位置(三参数)开始查找,不区分大小写
	//System.out.println(StringUtils.indexOfIgnoreCase("adfs", "a", 3));
	//从后往前查找
	//System.out.println(StringUtils.lastIndexOf("adfas", "a"));
	//未理解,此结果为2
	//System.out.println(StringUtils.lastIndexOf("d饿abasdafs我", "a", 3));
	//未解,此结果为-1
	//System.out.println(StringUtils.lastOrdinalIndexOf("yksdfdht", "f", 2));
	//从后往前查，不区分大小写
	//System.out.println(StringUtils.lastIndexOfIgnoreCase("sdffet", "E"));
	//未解,此结果为1
	//System.out.println(StringUtils.lastIndexOfIgnoreCase("efefrfs看", "F" , 2));
	//检查是否查到，返回boolean,null返回假
	//System.out.println(StringUtils.contains("sdf", "dg"));
	//检查是否查到，返回boolean,null返回假,不区分大小写
	//System.out.println(StringUtils.containsIgnoreCase("sdf", "D"));
	//检查是否有含有空格,返回boolean
	//System.out.println(StringUtils.containsWhitespace(" d"));
	//查询字符串跟数组任一元素相同的第一次相同的位置
	//System.out.println(StringUtils.indexOfAny("absfekf", new String[]{"f", "b"}));
	//查询字符串中指定字符串(参数二)出现的次数
	//System.out.println(StringUtils.indexOfAny("afefes", "e"));
	//查找字符串中是否有字符数组中相同的字符，返回boolean
	//System.out.println(StringUtils.containsAny("asfsd", new char[]{'k', 'e', 's'}));
	//未理解与lastIndexOf不同之处。是否查到，返回boolean
	//System.out.println(StringUtils.containsAny("啡f咖啡", "咖"));
	//未解
	//System.out.println(StringUtils.indexOfAnyBut("seefaff", "af"));
	//判断字符串中所有字符，都是出自参数二中。
	//System.out.println(StringUtils.containsOnly("中华华", "华"));
	//判断字符串中所有字符，都是出自参数二的数组中。
	//System.out.println(StringUtils.containsOnly("中华中", new char[]{'中', '华'}));
	//判断字符串中所有字符，都不在参数二中。
	//System.out.println(StringUtils.containsNone("中华华", "国"));
	//判断字符串中所有字符，都不在参数二的数组中。
	//System.out.println(StringUtils.containsNone("中华中", new char[]{'中', '达人'}));
	//从后往前查找字符串中与字符数组中相同的元素第一次出现的位置。本例为4
	//System.out.println(StringUtils.lastIndexOfAny("中国人民共和国", new String[]{"国人", "共和"}));
	//未发现与indexOfAny不同之处  查询字符串中指定字符串(参数二)出现的次数
	//System.out.println(StringUtils.countMatches("中国人民共和中国", "中国"));
	//检查是否CharSequence的只包含Unicode的字母。空将返回false。一个空的CharSequence（长（）= 0）将返回true
	//System.out.println(StringUtils.isAlpha("这是干什么的2"));
	//检查是否只包含Unicode的CharSequence的字母和空格（''）。空将返回一个空的CharSequence假（长（）= 0）将返回true。
	//System.out.println(StringUtils.isAlphaSpace("NBA直播 "));
	//检查是否只包含Unicode的CharSequence的字母或数字。空将返回false。一个空的CharSequence（长（）= 0）将返回true。
	//System.out.println(StringUtils.isAlphanumeric("NBA直播"));
	//如果检查的Unicode CharSequence的只包含字母，数字或空格（''）。空将返回false。一个空的CharSequence（长（）= 0）将返回true。
	//System.out.println(StringUtils.isAlphanumericSpace("NBA直播"));
	//检查是否只包含ASCII可CharSequence的字符。空将返回false。一个空的CharSequence（长（）= 0）将返回true。
	//System.out.println(StringUtils.isAsciiPrintable("NBA直播"));
	//检查是否只包含数值。
	//System.out.println(StringUtils.isNumeric("NBA直播"));
	//检查是否只包含数值或者空格
	//System.out.println(StringUtils.isNumericSpace("33 545"));
	//检查是否只是空格或""。
	//System.out.println(StringUtils.isWhitespace(" "));
	//检查是否全是英文小写。
	//System.out.println(StringUtils.isAllLowerCase("kjk33"));
	//检查是否全是英文大写。
	//System.out.println(StringUtils.isAllUpperCase("KJKJ"));
	//交集操作~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//去掉参数2字符串中在参数一中开头部分共有的部分，结果为:人民共和加油
	//System.out.println(StringUtils.difference("中国加油", "中国人民共和加油"));
	//统计2个字符串开始部分共有的字符个数
	//System.out.println(StringUtils.indexOfDifference("ww.taobao", "www.taobao.com"));
	//统计数组中各个元素的字符串开始都一样的字符个数
	//System.out.println(StringUtils.indexOfDifference(new String[] {"中国加油", "中国共和", "中国人民"}));
	//取数组每个元素共同的部分字符串
	//System.out.println(StringUtils.getCommonPrefix(new String[] {"中国加油", "中国共和", "中国人民"}));
	//统计参数一中每个字符与参数二中每个字符不同部分的字符个数
	//System.out.println(StringUtils.getLevenshteinDistance("中国共和发国人民", "共和国"));
	//判断开始部分是否与二参数相同
	//System.out.println(StringUtils.startsWith("中国共和国人民", "中国"));
	//判断开始部分是否与二参数相同。不区分大小写
	//System.out.println(StringUtils.startsWithIgnoreCase("中国共和国人民", "中国"));
	//判断字符串开始部分是否与数组中的某一元素相同
	//System.out.println(StringUtils.startsWithAny("abef", new String[]{"ge", "af", "ab"}));
	//判断结尾是否相同
	//System.out.println(StringUtils.endsWith("abcdef", "def"));
	//判断结尾是否相同，不区分大小写
	//System.out.println(StringUtils.endsWithIgnoreCase("abcdef", "Def"));
	//字符串截取~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//截取指定位置的字符，null返回null.""返回""
	//System.out.println(StringUtils.substring("国民党", 2));
	//截取指定区间的字符
	//System.out.println(StringUtils.substring("中国人民共和国", 2, 4));
	//从左截取指定长度的字符串
	//System.out.println(StringUtils.left("说点什么好呢", 3));
	//从右截取指定长度的字符串
	//System.out.println(StringUtils.right("说点什么好呢", 3));
	//从第几个开始截取，三参数表示截取的长度
	//System.out.println(StringUtils.mid("说点什么好呢", 3, 2));
	//截取到等于第二个参数的字符串为止
	//System.out.println(StringUtils.substringBefore("说点什么好呢", "好"));
	//从左往右查到相等的字符开始，保留后边的，不包含等于的字符。本例：什么好呢
	//System.out.println(StringUtils.substringAfter("说点什么好呢", "点"));
	//这个也是截取到相等的字符，但是是从右往左.本例结果：说点什么好
	//System.out.println(StringUtils.substringBeforeLast("说点什么好点呢", "点"));
	//这个截取同上是从右往左。但是保留右边的字符
	//System.out.println(StringUtils.substringAfterLast("说点什么好点呢？", "点"));
	//截取查找到第一次的位置，和第二次的位置中间的字符。如果没找到第二个返回null。本例结果:2010世界杯在
	//System.out.println(StringUtils.substringBetween("南非2010世界杯在南非，在南非", "南非"));
	//返回参数二和参数三中间的字符串，返回数组形式
	//ArrayToList(StringUtils.substringsBetween("[a][b][c]", "[", "]"));
	//分割~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//用空格分割成数组，null为null
	//ArrayToList(StringUtils.split("中华 人民  共和"));
	//以指定字符分割成数组
	//ArrayToList(StringUtils.split("中华 ,人民,共和", ","));
	//以指定字符分割成数组，第三个参数表示分隔成数组的长度，如果为0全体分割
	//ArrayToList(StringUtils.split("中华 ：人民：共和", "：", 2));
	//未发现不同的地方,指定字符分割成数组
	//ArrayToList(StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-"));
	//未发现不同的地方,以指定字符分割成数组，第三个参数表示分隔成数组的长度
	//ArrayToList(StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2));
	//分割，但" "不会被忽略算一个元素,二参数为null默认为空格分隔
	//ArrayToList(StringUtils.splitByWholeSeparatorPreserveAllTokens(" ab   de fg ", null));
	//同上，分割," "不会被忽略算一个元素。第三个参数代表分割的数组长度。
	//ArrayToList(StringUtils.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null, 3));
	//未发现不同地方,分割
	//ArrayToList(StringUtils.splitPreserveAllTokens(" ab   de fg "));
	//未发现不同地方,指定字符分割成数组
	//ArrayToList(StringUtils.splitPreserveAllTokens(" ab   de fg ", null));
	//未发现不同地方,以指定字符分割成数组，第三个参数表示分隔成数组的长度
	//ArrayToList(StringUtils.splitPreserveAllTokens(" ab   de fg ", null, 2));
	//以不同类型进行分隔
	//ArrayToList(StringUtils.splitByCharacterType("AEkjKr i39:。中文"));
	//未解
	//ArrayToList(StringUtils.splitByCharacterTypeCamelCase("ASFSRules234"));
	//拼接~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//将数组转换为字符串形式
	//System.out.println(StringUtils.concat(getArrayData()));
	//拼接时用参数一得字符相连接.注意null也用连接符连接了
	//System.out.println(StringUtils.concatWith(",", getArrayData()));
	//也是拼接。未发现区别
	//System.out.println(StringUtils.join(getArrayData()));
	//用连接符拼接，为发现区别
	//System.out.println(StringUtils.join(getArrayData(), ":"));
	//拼接指定数组下标的开始(三参数)和结束(四参数,不包含)的中间这些元素，用连接符连接
	//System.out.println(StringUtils.join(getArrayData(), ":", 1, 3));
	//用于集合连接字符串.用于集合
	//System.out.println(StringUtils.join(getListData(), ":"));
	//移除，删除~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//删除所有空格符
	//System.out.println(StringUtils.deleteWhitespace(" s 中 你 4j"));
	//移除开始部分的相同的字符
	//System.out.println(StringUtils.removeStart("www.baidu.com", "www."));
	//移除开始部分的相同的字符,不区分大小写
	//System.out.println(StringUtils.removeStartIgnoreCase("www.baidu.com", "WWW"));
	//移除后面相同的部分
	//System.out.println(StringUtils.removeEnd("www.baidu.com", ".com"));
	//移除后面相同的部分，不区分大小写
	//System.out.println(StringUtils.removeEndIgnoreCase("www.baidu.com", ".COM"));
	//移除所有相同的部分
	//System.out.println(StringUtils.remove("www.baidu.com/baidu", "bai"));
	//移除结尾字符为"\n", "\r", 或者 "\r\n".
	//System.out.println(StringUtils.chomp("abcrabc\r"));
	//也是移除，未解。去结尾相同字符
	//System.out.println(StringUtils.chomp("baidu.com", "com"));
	//去掉末尾最后一个字符.如果是"\n", "\r", 或者 "\r\n"也去除
	//System.out.println(StringUtils.chop("wwe.baidu"));
	//替换~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//替换指定的字符，只替换第一次出现的
	//System.out.println(StringUtils.replaceOnce("www.baidu.com/baidu", "baidu", "hao123"));
	//替换所有出现过的字符
	//System.out.println(StringUtils.replace("www.baidu.com/baidu", "baidu", "hao123"));
	//也是替换，最后一个参数表示替换几个
	//System.out.println(StringUtils.replace("www.baidu.com/baidu", "baidu", "hao123", 1));
	//这个有意识，二三参数对应的数组，查找二参数数组一样的值，替换三参数对应数组的值。本例:baidu替换为taobao。com替换为net
	//System.out.println(StringUtils.replaceEach("www.baidu.com/baidu", new String[]{"baidu", "com"}, new String[]{"taobao", "net"}));
	//同上，未发现不同
	//System.out.println(StringUtils.replaceEachRepeatedly("www.baidu.com/baidu", new String[]{"baidu", "com"}, new String[]{"taobao", "net"}));
	//这个更好，不是数组对应，是字符串参数二和参数三对应替换.(二三参数不对应的话，自己看后果)
	//System.out.println(StringUtils.replaceChars("www.baidu.com", "bdm", "qo"));
	//替换指定开始(参数三)和结束(参数四)中间的所有字符
	//System.out.println(StringUtils.overlay("www.baidu.com", "hao123", 4, 9));
	//添加，增加~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//复制参数一的字符串，参数二为复制的次数
	//System.out.println(StringUtils.repeat("ba", 3));
	//复制参数一的字符串，参数三为复制的次数。参数二为复制字符串中间的连接字符串
	//System.out.println(StringUtils.repeat("ab", "ou", 3));
	//如何字符串长度小于参数二的值，末尾加空格补全。(小于字符串长度不处理返回)
	//System.out.println(StringUtils.rightPad("海川", 4));
	//字符串长度小于二参数，末尾用参数三补上，多于的截取(截取补上的字符串)
	//System.out.println(StringUtils.rightPad("海川", 4, "河流啊"));
	//同上在前面补全空格
	//System.out.println(StringUtils.leftPad("海川", 4));
	//字符串长度小于二参数，前面用参数三补上，多于的截取(截取补上的字符串)
	//System.out.println(StringUtils.leftPad("海川", 4, "大家好"));
	//字符串长度小于二参数。在两侧用空格平均补全（测试后面补空格优先）
	//System.out.println(StringUtils.center("海川", 3));
	//字符串长度小于二参数。在两侧用三参数的字符串平均补全（测试后面补空格优先）
	//System.out.println(StringUtils.center("海川", 5, "流"));
	//只显示指定数量(二参数)的字符,后面以三个点补充(参数一截取+三个点=二参数)
	//System.out.println(StringUtils.abbreviate("中华人民共和国", 5));
	//2头加点这个有点乱。本例结果: ...ijklmno
	//System.out.println(StringUtils.abbreviate("abcdefghijklmno", 12, 10));
	//保留指定长度，最后一个字符前加点.本例结果: ab.f
	//System.out.println(StringUtils.abbreviateMiddle("abcdef", ".", 4));
	//转换,刷选~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//转换第一个字符为大写.如何第一个字符是大写原始返回
	//System.out.println(StringUtils.capitalize("Ddf"));
	//转换第一个字符为大写.如何第一个字符是大写原始返回
	//System.out.println(StringUtils.uncapitalize("DTf"));
	//反向转换，大写变小写，小写变大写
	//System.out.println(StringUtils.swapCase("I am Jiang, Hello"));
	//将字符串倒序排列
	//System.out.println(StringUtils.reverse("中国人民"));
	//根据特定字符(二参数)分隔进行反转
	//System.out.println(StringUtils.reverseDelimited("中:国:人民", ':'));
}

		//将数组转换为List
		private static void ArrayToList(String[] str){
			System.out.println(Arrays.asList(str) + " 长度:" + str.length);
		}
		
		//获得集合数据
		private static List getListData(){
			List list = new ArrayList();
			list.add("你好");
			list.add(null);
			list.add("他好");
			list.add("大家好");
			return list;
		}
		
		//获得数组数据 
		private static String[] getArrayData(){
			return (String[]) getListData().toArray(new String[0]);
		}
		
		public static void main(String[] args) {
			TestStr();
		}
     * 
     * 
     * 
     */
	//***********************************************************************************************


//��������ַ����һ�α���ת������ֹSQLע��
    public static String StringtoSql(String str) {
        str = nullToString(str, "");
        try {
            str = str.trim().replace('\'', (char) 1);
        } catch (Exception e) {
            return "";
        }
        return str;
    }

//���ַ���ж��α���ת������ֹ����ʱ�쳣
    public static String SqltoString(String str) {
        str = nullToString(str, "");
        try {
            str = str.replace( (char) 1, '\'').trim();
        } catch (Exception e) {
            return "";
        }
        return str;
    }

//���ַ����Unicode����
    public static String toUnicode(String strvalue) {
        try {
            if (strvalue == null) {
                return null;
            } else {
                strvalue = new String(strvalue.getBytes("GBK"), "ISO8859_1");
                return strvalue;
            }
        } catch (Exception e) {
            return "";
        }
    }

//�ж��Ƿ�Ϊ��ǰʱ��
    public static boolean compareNowTime(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = format.parse(date);  //����һ��Date���ʵ��
        } catch (ParseException ex) {
        }
        if (System.currentTimeMillis() - 259200000 < d.getTime()) {
            return true;
        }
        return false;
    }

//�ж��û�������Ƿ������ֻ���ĸ
    public static boolean isID(String str) {
        if (str != null && str.length() > 0) {
            if (str.charAt(0) < 57 && str.charAt(0) > 48) {
                return false;
            }
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) < 65 && str.charAt(i) > 57 || str.charAt(i) > 90
                    && str.charAt(i) < 97 && str.charAt(i) != 95 || str.charAt(i) > 122 ||
                    str.charAt(i) < 48) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

//�����ַ��еĿ�ֵ
    public static final String nullToString(String v, String toV) {
        if (v == null) {
            v = toV;
        }
        return v;
    }

// ��SQL���������Ŀ�ֵ���д���
    public static final String SqlToLink(String str) {
        str = StringUtil.nullToString(str, "");
        if ("".equals(str)) {
            str = " LIKE '%' ";
        } else {
            str = (" LIKE '%" + str + "%' ");
        }
        return str;
    }

//������ֵת��Ϊ�ַ�
    public static final String SqlToLink(int i) {
        String str = "";
        try {
            str = new Integer(i).toString();
        } catch (Exception e) {}
        if (i == -1) {
            str = "";
        }
        return StringUtil.SqlToLink(str);
    }
	//***********************************************************************************************


    // ת��html�ĳ���;
    private static final char[] APOS_ENCODE = "&apos;".toCharArray();
	private static final char[] BR_TAG = "<BR>".toCharArray();

    /**
     * ��ʼ��,������һ����ֻ�ܵ���
     * 
     */
    private static Object initLock = new Object();

    /**
     * ���linde�е�oldStringΪnewString
     *
     * @���� line ��Ҫ�������ַ�
     * @���� oldString the String that should be replaced by newString
     * @param newString the String that will replace all instances of oldString
     *
     * @return a String will all instances of oldString replaced by newString
     */

  
	public static String convertNewlines(String input) {
        char [] chars = input.toCharArray();
        int cur = 0;
        int len = chars.length;
        StringBuffer buf = new StringBuffer(len);
        // Loop through each character lookin for newlines.
        for (int i=0; i<len; i++) {
            // If we've found a Unix newline, add BR tag.
            if (chars[i]=='\n') {
                buf.append(chars, cur, i-cur).append(BR_TAG);
                cur = i+1;
            }
            // If we've found a Windows newline, add BR tag.
            else if (chars[i]=='\r' && i<len-1 && chars[i+1]=='\n') {
                buf.append(chars, cur, i-cur).append(BR_TAG);
                i++;
                cur = i+1;
            }
        }
        // Add whatever chars are left to buffer.
        buf.append(chars, cur, len-cur);
        return buf.toString();
    }

	public static String  getTranslateStr(String sourceStr,String fieldStr){
	//�����߼����ʽ��ת������
		  String []  sourceList;
		  String resultStr="";
		  //dim i,j
		  if (sourceStr.indexOf(" ")>0){ 
			 boolean isOperator=true;
			 sourceList=sourceStr.split(" ");
			 //'--------------------------------------------------------
			 //rem Response.Write "num:" & cstr(ubound(sourceList)) & "<br>"
			for(int i=0;i<sourceList.length;i++){
				 if(sourceList[i].equals("AND")||sourceList[i].equals("&")||sourceList[i].equals("��")||sourceList[i].equals("��")){
				  	 resultStr=resultStr+" and ";
					 isOperator=true;
				 }
				 else if(sourceList[i].equals("OR")||sourceList[i].equals("|")||sourceList[i].equals("��")){
				 	resultStr=resultStr + " or ";
					isOperator = true;
				 }
				 else if(sourceList[i].equals("NOT")||sourceList[i].equals("!")||sourceList[i].equals("��")||sourceList[i].equals("��")){
				 	resultStr=resultStr + " not ";
					isOperator = true;
				 }
				 else if(sourceList[i].equals("(")||sourceList[i].equals("��")||sourceList[i].equals("��")){
					resultStr=resultStr + " ( ";
					isOperator = true;
				 }
				 else if(sourceList[i].equals(")")||sourceList[i].equals("��")||sourceList[i].equals("��")){
					resultStr=resultStr + " ) ";
					isOperator = true;
				 }
				 else{
					if(!"".equals(sourceList[i])){
						if (!isOperator)
						{
							resultStr=resultStr + " and ";
						}
						if (sourceList[i].indexOf("%")>0)
						{
							resultStr=resultStr+" "+fieldStr+ " like '" + sourceList[i].replaceAll("'","''") + "' ";

						} 
						else
							resultStr=resultStr+" "+fieldStr+ " like '%" + sourceList[i].replaceAll("'","''") + "%' ";
						isOperator=false;
					}
				 }
			}
			return resultStr;
		  }
		  else{
				if (sourceStr.indexOf("%")>0)
				{
					resultStr=resultStr+" "+fieldStr+ " like '" + sourceStr.replaceAll("'","''") + "' ";

				} 
				else
					resultStr=resultStr+" "+fieldStr+ " like '%" + sourceStr.replaceAll("'","''") + "%' ";
						
			return resultStr;
		  }

				 
								
	}
    //***********************************************************************************************

//	public static final String UTF8 = "UTF-8";
//	public static final String UTF16 = "UTF-16";
//	public static final String GB2312 = "GB2312";
//	public static final String GBK = "GBK";
//	public static final String ISO8859_1 = "ISO-8859-1";
//	public static final String ISO8859_2 = "ISO-8859-2";
//	private static final String QUOTE = "&quot;";
//	private static final String AMP = "&amp;";
//	private static final String LT = "&lt;";
//	private static final String GT = "&gt;";
//	private static final int FILLCHAR = '=';
//	private static final String CVT = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
//	private static final String NUMBER_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//	private static Random randGen = new Random();

 

  

	  

	public static final String ISO2GB(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("ISO-8859-1"), "GB2312");
		} catch (UnsupportedEncodingException ex) {
			result = ex.toString();
		}
		return result;
	}

	public static final String GB2ISO(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("GB2312"), "ISO-8859-1");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static final String Utf8URLencode(String text) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				result.append(c);
			} else {
				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return result.toString();
	}

	public static final String Utf8URLdecode(String text) {
		String result = "";
		int p = 0;
		if (text != null && text.length() > 0) {
			text = text.toLowerCase();
			p = text.indexOf("%e");
			if (p == -1) return text;
			while (p != -1) {
				result += text.substring(0, p);
				text = text.substring(p, text.length());
				if (text == "" || text.length() < 9) return result;
				result += CodeToWord(text.substring(0, 9));
				text = text.substring(9, text.length());
				p = text.indexOf("%e");
			}
		}
		return result + text;
	}

	private static final String CodeToWord(String text) {
		String result;
		if (Utf8codeCheck(text)) {
			byte[] code = new byte[3];
			code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
			code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
			code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
			try {
				result = new String(code, "UTF-8");
			} catch (UnsupportedEncodingException ex) {
				result = null;
			}
		} else {
			result = text;
		}
		return result;
	}

	private static final boolean Utf8codeCheck(String text) {
		String sign = "";
		if (text.startsWith("%e")) for (int i = 0, p = 0; p != -1; i++) {
			p = text.indexOf("%", p);
			if (p != -1) p++;
			sign += p;
		}
		return sign.equals("147-1");
	}

	public static final boolean isUtf8Url(String text) {
		text = text.toLowerCase();
		int p = text.indexOf("%");
		if (p != -1 && text.length() - p > 9) {
			text = text.substring(p, p + 9);
		}
		return Utf8codeCheck(text);
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


	public static void main2(String args[]) {
		String reStr = "+";
		System.out.println((new StringBuilder()).append("AAA").append(reStr)
				.toString());
		String regEx = "[+]";
	}

	public static String[] getValueAsArray(Object value) {
		if (value == null) {
			return new String[] {}; // put in a placeholder
		}
		if (value instanceof String[]) {
			return (String[]) value;
		} else if (value instanceof List) {
			List valueList = (List) value;
			return (String[]) valueList.toArray(new String[valueList.size()]);
		}
		return new String[] { value.toString() };
	}

	public static boolean isNotEmpty(String pStr) {
		return !isEmpty(pStr);
	}

	public static boolean isEmpty1(String pStr) {
		if (pStr == null) {
			return true;
		}
		if ("".equals(pStr)) {
			return true;
		}
		return false;
	}

	/**
	 * Convenience method to return a String array as a delimited (e.g. CSV)
	 * String. E.g. useful for <code>toString()</code> implementations.
	 * 
	 * @param arr
	 *            array to display. Elements may be of any type (toString will
	 *            be called on each element).
	 * @param delim
	 *            delimiter to use (probably a ",")
	 */
	public static String arrayToDelimitedString(Object[] arr, String delim) {
		/*
		 * if (ObjectUtils.isEmpty(arr)) { return ""; }
		 */
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	public static String arrayToDelimitedCharString(Object[] arr, String delim) {
		/*
		 * if (ObjectUtils.isEmpty(arr)) { return ""; }
		 */
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append("'").append(arr[i]).append("'");
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a String array as a CSV String. E.g. useful
	 * for <code>toString()</code> implementations.
	 * 
	 * @param arr
	 *            array to display. Elements may be of any type (toString will
	 *            be called on each element).
	 */
	public static String arrayToCommaDelimitedString(Object[] arr) {
		return arrayToDelimitedString(arr, ",");
	}

	public static String nullToBlank(String src) {
		if (src == null)
			return "";
		else
			return src;
	}

	/**
	 * org.apache.commons.lang.StringUtil使用说明 public static void TestStr(){
	 * //null 和 ""操作~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //判断是否Null 或者 ""
	 * //System.out.println(StringUtil.isEmpty(null));
	 * //System.out.println(StringUtil.isNotEmpty(null)); //判断是否null 或者 ""
	 * 去空格~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * //System.out.println(StringUtil.isBlank("  "));
	 * //System.out.println(StringUtil.isNotBlank(null));
	 * //去空格.Null返回null~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * //System.out.println(StringUtil.trim(null)); //去空格，将Null和"" 转换为Null
	 * //System.out.println(StringUtil.trimToNull("")); //去空格，将NULL 和 "" 转换为""
	 * //System.out.println(StringUtil.trimToEmpty(null)); //可能是对特殊空格符号去除？？
	 * //System.out.println(StringUtil.strip("大家好  啊  \t"));
	 * //同上，将""和null转换为Null //System.out.println(StringUtil.stripToNull(" \t"));
	 * //同上，将""和null转换为"" //System.out.println(StringUtil.stripToEmpty(null));
	 * //将""或者Null 转换为 "" //System.out.println(StringUtil.defaultString(null));
	 * //仅当字符串为Null时 转换为指定的字符串(二参数)
	 * //System.out.println(StringUtil.defaultString("", "df"));
	 * //当字符串为null或者""时，转换为指定的字符串(二参数)
	 * //System.out.println(StringUtil.defaultIfEmpty(null, "sos"));
	 * //去空格.去字符~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * //如果第二个参数为null去空格(否则去掉字符串2边一样的字符，到不一样为止)
	 * //System.out.println(StringUtil.strip("fsfsdf", "f"));
	 * //如果第二个参数为null只去前面空格(否则去掉字符串前面一样的字符，到不一样为止)
	 * //System.out.println(StringUtil.stripStart("ddsuuu ", "d"));
	 * //如果第二个参数为null只去后面空格，(否则去掉字符串后面一样的字符，到不一样为止)
	 * //System.out.println(StringUtil.stripEnd("dabads", "das"));
	 * //对数组没个字符串进行去空格。 //ArrayToList(StringUtil.stripAll(new String[]{" 中华 ",
	 * "民 国 ", "共和 "})); //如果第二个参数为null.对数组每个字符串进行去空格。(否则去掉数组每个元素开始和结尾一样的字符)
	 * //ArrayToList(StringUtil.stripAll(new String[]{" 中华 ", "民 国", "国共和国"},
	 * "国"));
	 * //查找,判断~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * ~~~~~~~ //判断2个字符串是否相等相等,Null也相等
	 * //System.out.println(StringUtil.equals(null, null)); //不区分大小写比较
	 * //System.out.println(StringUtil.equalsIgnoreCase("abc", "ABc"));
	 * //查找，不知道怎么弄这么多查找，很多不知道区别在哪？费劲~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * //普通查找字符，如果一参数为null或者""返回-1 //System.out.println(StringUtil.indexOf(null,
	 * "a")); //从指定位置(三参数)开始查找，本例从第2个字符开始查找k字符
	 * //System.out.println(StringUtil.indexOf("akfekcd中华", "k", 2)); //未发现不同之处
	 * //System.out.println(StringUtil.ordinalIndexOf("akfekcd中华", "k", 2));
	 * //查找,不区分大小写 //System.out.println(StringUtil.indexOfIgnoreCase("adfs",
	 * "D")); //从指定位置(三参数)开始查找,不区分大小写
	 * //System.out.println(StringUtil.indexOfIgnoreCase("adfs", "a", 3));
	 * //从后往前查找 //System.out.println(StringUtil.lastIndexOf("adfas", "a"));
	 * //未理解,此结果为2 //System.out.println(StringUtil.lastIndexOf("d饿abasdafs我",
	 * "a", 3)); //未解,此结果为-1
	 * //System.out.println(StringUtil.lastOrdinalIndexOf("yksdfdht", "f", 2));
	 * //从后往前查，不区分大小写
	 * //System.out.println(StringUtil.lastIndexOfIgnoreCase("sdffet", "E"));
	 * //未解,此结果为1
	 * //System.out.println(StringUtil.lastIndexOfIgnoreCase("efefrfs看", "F" ,
	 * 2)); //检查是否查到，返回boolean,null返回假
	 * //System.out.println(StringUtil.contains("sdf", "dg"));
	 * //检查是否查到，返回boolean,null返回假,不区分大小写
	 * //System.out.println(StringUtil.containsIgnoreCase("sdf", "D"));
	 * //检查是否有含有空格,返回boolean
	 * //System.out.println(StringUtil.containsWhitespace(" d"));
	 * //查询字符串跟数组任一元素相同的第一次相同的位置
	 * //System.out.println(StringUtil.indexOfAny("absfekf", new String[]{"f",
	 * "b"})); //查询字符串中指定字符串(参数二)出现的次数
	 * //System.out.println(StringUtil.indexOfAny("afefes", "e"));
	 * //查找字符串中是否有字符数组中相同的字符，返回boolean
	 * //System.out.println(StringUtil.containsAny("asfsd", new char[]{'k', 'e',
	 * 's'})); //未理解与lastIndexOf不同之处。是否查到，返回boolean
	 * //System.out.println(StringUtil.containsAny("啡f咖啡", "咖")); //未解
	 * //System.out.println(StringUtil.indexOfAnyBut("seefaff", "af"));
	 * //判断字符串中所有字符，都是出自参数二中。
	 * //System.out.println(StringUtil.containsOnly("中华华", "华"));
	 * //判断字符串中所有字符，都是出自参数二的数组中。
	 * //System.out.println(StringUtil.containsOnly("中华中", new char[]{'中',
	 * '华'})); //判断字符串中所有字符，都不在参数二中。
	 * //System.out.println(StringUtil.containsNone("中华华", "国"));
	 * //判断字符串中所有字符，都不在参数二的数组中。
	 * //System.out.println(StringUtil.containsNone("中华中", new char[]{'中',
	 * '达人'})); //从后往前查找字符串中与字符数组中相同的元素第一次出现的位置。本例为4
	 * //System.out.println(StringUtil.lastIndexOfAny("中国人民共和国", new
	 * String[]{"国人", "共和"})); //未发现与indexOfAny不同之处 查询字符串中指定字符串(参数二)出现的次数
	 * //System.out.println(StringUtil.countMatches("中国人民共和中国", "中国"));
	 * //检查是否CharSequence的只包含Unicode的字母。空将返回false。一个空的CharSequence（长（）=
	 * 0）将返回true //System.out.println(StringUtil.isAlpha("这是干什么的2"));
	 * //检查是否只包含Unicode的CharSequence的字母和空格（''）。空将返回一个空的CharSequence假（长（）=
	 * 0）将返回true。 //System.out.println(StringUtil.isAlphaSpace("NBA直播 "));
	 * //检查是否只包含Unicode的CharSequence的字母或数字。空将返回false。一个空的CharSequence（长（）=
	 * 0）将返回true。 //System.out.println(StringUtil.isAlphanumeric("NBA直播"));
	 * //如果检查的Unicode
	 * CharSequence的只包含字母，数字或空格（''）。空将返回false。一个空的CharSequence（长（）= 0）将返回true。
	 * //System.out.println(StringUtil.isAlphanumericSpace("NBA直播"));
	 * //检查是否只包含ASCII可CharSequence的字符。空将返回false。一个空的CharSequence（长（）= 0）将返回true。
	 * //System.out.println(StringUtil.isAsciiPrintable("NBA直播")); //检查是否只包含数值。
	 * //System.out.println(StringUtil.isNumeric("NBA直播")); //检查是否只包含数值或者空格
	 * //System.out.println(StringUtil.isNumericSpace("33 545")); //检查是否只是空格或""。
	 * //System.out.println(StringUtil.isWhitespace(" ")); //检查是否全是英文小写。
	 * //System.out.println(StringUtil.isAllLowerCase("kjk33")); //检查是否全是英文大写。
	 * //System.out.println(StringUtil.isAllUpperCase("KJKJ"));
	 * //交集操作~~~~~~~~~~~
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * //去掉参数2字符串中在参数一中开头部分共有的部分，结果为:人民共和加油
	 * //System.out.println(StringUtil.difference("中国加油", "中国人民共和加油"));
	 * //统计2个字符串开始部分共有的字符个数
	 * //System.out.println(StringUtil.indexOfDifference("ww.taobao",
	 * "www.taobao.com")); //统计数组中各个元素的字符串开始都一样的字符个数
	 * //System.out.println(StringUtil.indexOfDifference(new String[] {"中国加油",
	 * "中国共和", "中国人民"})); //取数组每个元素共同的部分字符串
	 * //System.out.println(StringUtil.getCommonPrefix(new String[] {"中国加油",
	 * "中国共和", "中国人民"})); //统计参数一中每个字符与参数二中每个字符不同部分的字符个数
	 * //System.out.println(StringUtil.getLevenshteinDistance("中国共和发国人民",
	 * "共和国")); //判断开始部分是否与二参数相同
	 * //System.out.println(StringUtil.startsWith("中国共和国人民", "中国"));
	 * //判断开始部分是否与二参数相同。不区分大小写
	 * //System.out.println(StringUtil.startsWithIgnoreCase("中国共和国人民", "中国"));
	 * //判断字符串开始部分是否与数组中的某一元素相同
	 * //System.out.println(StringUtil.startsWithAny("abef", new String[]{"ge",
	 * "af", "ab"})); //判断结尾是否相同
	 * //System.out.println(StringUtil.endsWith("abcdef", "def"));
	 * //判断结尾是否相同，不区分大小写
	 * //System.out.println(StringUtil.endsWithIgnoreCase("abcdef", "Def"));
	 * //字符串截取~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * //截取指定位置的字符，null返回null.""返回""
	 * //System.out.println(StringUtil.substring("国民党", 2)); //截取指定区间的字符
	 * //System.out.println(StringUtil.substring("中国人民共和国", 2, 4));
	 * //从左截取指定长度的字符串 //System.out.println(StringUtil.left("说点什么好呢", 3));
	 * //从右截取指定长度的字符串 //System.out.println(StringUtil.right("说点什么好呢", 3));
	 * //从第几个开始截取，三参数表示截取的长度 //System.out.println(StringUtil.mid("说点什么好呢", 3,
	 * 2)); //截取到等于第二个参数的字符串为止
	 * //System.out.println(StringUtil.substringBefore("说点什么好呢", "好"));
	 * //从左往右查到相等的字符开始，保留后边的，不包含等于的字符。本例：什么好呢
	 * //System.out.println(StringUtil.substringAfter("说点什么好呢", "点"));
	 * //这个也是截取到相等的字符，但是是从右往左.本例结果：说点什么好
	 * //System.out.println(StringUtil.substringBeforeLast("说点什么好点呢", "点"));
	 * //这个截取同上是从右往左。但是保留右边的字符
	 * //System.out.println(StringUtil.substringAfterLast("说点什么好点呢？", "点"));
	 * //截取查找到第一次的位置，和第二次的位置中间的字符。如果没找到第二个返回null。本例结果:2010世界杯在
	 * //System.out.println(StringUtil.substringBetween("南非2010世界杯在南非，在南非",
	 * "南非")); //返回参数二和参数三中间的字符串，返回数组形式
	 * //ArrayToList(StringUtil.substringsBetween("[a][b][c]", "[", "]"));
	 * //分割~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * //用空格分割成数组，null为null //ArrayToList(StringUtil.split("中华 人民  共和"));
	 * //以指定字符分割成数组 //ArrayToList(StringUtil.split("中华 ,人民,共和", ","));
	 * //以指定字符分割成数组，第三个参数表示分隔成数组的长度，如果为0全体分割
	 * //ArrayToList(StringUtil.split("中华 ：人民：共和", "：", 2));
	 * //未发现不同的地方,指定字符分割成数组
	 * //ArrayToList(StringUtil.splitByWholeSeparator("ab-!-cd-!-ef", "-!-"));
	 * //未发现不同的地方,以指定字符分割成数组，第三个参数表示分隔成数组的长度
	 * //ArrayToList(StringUtil.splitByWholeSeparator("ab-!-cd-!-ef", "-!-",
	 * 2)); //分割，但" "不会被忽略算一个元素,二参数为null默认为空格分隔
	 * //ArrayToList(StringUtil.splitByWholeSeparatorPreserveAllTokens
	 * (" ab   de fg ", null)); //同上，分割," "不会被忽略算一个元素。第三个参数代表分割的数组长度。
	 * //ArrayToList
	 * (StringUtil.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null,
	 * 3)); //未发现不同地方,分割
	 * //ArrayToList(StringUtil.splitPreserveAllTokens(" ab   de fg "));
	 * //未发现不同地方,指定字符分割成数组
	 * //ArrayToList(StringUtil.splitPreserveAllTokens(" ab   de fg ", null));
	 * //未发现不同地方,以指定字符分割成数组，第三个参数表示分隔成数组的长度
	 * //ArrayToList(StringUtil.splitPreserveAllTokens(" ab   de fg ", null,
	 * 2)); //以不同类型进行分隔
	 * //ArrayToList(StringUtil.splitByCharacterType("AEkjKr i39:。中文")); //未解
	 * //ArrayToList(StringUtil.splitByCharacterTypeCamelCase("ASFSRules234"));
	 * //拼接~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //将数组转换为字符串形式
	 * //System.out.println(StringUtil.concat(getArrayData()));
	 * //拼接时用参数一得字符相连接.注意null也用连接符连接了
	 * //System.out.println(StringUtil.concatWith(",", getArrayData()));
	 * //也是拼接。未发现区别 //System.out.println(StringUtil.join(getArrayData()));
	 * //用连接符拼接，为发现区别 //System.out.println(StringUtil.join(getArrayData(),
	 * ":")); //拼接指定数组下标的开始(三参数)和结束(四参数,不包含)的中间这些元素，用连接符连接
	 * //System.out.println(StringUtil.join(getArrayData(), ":", 1, 3));
	 * //用于集合连接字符串.用于集合 //System.out.println(StringUtil.join(getListData(),
	 * ":"));
	 * //移除，删除~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * ~~~~~~~~~~~~~~~~~~~ //删除所有空格符
	 * //System.out.println(StringUtil.deleteWhitespace(" s 中 你 4j"));
	 * //移除开始部分的相同的字符
	 * //System.out.println(StringUtil.removeStart("www.baidu.com", "www."));
	 * //移除开始部分的相同的字符,不区分大小写
	 * //System.out.println(StringUtil.removeStartIgnoreCase("www.baidu.com",
	 * "WWW")); //移除后面相同的部分
	 * //System.out.println(StringUtil.removeEnd("www.baidu.com", ".com"));
	 * //移除后面相同的部分，不区分大小写
	 * //System.out.println(StringUtil.removeEndIgnoreCase("www.baidu.com",
	 * ".COM")); //移除所有相同的部分
	 * //System.out.println(StringUtil.remove("www.baidu.com/baidu", "bai"));
	 * //移除结尾字符为"\n", "\r", 或者 "\r\n".
	 * //System.out.println(StringUtil.chomp("abcrabc\r")); //也是移除，未解。去结尾相同字符
	 * //System.out.println(StringUtil.chomp("baidu.com", "com"));
	 * //去掉末尾最后一个字符.如果是"\n", "\r", 或者 "\r\n"也去除
	 * //System.out.println(StringUtil.chop("wwe.baidu"));
	 * //替换~~~~~~~~~~~~~~~~~~
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //替换指定的字符，只替换第一次出现的
	 * //System.out.println(StringUtil.replaceOnce("www.baidu.com/baidu",
	 * "baidu", "hao123")); //替换所有出现过的字符
	 * //System.out.println(StringUtil.replace("www.baidu.com/baidu", "baidu",
	 * "hao123")); //也是替换，最后一个参数表示替换几个
	 * //System.out.println(StringUtil.replace("www.baidu.com/baidu", "baidu",
	 * "hao123", 1));
	 * //这个有意识，二三参数对应的数组，查找二参数数组一样的值，替换三参数对应数组的值。本例:baidu替换为taobao。com替换为net
	 * //System.out.println(StringUtil.replaceEach("www.baidu.com/baidu", new
	 * String[]{"baidu", "com"}, new String[]{"taobao", "net"})); //同上，未发现不同
	 * //System
	 * .out.println(StringUtil.replaceEachRepeatedly("www.baidu.com/baidu", new
	 * String[]{"baidu", "com"}, new String[]{"taobao", "net"}));
	 * //这个更好，不是数组对应，是字符串参数二和参数三对应替换.(二三参数不对应的话，自己看后果)
	 * //System.out.println(StringUtil.replaceChars("www.baidu.com", "bdm",
	 * "qo")); //替换指定开始(参数三)和结束(参数四)中间的所有字符
	 * //System.out.println(StringUtil.overlay("www.baidu.com", "hao123", 4,
	 * 9));
	 * //添加，增加~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * ~~~~~ //复制参数一的字符串，参数二为复制的次数 //System.out.println(StringUtil.repeat("ba",
	 * 3)); //复制参数一的字符串，参数三为复制的次数。参数二为复制字符串中间的连接字符串
	 * //System.out.println(StringUtil.repeat("ab", "ou", 3));
	 * //如何字符串长度小于参数二的值，末尾加空格补全。(小于字符串长度不处理返回)
	 * //System.out.println(StringUtil.rightPad("海川", 4));
	 * //字符串长度小于二参数，末尾用参数三补上，多于的截取(截取补上的字符串)
	 * //System.out.println(StringUtil.rightPad("海川", 4, "河流啊")); //同上在前面补全空格
	 * //System.out.println(StringUtil.leftPad("海川", 4));
	 * //字符串长度小于二参数，前面用参数三补上，多于的截取(截取补上的字符串)
	 * //System.out.println(StringUtil.leftPad("海川", 4, "大家好"));
	 * //字符串长度小于二参数。在两侧用空格平均补全（测试后面补空格优先）
	 * //System.out.println(StringUtil.center("海川", 3));
	 * //字符串长度小于二参数。在两侧用三参数的字符串平均补全（测试后面补空格优先）
	 * //System.out.println(StringUtil.center("海川", 5, "流"));
	 * //只显示指定数量(二参数)的字符,后面以三个点补充(参数一截取+三个点=二参数)
	 * //System.out.println(StringUtil.abbreviate("中华人民共和国", 5));
	 * //2头加点这个有点乱。本例结果: ...ijklmno
	 * //System.out.println(StringUtil.abbreviate("abcdefghijklmno", 12, 10));
	 * //保留指定长度，最后一个字符前加点.本例结果: ab.f
	 * //System.out.println(StringUtil.abbreviateMiddle("abcdef", ".", 4));
	 * //转换,
	 * 刷选~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * //转换第一个字符为大写.如何第一个字符是大写原始返回
	 * //System.out.println(StringUtil.capitalize("Ddf"));
	 * //转换第一个字符为大写.如何第一个字符是大写原始返回
	 * //System.out.println(StringUtil.uncapitalize("DTf")); //反向转换，大写变小写，小写变大写
	 * //System.out.println(StringUtil.swapCase("I am Jiang, Hello"));
	 * //将字符串倒序排列 //System.out.println(StringUtil.reverse("中国人民"));
	 * //根据特定字符(二参数)分隔进行反转
	 * //System.out.println(StringUtil.reverseDelimited("中:国:人民", ':')); }
	 * 
	 * //将数组转换为List private static void ArrayToList(String[] str){
	 * System.out.println(Arrays.asList(str) + " 长度:" + str.length); }
	 * 
	 * //获得集合数据 private static List getListData(){ List list = new ArrayList();
	 * list.add("你好"); list.add(null); list.add("他好"); list.add("大家好"); return
	 * list; }
	 * 
	 * //获得数组数据 private static String[] getArrayData(){ return (String[])
	 * getListData().toArray(new String[0]); }
	 * 
	 * public static void main(String[] args) { TestStr(); }
	 * 
	 * 
	 * 
	 */
	/*
	 * (non-Javadoc) <p>Title: isPattern</p> <p>Description: </p>
	 * 
	 * @param pattern
	 * 
	 * @return
	 * 
	 * @see
	 * com.sysmanage.common.tools.macher.Matcher#isPattern(java.lang.String)
	 */
	public boolean isPattern(String pattern) {
		return (pattern.indexOf('*') != -1 || pattern.indexOf('?') != -1);
	}

	/*
	 * (non-Javadoc) <p>Title: match</p> <p>Description: </p>
	 * 
	 * @param pattern
	 * 
	 * @param value
	 * 
	 * @return
	 * 
	 * @see com.sysmanage.common.tools.macher.Matcher#match(java.lang.String,
	 * java.lang.Object)
	 */
	public boolean match(String pattern, Object value) {
		if (!(value instanceof String)) return false;
		String str = (String) value;
		if (pattern == null || pattern.trim().equals("") || str == null
				|| str.trim().equals("")) return false;
		pattern = pattern.replaceAll("[.]", "\\\\.");
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < pattern.length(); i++) {
			char subChar = pattern.charAt(i);
			switch (subChar) {
			case '*':
				sb.append(".*");
				break;
			case '?':
				sb.append(".{1}");
				break;
			default:
				sb.append(subChar);
			}
		}
		pattern = sb.toString();
		return Pattern.matches(pattern, str);
	}

	public void main3() {
		// CharTools charTools = new CharTools();
		String url;
		url = "http://www.google.com/search?hl=zh-CN&newwindow=1&q=%E4%B8%AD%E5%9B%BD%E5%A4%A7%E7%99%BE%E7%A7%91%E5%9C%A8%E7%BA%BF%E5%85%A8%E6%96%87%E6%A3%80%E7%B4%A2&btnG=%E6%90%9C%E7%B4%A2&lr=";
		if (StringUtil.isUtf8Url(url)) {
			System.out.println(StringUtil.Utf8URLdecode(url));
		} else {
			System.out.println(URLDecoder.decode(url));
		}
		url = "http://www.baidu.com/baidu?word=%D6%D0%B9%FA%B4%F3%B0%D9%BF%C6%D4%DA%CF%DF%C8%AB%CE%C4%BC%EC%CB%F7&tn=myie2dg";
		if (StringUtil.isUtf8Url(url)) {
			System.out.println(StringUtil.Utf8URLdecode(url));
		} else {
			System.out.println(URLDecoder.decode(url));
		}
		// �����ַ���
		String myName = "G. Leeman";
		int length = "Best Wishes!".length();
		System.out.println("����1��" + myName.length()); // ���9
		System.out.println("����2��" + length); // ���3����6
		// �Ƚ��ַ��С
		String name1 = "programming in java";
		String name2 = "Programming in Java";
		System.out.println("�Ƚ�1��" + name1.equals(name2)); // ���false
		System.out.println("�Ƚ�2��" + name1.equalsIgnoreCase(name2)); // ���true
		System.out.println("�Ƚ�3��" + name2.compareTo("Program")); // �������
		// �����ַ��е��ַ�
		System.out.println("�ַ�1��" + name1.charAt(4)); // ���r����g
		System.out.println("�ַ�2��" + name1.indexOf('a')); // ���5
		System.out.println("�ַ�3��" + name2.lastIndexOf('a')); // ���18
		// �����ַ��е��Ӵ�
		String subname = "in";
		System.out.println("�Ӵ�1��" + name1.substring(3, 10)); // ���grammin
		System.out.println("�Ӵ�2��" + "abc".concat("123")); // ���abc123
		System.out.println("�Ӵ�3��" + name2.startsWith("Pro")); // ���true
		System.out.println("�Ӵ�4��" + name2.endsWith("in Java")); // ���true
		System.out.println("�Ӵ�5��" + name1.indexOf(subname)); // ���8
		System.out.println("�Ӵ�6��" + name1.lastIndexOf(subname)); // ���12
		// �ַ���������
		System.out.println("Сд��" + name2.toLowerCase()); // ���programming in
															// java
		System.out.println("��д��" + name2.toUpperCase()); // ���PROGRAMMING IN
															// JAVA
		System.out.println("�滻��" + name1.replace('a', 'A')); // ���progrAmming
																// in jAvA
	}

	public void main111() throws Exception {
		// char ch = '\u8D1F';
		// System.err.println(ch);
		String str = null;
		int hightPos, lowPos; // 定义高低位
		Random random = new Random();
		hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
		lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
		byte[] b = new byte[2];
		b[0] = (new Integer(hightPos).byteValue());
		b[1] = (new Integer(lowPos).byteValue());
		str = new String(b, "GBK");// 转成中文
		System.err.println(str);
	}

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
		return null == source || source.length() == 0
				|| source.trim().equals("");
	}

	/**
	 * 将Clob对象转换成字符串
	 */
	public static String clobToString(Clob clob) {
		if (null == clob) {
			return "";
		}
		StringBuffer sb = new StringBuffer(65535); // 64K
		Reader clobStream = null;
		try {
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];// 每次读取60K
			int i = 0;
			while ((i = clobStream.read(b)) != -1) {
				sb.append(b, 0, i);
			}
		} catch (Exception ex) {
			sb = new StringBuffer();
		} finally {
			try {
				if (null != clobStream) {
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

	/**
	 * 获得字符串
	 * 
	 * @param c
	 *            java.sql.Clob
	 * @return 字符串
	 */
	public static String getString(Clob c) {
		StringBuffer s = new StringBuffer();
		if (c != null) {
			try {
				BufferedReader bufferRead = new BufferedReader(
						c.getCharacterStream());
				try {
					String str;
					while ((str = bufferRead.readLine()) != null) {
						s.append(str);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return s.toString();
	}

	/**
	 * 获得Clob
	 * 
	 * @param s
	 *            字符串
	 * @return java.sql.Clob
	 */
	public static Clob getClob(String s) {
		Clob c = null;
		try {
			if (s != null) {
				c = new SerialClob(s.toCharArray());
			}
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public void main11() {
		String reStr = "+";
		System.out.println((new StringBuilder()).append("AAA").append(reStr)
				.toString());
		String regEx = "[+]";
	}

	public static void main(String[] args) {
		System.err.println(StringUtil.getUUID());
	}

	// �ж��ַ��Ƿ�Ϊ�գ���ɾ����β�ո�
	public static String convertNullCode(String tempSql) {
		if (tempSql == null) tempSql = "";
		return tempSql;
	}

	/**
	 * �ַ��滻����
	 * 
	 * @param originString
	 *            ԭ�ַ�
	 * @param oldString
	 *            ���滻�ַ�
	 * @param newString
	 *            �滻�ַ�
	 * @return �滻��������ַ�
	 */
	public static String replace1(String originString, String oldString,
			String newString) {
		String getstr = originString;
		while (getstr.indexOf(oldString) > -1) {
			getstr = getstr.substring(0, getstr.indexOf(oldString))
					+ newString
					+ getstr.substring(
							getstr.indexOf(oldString) + oldString.length(),
							getstr.length());
		}
		return getstr;
	}

	/**
	 * ����ת����GBKת��ΪISO-8859-1
	 * 
	 * @param tempSql
	 *            Ҫת�����ַ�
	 * @return
	 */
	public static String ISOCode(String tempSql) {
		String returnString = convertNullCode(tempSql);
		try {
			byte[] ascii = returnString.getBytes("GBK");
			returnString = new String(ascii, "ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	/**
	 * ����ת����ISO-8859-1ת��ΪGBK
	 * 
	 * @param tempSql
	 *            Ҫת�����ַ�
	 * @return
	 */
	public static String GBKCode(String tempSql) {
		String returnString = convertNullCode(tempSql);
		try {
			byte[] ascii = returnString.getBytes("ISO-8859-1");
			returnString = new String(ascii, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	/**
	 * ����ת�� ��srcCodeת��ΪdestCode
	 * 
	 * @param srcCode
	 *            ԭ����
	 * @param destCode
	 *            Ŀ�����
	 * @param strTmp
	 *            Ҫת�����ַ�
	 * @return
	 */
	public static String convertCode(String srcCode, String destCode,
			String strTmp) {
		String returnString = convertNullCode(strTmp);
		try {
			byte[] ascii = returnString.getBytes(srcCode);
			returnString = new String(ascii, destCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	/**
	 * ����ת����GBKת��Ϊbig5
	 * 
	 * @param tempSql
	 *            Ҫת�����ַ�
	 * @return
	 */
	public static String GBK2BIG5Code(String tempSql) {
		String returnString = convertNullCode(tempSql);
		try {
			byte[] ascii = returnString.getBytes("GBK");
			returnString = new String(ascii, "big5");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	// �滻�Ƿ��ַ�
	public static String convertHtml(String input) {
		StringBuffer returnString = new StringBuffer(input.length());
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				returnString = returnString.append("&lt");
			} else if (ch == '>') {
				returnString = returnString.append("&gt");
			} else if (ch == ' ') {
				returnString = returnString.append("&nbsp");
			} else if (ch == '\\') {
				returnString = returnString.append("&acute");
			} else {
				returnString = returnString.append(ch);
			}
		}
		return returnString.toString();
	}

	/*
	 *
	 */
	private String delSQlString(String sql) {
		String delSql = "in(";
		StringTokenizer Tokenizer = new StringTokenizer(sql, "|");
		// ��Ǳ�����ڷָ�����������
		delSql += Tokenizer.nextToken().toString();
		while (Tokenizer.hasMoreTokens()) {
			delSql += Tokenizer.nextToken() + ",";
		}
		delSql = delSql.substring(0, delSql.length() - 1) + ")";
		return delSql;
	}

	/*
	 * format selectedIDs to sql language in (...) second of methods bt own idea
	 */
	private String delNewSQlString(String sql) {
		return "in (" + sql.replace('|', ',') + ")";
	}

	private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
	private static final char[] AMP_ENCODE = "&amp;".toCharArray();
	private static final char[] LT_ENCODE = "&lt;".toCharArray();
	private static final char[] GT_ENCODE = "&gt;".toCharArray();

	/**
	 * This method takes a string which may contain HTML tags (ie, &lt;b&gt;,
	 * &lt;table&gt;, etc) and converts the '&lt'' and '&gt;' characters to
	 * their HTML escape sequences.
	 * 
	 * @param in
	 *            the text to be converted.
	 * @return the input string with the characters '&lt;' and '&gt;' replaced
	 *         with their HTML escape sequences.
	 */
	public static final String escapeHTMLTags1(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	public static String filterString(String allstr) {
		StringBuffer returnString = new StringBuffer(allstr.length());
		char ch = ' ';
		for (int i = 0; i < allstr.length(); i++) {
			ch = allstr.charAt(i);
			String lsTemp = "'";
			char lcTemp = lsTemp.charAt(0);
			if (ch == lcTemp) {
				returnString.append("''");
			} else {
				returnString.append(ch);
			}
		}
		return returnString.toString();
	}

	/**
	 * ���ֵĽ����ʽ
	 * 
	 * @param num
	 * @return
	 */
	public static String convertNumToMoney(int num) {
		NumberFormat formatc = NumberFormat.getCurrencyInstance(Locale.CHINA);
		String strcurr = formatc.format(num);
		System.out.println(strcurr);
		// num = NumberFormat.getInstance().setParseIntegerOnly(true));
		return strcurr;
	}

	public static void main1(String args[]) {
		StringUtil.convertNumToMoney(1234566);
	}

	/**
	 * <pre>
	 * ��
	 * String strVal="This is a dog";
	 * String strResult=CTools.replace(strVal,"dog","cat");
	 * ���
	 * strResult equals "This is cat"
	 * 
	 * @param strSrc Ҫ�����滻�������ַ�
	 * @param strOld Ҫ���ҵ��ַ�
	 * @param strNew Ҫ�滻���ַ�
	 * @return �滻����ַ�
	 * 
	 * <pre>
	 */
	public static final String replace11(String strSrc, String strOld,
			String strNew) {
		if (strSrc == null || strOld == null || strNew == null) return "";
		int i = 0;
		if (strOld.equals(strNew)) // �����¾��ַ�һ�������ѭ��
			return strSrc;
		if ((i = strSrc.indexOf(strOld, i)) >= 0) {
			char[] arr_cSrc = strSrc.toCharArray();
			char[] arr_cNew = strNew.toCharArray();
			int intOldLen = strOld.length();
			StringBuffer buf = new StringBuffer(arr_cSrc.length);
			buf.append(arr_cSrc, 0, i).append(arr_cNew);
			i += intOldLen;
			int j = i;
			while ((i = strSrc.indexOf(strOld, i)) > 0) {
				buf.append(arr_cSrc, j, i - j).append(arr_cNew);
				i += intOldLen;
				j = i;
			}
			buf.append(arr_cSrc, j, arr_cSrc.length - j);
			return buf.toString();
		}
		return strSrc;
	}

	/**
	 * ���ڽ��ַ��е������ַ�ת����Webҳ�п��԰�ȫ��ʾ���ַ�
	 * �ɶԱ?��ݾݽ��д����һЩҳ�������ַ���д�����'<','>','"',''','&'
	 * 
	 * @param strSrc
	 *            Ҫ�����滻�������ַ�
	 * @return �滻�����ַ����ַ�
	 * @since 1.0
	 */
	public static String htmlEncode(String strSrc) {
		if (strSrc == null) return "";
		char[] arr_cSrc = strSrc.toCharArray();
		StringBuffer buf = new StringBuffer(arr_cSrc.length);
		char ch;
		for (int i = 0; i < arr_cSrc.length; i++) {
			ch = arr_cSrc[i];
			if (ch == '<')
				buf.append("&lt;");
			else if (ch == '>')
				buf.append("&gt;");
			else if (ch == '"')
				buf.append("&quot;");
			else if (ch == '\'')
				buf.append("&#039;");
			else if (ch == '&')
				buf.append("&amp;");
			else
				buf.append(ch);
		}
		return buf.toString();
	}

	/**
	 * ���ڽ��ַ��е������ַ�ת����Webҳ�п��԰�ȫ��ʾ���ַ�
	 * �ɶԱ?��ݾݽ��д����һЩҳ�������ַ���д�����'<','>','"',''','&'
	 * 
	 * @param strSrc
	 *            Ҫ�����滻�������ַ�
	 * @param quotes
	 *            Ϊ0ʱ����ź�˫��Ŷ��滻��Ϊ1ʱ���滻����ţ�Ϊ2ʱ���滻˫��ţ�Ϊ3ʱ����ź�˫��Ŷ����滻
	 * @return �滻�����ַ����ַ�
	 * @since 1.0
	 */
	public static String htmlEncode(String strSrc, int quotes) {
		if (strSrc == null) return "";
		if (quotes == 0) {
			return htmlEncode(strSrc);
		}
		char[] arr_cSrc = strSrc.toCharArray();
		StringBuffer buf = new StringBuffer(arr_cSrc.length);
		char ch;
		for (int i = 0; i < arr_cSrc.length; i++) {
			ch = arr_cSrc[i];
			if (ch == '<')
				buf.append("&lt;");
			else if (ch == '>')
				buf.append("&gt;");
			else if (ch == '"' && quotes == 1)
				buf.append("&quot;");
			else if (ch == '\'' && quotes == 2)
				buf.append("&#039;");
			else if (ch == '&')
				buf.append("&amp;");
			else
				buf.append(ch);
		}
		return buf.toString();
	}

	/**
	 * ��htmlEncode����෴
	 * 
	 * @param strSrc
	 *            Ҫ����ת�����ַ�
	 * @return ת������ַ�
	 * @since 1.0
	 */
	public static String htmlDecode(String strSrc) {
		if (strSrc == null) return "";
		strSrc = strSrc.replaceAll("&lt;", "<");
		strSrc = strSrc.replaceAll("&gt;", ">");
		strSrc = strSrc.replaceAll("&quot;", "\"");
		strSrc = strSrc.replaceAll("&#039;", "'");
		strSrc = strSrc.replaceAll("&amp;", "&");
		return strSrc;
	}

	/**
	 * �ڽ���ݴ�����ݿ�ǰת��
	 * 
	 * @param strVal
	 *            Ҫת�����ַ�
	 * @return �ӡ�ISO8859_1������GBK���õ����ַ�
	 * @since 1.0
	 */
	public static String toChinese(String strVal) {
		try {
			if (strVal == null) {
				return "";
			} else {
				strVal = strVal.trim();
				strVal = new String(strVal.getBytes("ISO8859_1"), "GBK");
				return strVal;
			}
		} catch (Exception exp) {
			return "";
		}
	}

	/**
	 * ����ת�� ��UTF-8��GBK
	 * 
	 * @param strVal
	 * @return
	 */
	public static String toGBK(String strVal) {
		try {
			if (strVal == null) {
				return "";
			} else {
				strVal = strVal.trim();
				strVal = new String(strVal.getBytes("UTF-8"), "GBK");
				return strVal;
			}
		} catch (Exception exp) {
			return "";
		}
	}

	public static String toISO(String strVal) {
		try {
			if (strVal == null) {
				return "";
			} else {
				strVal = new String(strVal.getBytes("GBK"), "ISO8859_1");
				return strVal;
			}
		} catch (Exception exp) {
			return "";
		}
	}

	public static String gbk2UTF8(String strVal) {
		try {
			if (strVal == null) {
				return "";
			} else {
				strVal = new String(strVal.getBytes("GBK"), "UTF-8");
				return strVal;
			}
		} catch (Exception exp) {
			return "";
		}
	}

	public static String ISO2UTF8(String strVal) {
		try {
			if (strVal == null) {
				return "";
			} else {
				strVal = new String(strVal.getBytes("ISO-8859-1"), "UTF-8");
				return strVal;
			}
		} catch (Exception exp) {
			return "";
		}
	}

	public static String UTF82ISO(String strVal) {
		try {
			if (strVal == null) {
				return "";
			} else {
				strVal = new String(strVal.getBytes("UTF-8"), "ISO-8859-1");
				return strVal;
			}
		} catch (Exception exp) {
			return "";
		}
	}

	public static String toISOHtml(String str) {
		return toISO(htmlDecode(null2Blank((str))));
	}

	public static String toChineseAndHtmlEncode(String str, int quotes) {
		return htmlEncode(toChinese(str), quotes);
	}

	public static String str4Table(String str) {
		if (str == null)
			return "&nbsp;";
		else if (str.equals(""))
			return "&nbsp;";
		else
			return str;
	}

	public static int str2Int(String str) {
		int intVal;
		try {
			intVal = Integer.parseInt(str);
		} catch (Exception e) {
			intVal = 0;
		}
		return intVal;
	}

	public static double str2Double(String str) {
		double dVal = 0;
		try {
			dVal = Double.parseDouble(str);
		} catch (Exception e) {
			dVal = 0;
		}
		return dVal;
	}

	public static long str2Long(String str) {
		long longVal = 0;
		try {
			longVal = Long.parseLong(str);
		} catch (Exception e) {
			longVal = 0;
		}
		return longVal;
	}

	public static float stringToFloat(String floatstr) {
		Float floatee;
		floatee = Float.valueOf(floatstr);
		return floatee.floatValue();
	}

	// change the float type to the string type
	public static String floatToString(float value) {
		Float floatee = new Float(value);
		return floatee.toString();
	}

	public static String int2Str(int intVal) {
		String str;
		try {
			str = String.valueOf(intVal);
		} catch (Exception e) {
			str = "";
		}
		return str;
	}

	public static String long2Str(long longVal) {
		String str;
		try {
			str = String.valueOf(longVal);
		} catch (Exception e) {
			str = "";
		}
		return str;
	}

	/**
	 * null ����
	 * 
	 * @param str
	 *            Ҫ����ת�����ַ�
	 * @return ���strΪnullֵ�����ؿմ�"",���򷵻�str
	 */
	public static String null2Blank(String str) {
		if (str == null)
			return "";
		else
			return str;
	}

	/**
	 * null ����
	 * 
	 * @param d
	 *            Ҫ����ת�������ڶ���
	 * @return ���dΪnullֵ�����ؿմ�"",���򷵻�d.toString()
	 */
	public static String null2Blank(Date d) {
		if (d == null)
			return "";
		else
			return d.toString();
	}

	/**
	 * null ����
	 * 
	 * @param str
	 *            Ҫ����ת�����ַ�
	 * @return ���strΪnullֵ�����ؿմ�����0,���򷵻���Ӧ������
	 */
	public static int null2Zero(String str) {
		int intTmp;
		intTmp = str2Int(str);
		if (intTmp == -1)
			return 0;
		else
			return intTmp;
	}

	/**
	 * ��nullת��Ϊ�ַ�"0"
	 * 
	 * @param str
	 * @return
	 */
	public static String null2SZero(String str) {
		str = StringUtil.null2Blank(str);
		if (str.equals(""))
			return "0";
		else
			return str;
	}

	/**
	 * sql��� ����
	 * 
	 * @param sql
	 *            Ҫ���д����sql���
	 * @param dbtype
	 *            ��ݿ�����
	 * @return ������sql���
	 */
	public static String sql4DB(String sql, String dbtype) {
		if (!dbtype.equalsIgnoreCase("oracle")) {
			sql = StringUtil.toISO(sql);
		}
		return sql;
	}

	/**
	 * ���ַ����md5����
	 * 
	 * @param s
	 *            Ҫ���ܵ��ַ�
	 * @return md5���ܺ���ַ�
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * �ַ��GBK����ת��ΪUnicode����
	 * 
	 * @param text
	 * @return
	 */
	public static String StringToUnicode(String text) {
		String result = "";
		int input;
		StringReader isr;
		try {
			isr = new StringReader(new String(text.getBytes(), "GBK"));
		} catch (UnsupportedEncodingException e) {
			return "-1";
		}
		try {
			while ((input = isr.read()) != -1) {
				result = result + "&#x" + Integer.toHexString(input) + ";";
			}
		} catch (IOException e) {
			return "-2";
		}
		isr.close();
		return result;
	}

	/**
	 * 
	 * @param inStr
	 * @return
	 */
	public static String gb2utf(String inStr) {
		char temChr;
		int ascInt;
		int i;
		String result = new String("");
		if (inStr == null) {
			inStr = "";
		}
		for (i = 0; i < inStr.length(); i++) {
			temChr = inStr.charAt(i);
			ascInt = temChr + 0;
			// System.out.println("1=="+ascInt);
			// System.out.println("1=="+Integer.toBinaryString(ascInt));
			if (Integer.toHexString(ascInt).length() > 2) {
				result = result + "&#x" + Integer.toHexString(ascInt) + ";";
			} else {
				result = result + temChr;
			}
		}
		return result;
	}

	/**
	 * This method will encode the String to unicode.
	 * 
	 * @param gbString
	 * @return
	 */
	// ����:--------------------------------------------------------------------------------
	public static String gbEncoding(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		System.out.println("unicodeBytes is: " + unicodeBytes);
		return unicodeBytes;
	}

	/**
	 * This method will decode the String to a recognized String in ui.
	 * 
	 * @param dataStr
	 * @return
	 */
	public static StringBuffer decodeUnicode(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = dataStr.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = dataStr.substring(start + 2, dataStr.length());
			} else {
				charStr = dataStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16����parse�����ַ�
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer;
	}
	
	
	/** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
	public static final String US_ASCII = "US-ASCII";

	/** ISO拉丁字母表 No.1，也叫做ISO-LATIN-1 */
	public static final String ISO_8859_1 = "ISO-8859-1";

	/** 8 位 UCS 转换格式 */
	public static final String UTF_8 = "UTF-8";

	/** 16 位 UCS 转换格式，Big Endian(最低地址存放高位字节）字节顺序 */
	public static final String UTF_16BE = "UTF-16BE";

	/** 16 位 UCS 转换格式，Litter Endian（最高地址存放地位字节）字节顺序 */
	public static final String UTF_16LE = "UTF-16LE";

	/** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
	public static final String UTF_16 = "UTF-16";

	/** 中文超大字符集 **/
//	public static final String GBK = "GBK";
//
//	public static final String GB2312 = "GB2312";

	/** 将字符编码转换成US-ASCII码 */
	public static String toASCII(String str) throws UnsupportedEncodingException {
		return changeCharset(str, US_ASCII);
	}

	/** 将字符编码转换成ISO-8859-1 */
	public static String toISO_8859_1(String str) throws UnsupportedEncodingException {
		return changeCharset(str, ISO_8859_1);
	}

	/** 将字符编码转换成UTF-8 */
	public static String toUTF_8(String str) throws UnsupportedEncodingException {
		return changeCharset(str, UTF_8);
	}

	/** 将字符编码转换成UTF-16BE */
	public static String toUTF_16BE(String str) throws UnsupportedEncodingException {
		return changeCharset(str, UTF_16BE);
	}

	/** 将字符编码转换成UTF-16LE */
	public static String toUTF_16LE(String str) throws UnsupportedEncodingException {
		return changeCharset(str, UTF_16LE);
	}

	/** 将字符编码转换成UTF-16 */
	public static String toUTF_16(String str) throws UnsupportedEncodingException {
		return changeCharset(str, UTF_16);
	}

	/** 将字符编码转换成GBK */
	public static String toGBK1(String str) throws UnsupportedEncodingException {
		return changeCharset(str, GBK);
	}

	/** 将字符编码转换成GB2312 */
	public static String toGB2312(String str) throws UnsupportedEncodingException {
		return changeCharset(str, GB2312);
	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param str
	 *            待转换的字符串
	 * @param newCharset
	 *            目标编码
	 */
	private static String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
		if (str != null) {
			// 用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
			byte[] bs = str.getBytes();
			return new String(bs, newCharset); // 用新的字符编码生成字符串
		}
		return null;
	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param str
	 *            待转换的字符串
	 * @param oldCharset
	 *            源字符集
	 * @param newCharset
	 *            目标字符集
	 */
	public static String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
		if (str != null) {
			// 用源字符编码解码字符串
			byte[] bs = str.getBytes(oldCharset);
			return new String(bs, newCharset);
		}
		return null;
	}

	/**
	 * GBK编码到Unicode编码转换
	 * 
	 * @param dataStr
	 * @return
	 */
	public static String GBK2Unicode(String dataStr) {
		StringBuffer buffer = new StringBuffer();
		if (null == dataStr)
			return "";
		for (int i = 0; i < dataStr.length(); i++) {
			buffer.append("&#x");
			char c = dataStr.charAt(i);
			int t = (int) c;

			String last = Integer.toHexString(t / (16 * 16)).toUpperCase();
			if (last.length() < 2) {
				buffer.append("0" + last);
			} else {
				buffer.append(last);
			}
			String frist = Integer.toHexString(t % (16 * 16)).toUpperCase();
			if (frist.length() < 2) {
				buffer.append("0" + frist);
			} else {
				buffer.append(frist);
			}
			buffer.append(";");
		}
		return buffer.toString();
	}

	/**
	 * <p>
	 * 判断文件的编码格式。
	 * <p>
	 * <list>
	 * <li>前两字节为0xefbb: "UTF-8";
	 * <li>前两字节为EFBB 0xfffe: "Unicode";
	 * <li>前两字节为FFFE 0xfeff: "Unicode   big   endian";
	 * <li>前两字节为FEFF ANSI：无格式定义 <list>
	 * 
	 * @param file
	 *            - 文件
	 * @return
	 * @throws IOException
	 */
	public static String getFileCharset(File file) throws IOException {

		// 默认字符集编码格式
		String charset = "GBK";

		byte[] first3Bytes = new byte[3];

		boolean checked = false;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			bis.mark(0);

			// 获取前3个字符 BOM
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset;
			}
			if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8";
				checked = true;
			}

			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
							// (0x80 - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
				// BOM信息
				// System.out.println(loc + " " + Integer.toHexString(read));
			}

			return charset;
		} finally {
			bis.close();
		}
	}

	/**
	 * 将字符编码转换成UTF-8
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeToUTF8(String str) throws UnsupportedEncodingException {
		return java.net.URLDecoder.decode(str, UTF_8);
	}

	/**
	 * 将字符编码转换成UTF-8
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeToUTF8(String str) throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(str, UTF_8);
	}

	public static void main11(String[] args) throws UnsupportedEncodingException {
		String str = "This is a 中文的 String!";
		System.out.println("str：" + str);

		String gbk = StringUtil.toGBK(str);
		System.out.println("转换成GBK码：" + gbk);
		System.out.println();

		String ascii = StringUtil.toASCII(str);
		System.out.println("转换成US-ASCII：" + ascii);
		System.out.println();

		String iso88591 = StringUtil.toISO_8859_1(str);
		System.out.println("转换成ISO-8859-1码：" + iso88591);
		System.out.println();

		gbk = StringUtil.changeCharset(iso88591, ISO_8859_1, GBK);
		System.out.println("再把ISO-8859-1码的字符串转换成GBK码：" + gbk);
		System.out.println();

		String utf8 = StringUtil.toUTF_8(str);
		System.out.println();
		System.out.println("转换成UTF-8码：" + utf8);

		String utf16be = StringUtil.toUTF_16BE(str);
		System.out.println("转换成UTF-16BE码：" + utf16be);

		gbk = StringUtil.changeCharset(utf16be, UTF_16BE, GBK);
		System.out.println("再把UTF-16BE编码的字符转换成GBK码：" + gbk);
		System.out.println();

		String utf16le = StringUtil.toUTF_16LE(str);
		System.out.println("转换成UTF-16LE码：" + utf16le);

		gbk = StringUtil.changeCharset(utf16le, UTF_16LE, GBK);
		System.out.println("再把UTF-16LE编码的字符串转换成GBK码：" + gbk);
		System.out.println();

		String utf16 = StringUtil.toUTF_16(str);
		System.out.println("转换成UTF-16码：" + utf16);

		String gb2312 = StringUtil.changeCharset(utf16, UTF_16, GB2312);
		System.out.println("再把UTF-16编码的字符串转换成GB2312码：" + gb2312);
	}
    //***********************************************************************************************
	//***********************************************************************************************
	/**
	 * ���ܷ���
	 * @param plainText
	 * @param passWord
	 * @param model ����ΪAES��DES��������Կ���ȱ���һ��
	 * @return
	 */
	public static String encrypt(String plainText, String passWord,
			String model) {
		try {
			// ������תΪ�ֽ�����
			byte[] key = passWord.getBytes("utf-8");
			// ������Կ�Ĺ�����
			SecretKeySpec skeySpec = new SecretKeySpec(key, model);
			// ��ָ���ļ���ģʽ�õ�һ�����ܵĹ�����
			Cipher cipher = Cipher.getInstance(model);
			// ����Կ��ģʽ��ʼ��������
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			// ִ�в���
			byte[] decryptText = cipher.doFinal(plainText.getBytes("utf-8"));
			// תΪָ���ַ�
			return byte2hex(decryptText);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ���ܷ���
	 * @param cipherText
	 * @param passWord
	 * @param model ����ΪAES��DES��������Կ���ȱ���һ��
	 * @return
	 */
	public static String decrypt(String cipherText, String passWord,
			String model) {
		try {
			// ������תΪ�ֽ�����
			byte[] key = passWord.getBytes("utf-8");
			// ������Կ�Ĺ�����
			SecretKeySpec skeySpec = new SecretKeySpec(key, model);
			// ��ָ���ļ���ģʽ�õ�һ�����ܵĹ�����
			Cipher cipher = Cipher.getInstance(model);
			// ����Կ��ģʽ��ʼ��������
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			// ִ�в���
			byte[] plainText = cipher.doFinal(hex2byte(cipherText));
			// תΪָ���ַ�
			return new String(plainText, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 16�����ַ�ת�����ֽ���
	 * @param strhex
	 * @return
	 */
	private static byte[] hex2byte2(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);
		}
		return b;
	}

	/**
	 * �ֽ���ת����16�����ַ�
	 * @param b
	 * @return
	 */
	private static String byte2hex2(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	//***********************************************************************************************
	//***********************************************************************************************

	/**
	 * 封装查询条件
	 * @param val
	 * @return
	 */
	public static String getStringSplit(String[] val){
		StringBuffer sqlStr = new StringBuffer();
		for(String s:val){
			if(StringUtils.isNotBlank(s)){
				sqlStr.append(",");
				sqlStr.append("'");
				sqlStr.append(s.trim());
				sqlStr.append("'");
			}
		}
		return sqlStr.toString().substring(1);
	}
	/**
	 * 字符串首字母变小写
	 */
	public static String getInitialSmall(String str) {
		if(StringUtils.isNotBlank(str)){
			str = str.substring(0, 1).toLowerCase() + str.substring(1);
		}
		return str;
	}
	
	/**
	 * 判断如果字段为空，则返回0
	 */
	public static Integer getIntegerNotNull(Integer t){
		if(t==null){
			return 0;
		}
		return t;
	}
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************


	/**
	 * 获得字符串
	 * 
	 * @param c
	 *            java.sql.Clob
	 * @return 字符串
	 */
	public static String getString1(Clob c) {
		
		StringBuffer s = new StringBuffer();
		if (c != null) {
			try {
				BufferedReader bufferRead = new BufferedReader(c.getCharacterStream());
				try {
					String str;
					while ((str = bufferRead.readLine()) != null) {
						s.append(str);
					}
				} catch (IOException e) {
					ExceptionUtil.getExceptionMessage(e);
				}
			} catch (SQLException e) {
				ExceptionUtil.getExceptionMessage(e);
			}
		}
		return s.toString();
	}

	/**
	 * 获得Clob
	 * 
	 * @param s
	 *            字符串
	 * @return java.sql.Clob
	 */
	public static Clob getClob1(String s) {
		Clob c = null;
		try {
			if (s != null) {
				c = new SerialClob(s.toCharArray());
			}
		} catch (SerialException e) {
			ExceptionUtil.getExceptionMessage(e);
		} catch (SQLException e) {
			ExceptionUtil.getExceptionMessage(e);
		}
		return c;
	}
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************


	
	public static void main466(String[] args) throws Exception {
		//
		String data = "中文";
		//编码
//		String encode = URLEncoder.encode(data,"UTF-8");
//		System.out.println(encode);
		//解码
//		String decode = URLDecoder.decode(encode, "UTF-8");
//		System.out.println(decode);
		
		//手动编码  byte[] --> String
		byte[] bytes = data.getBytes("UTF-8");  // [12,34,56]
		//生成一个字符串  "12,34,56"
		StringBuffer buf = new StringBuffer();
		for(int i = 0 ; i < bytes.length ; i ++){
			buf.append(bytes[i]).append(",");
		}
		//将最后一个“,”删除
		buf.deleteCharAt(buf.lastIndexOf(","));
		String bufData = buf.toString();
		System.out.println(bufData);
		
		
		//手动解码  bufData String -- byte[]
		String[] strs = bufData.split(",");
		//String[] -- byte[]
		byte[] bs = new byte[strs.length];
		for(int i = 0 ; i < strs.length ; i ++){
			bs[i] = new Byte(strs[i]);
		}
		System.out.println(bs);
		System.out.println(new String(bs,"UTF-8"));
	}
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************

    
    public static String getDateStr(String pt) {
        if (pt == null || pt.trim().length() == 0) {
            pt = "yyyy-MM-dd";
        }
        
        SimpleDateFormat fm = new SimpleDateFormat();
        fm.applyPattern(pt);
        return fm.format(new Date());
    }
    
    public static String getDateStr(Object dateObj, String pt) {
        Date date = null;
        if (dateObj instanceof Date) {
            if (dateObj == null) {
                return "";
            }
            date = (Date) dateObj;
        } else {
            if (dateObj == null || dateObj.toString().length() == 0) {
                return "";
            }
            
            java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(dateObj
                    .toString());
            date = new Date(sqlDate.getTime());
        }
        
        if (pt == null || pt.trim().length() == 0) {
            pt = "yyyy-MM-dd";
        }
        
        SimpleDateFormat fm = new SimpleDateFormat();
        fm.applyPattern(pt);
        return fm.format(date);
    }
    
    public static Date getDate(String str, String pt) throws ParseException {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        
        if (pt == null || pt.trim().length() == 0) {
            pt = "yyyy-MM-dd";
        }
        
        SimpleDateFormat fm = new SimpleDateFormat();
        fm.applyPattern(pt);
        return fm.parse(str);
    }
    
    public static java.sql.Timestamp getSqlDate() {
        long dateTime = new Date().getTime();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(dateTime);
        return sqlDate;
    }
    
    public static java.sql.Timestamp getSqlDate(String timeValue) {
        java.sql.Timestamp sqlDate = null;
        Long dateTime = StringUtil.getDateTime(timeValue);
        if (dateTime != null) {
            sqlDate = new java.sql.Timestamp(dateTime.longValue());
        }
        return sqlDate;
    }
    
    public static Long getDateTime(String timeValue) {
        Long dateTime = null;
        
        if (timeValue != null && timeValue.trim().length() > 0) {
            timeValue = rightPadTo(timeValue, "1900-01-01 00:00:00");
            timeValue = timeValue.replace("/", "-");
            
            try {
                Date date = StringUtil
                        .getDate(timeValue, "yyyy-MM-dd HH:mm:ss");
                dateTime = new Long(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        return dateTime;
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> parmsMap = new HashMap<String, String>();
        
        Map<String, String[]> properties = request.getParameterMap();
        Object obj = "";
        String value = "";
        String[] values = null;
        
        for (String key : properties.keySet()) {
            obj = properties.get(key);
            if (null == obj) {
                value = "";
            } else if (obj instanceof String[]) {
                value = "";
                values = (String[]) obj;
                for (int i = 0; i < values.length; i++) {
                    value += "," + values[i];
                }
                value = value.length() > 0 ? value.substring(1) : value;
            } else {
                value = obj.toString();
            }
            
            parmsMap.put(key, value);
        }
        
        return parmsMap;
    }
    
    
    
    
    
    public static String rightPadTo(String src, String dec) {
        String retStr = src;
        int len = src.length();
        if (dec.length() - len > 0) {
            retStr += dec.substring(len);
        }
        return retStr;
    }
    
    
	public static String changeUTF(String str) {
		
		String newStr = null;
		try {
			newStr = new String(str.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newStr;
	}
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************

	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String numberChar = "0123456789";
	public static final String numberChar_1 = "123456789";

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateNumAndLetterStr(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机字符串(只包含数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateNumStr(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
		}
		return sb.toString();
	}
	
	/**
	 * 返回一个定长的随机字符串(只包含数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateNumStr01(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int r=random.nextInt(2);
			sb.append(r);
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLetterStr(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerLetterStr(int length) {
		return generateLetterStr(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperLetterStr(int length) {
		return generateLetterStr(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroStr(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthStr(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroStr(fixdlenth - strNum.length()));
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthStr(int num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroStr(fixdlenth - strNum.length()));
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 
	 * TODO生成不含0的随机数
	 * 
	 * @param length
	 * @return
	 * 
	 *         String
	 */
	public static String generateNumStrNotContain_0(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(numberChar_1.charAt(random.nextInt(numberChar_1.length())));
		}
		return sb.toString();
	}

	/**
	 * 
	 * 获取时间和随机数的字符串
	 * 
	 * @return
	 * 
	 *         String
	 */
	public static String getRandomDateAndNumber() {
		return DateUtil.getTimeStamp() + generateNumStr(5);
	}
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************

	public static final boolean ereg(String pattern, String str)
			throws PatternSyntaxException {
		try {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			return m.find();
		} catch (PatternSyntaxException e) {
			throw e;
		}
	}

	public static final String ereg_replace(String pattern, String newstr,
			String str) throws PatternSyntaxException {
		try {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			return m.replaceAll(newstr);
		} catch (PatternSyntaxException e) {
			throw e;
		}
	}

	public static final Vector splitTags2Vector(String pattern, String str)
			throws PatternSyntaxException {
		Vector vector = new Vector();
		try {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			while (m.find()) {
				vector.add(ereg_replace("(\\[\\#)|(\\#\\])", "", m.group()));
			}
			return vector;
		} catch (PatternSyntaxException e) {
			throw e;
		}
	}

	public static final String[] splitTags(String pattern, String str) {
		try {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			String[] array = new String[m.groupCount()];
			int i = 0;
			while (m.find()) {
				array[i] = ereg_replace("(\\[\\#)|(\\#\\])", "", m.group());
				i++;
			}
			return array;
		} catch (PatternSyntaxException e) {
			throw e;
		}
	}

	public static final Vector regMatchAll2Vector(String pattern, String str)
			throws PatternSyntaxException {
		Vector vector = new Vector();
		try {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			while (m.find()) {
				vector.add(m.group());
			}
			return vector;
		} catch (PatternSyntaxException e) {
			throw e;
		}
	}

	public static final String[] regMatchAll2Array(String pattern, String str)
			throws PatternSyntaxException {
		try {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			String[] array = new String[m.groupCount()];
			int i = 0;
			while (m.find()) {
				array[i] = m.group();
				i++;
			}
			return array;
		} catch (PatternSyntaxException e) {
			throw e;
		}
	}

	public static String escapeDollarBackslash(String original) {
		StringBuffer buffer = new StringBuffer(original.length());
		for (int i = 0; i < original.length(); i++) {
			char c = original.charAt(i);
			if (c == '\\' || c == '$') {
				buffer.append("\\").append(c);
			} else {
				buffer.append(c);
			}
		}
		return buffer.toString();
	}

	public static final String fetchStr(String pattern, String str) {
		String returnValue = null;
		try {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			while (m.find()) {
				returnValue = m.group();
			}
			return returnValue;
		} catch (PatternSyntaxException e) {
			return returnValue;
		}
	}
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************
	//***********************************************************************************************
}
