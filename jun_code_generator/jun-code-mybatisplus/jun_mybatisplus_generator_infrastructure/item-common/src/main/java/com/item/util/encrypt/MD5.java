package com.item.util.encrypt;

import java.security.MessageDigest;

/**
 * MD5通用类
 * 
 * @author flying-cattle
 * @since 2019.04.25
 * @version 1.0.0_1
 * 
 */
public class MD5 {
	public static String encrypt(String txt) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(txt.getBytes("GBK")); 
			StringBuffer buf = new StringBuffer();
			for (byte b : md.digest()) {
				buf.append(String.format("%02x", b & 0xff));
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
	public static void main(String[] args) {
		System.out.println(MD5.encrypt("123"));
	}
}
