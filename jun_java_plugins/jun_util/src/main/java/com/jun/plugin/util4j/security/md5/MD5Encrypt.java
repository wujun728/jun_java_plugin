package com.jun.plugin.util4j.security.md5;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * MD5加密工具类
 * @author Administrator
 */
public class MD5Encrypt {
	public MD5Encrypt() {
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String byteToNumString(byte b) {
		int _b = b;
		if (_b < 0) {
			_b = 256 + _b;
		}
		return String.valueOf(_b);
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 转换字节数组为十六进制字符串
	 * 
	 * @param b
	 *            字节数组
	 * @return 十六进制字符串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(byteToHexString(b[i]));
		}
		return new String(sb);
	}

	/**
	 * 转换字节数组为十进制字符串
	 * 
	 * @param b
	 *            字节数组
	 * @return 十进制字符串
	 */
	public static String byteArrayToNumString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(byteToNumString(b[i]));
		}
		return new String(sb);
	}

	/**
	 * 把字符串加密成十六进制表示形式
	 * 
	 * @param str
	 *            字符串
	 * @return 十六进制表示的字符串
	 */
	public static String MD5EncodeToHex(String str) {
		String reStr = null;
		try {
			reStr = new String(str);
			MessageDigest md = MessageDigest.getInstance("MD5");
			reStr = byteArrayToHexString(md.digest(reStr.getBytes()));
		} catch (Exception e) {
		}
		return reStr;
	}
	
	/**
	 * 把字符串加密成十六进制表示形式
	 * 
	 * @param str
	 *            字符串
	 * @return 十六进制表示的字符串
	 */
	public static String MD5EncodeToHex(String str,Charset c) {
		String reStr = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			reStr = byteArrayToHexString(md.digest(str.getBytes(c)));
		} catch (Exception e) {
		}
		return reStr;
	}

	/**
	 * 把字符串加密成十进制表示形式
	 * 
	 * @param str
	 *            字符串
	 * @return 十进制表示的字符串
	 */
	public static String MD5EncodeToNum(String str) {
		String reStr = null;
		try {
			reStr = new String(str);
			MessageDigest md = MessageDigest.getInstance("MD5");
			reStr = byteArrayToNumString(md.digest(reStr.getBytes()));
		} catch (Exception e) {
		}
		return reStr;
	}

	public static void main(String[] args) {
		System.out.println(MD5Encrypt.MD5EncodeToHex("ggcs123456").toUpperCase());
		System.out.println(MD5Encrypt.MD5EncodeToHex("公告测试123456").toUpperCase());
	}
}
