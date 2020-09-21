/*   
 * Project: OSMP
 * FileName: Md5Encode.java
 * version: V1.0
 */
package com.osmp.utils.base;

import java.security.MessageDigest;

/**
 * MD5密码加密
 * 
 * @author wangqiang
 * 
 */
public class Md5Encode {

	// 十六进制下数字到字符的映射数组
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/**
	 * 把str加密
	 * 
	 * @param str
	 * @return
	 */
	public static String generatePassword(String str) {
		return encodeByMD5(str);
	}

	/** 对字符串进行MD5加密 */
	public static String encodeByMD5(String originString) {
		try {
			// 创建具有指定算法名称的信息摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
			byte[] results = md.digest(originString.getBytes());
			// 将得到的字节数组变成字符串返回
			String resultString = byteArrayToHexString(results);
			return resultString.toUpperCase();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * 转换字节数组为十六进制字符串
	 * 
	 * @param 字节数组
	 * @return 十六进制字符串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/** 将一个字节转化成十六进制形式的字符串 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static void main(String[] args) {
		String pass = Md5Encode.encodeByMD5("chenbenhui");
		System.out.println(pass.toUpperCase());
	}

}
