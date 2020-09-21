package com.baijob.commonTools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.util.BASE64EncoderStream;

/**
 * 安全相关的工具类，包括各种加密算法
 * @author xiaoleilu
 *
 */
public class SecureUtil {
	private static Logger logger = LoggerFactory.getLogger(SecureUtil.class);
	
	/**
	 * 获得指定字符串的MD5码
	 * @param key 字符串
	 * @return MD5
	 */
	public static String md5(String key) {
		return digest(ALGORITHM.MD5, key);
	}
	
	public static String sha1(String key) {
		return digest(ALGORITHM.SHA1, key);
	}
	
	/**
	 * 对给定的byte数组做base64编码
	 * @param bytes byte数组
	 * @return base64编码
	 */
	public static String base64Encode(byte[] bytes){
		return new String(BASE64EncoderStream.encode(bytes));
	}
	
	public static long crc32(String key) {
		CRC32 crc32 = new CRC32();
		crc32.update(key.getBytes());
		return crc32.getValue();
	}
	
	/**
	 * 计算指定加密算法后生成的结果
	 * @param algorithm 加密算法枚举
	 * @param key 字符串
	 * @return 加密后的结果
	 */
	private static String digest(ALGORITHM algorithm, String key) {
		MessageDigest instance = null;
		try {
			instance = MessageDigest.getInstance(algorithm.toString());
		} catch (NoSuchAlgorithmException e) {
			logger.error("No such Algorit!", e);
			return null;
		}
		if(instance != null) {
			instance.update(key.getBytes());
			return new String(BASE64EncoderStream.encode(instance.digest()));
		}
		return null;
	}
	
	/**
	 * 加密算法枚举
	 * @author xiaoleilu
	 *
	 */
	enum ALGORITHM{
		MD5, SHA1;
	}
}
