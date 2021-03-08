package org.typroject.tyboot.core.foundation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: Encrypt.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:对字符串或文件进行加密/解密
 *  TODO
 * 
 *  Notes:
 *  $Id: Encrypt.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
@SuppressWarnings("restriction")
public class Encrypt {

	private static final Logger logger = LoggerFactory.getLogger(Encrypt.class);

	/**
	 * md5加密算法 TODO.
	 *
	 * @param str
	 * @return
	 */

	public static String IrreversibleMD5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
		//	logger.error(e.getMessage());

			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			//logger.error(e.getMessage());
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	private static final String str = "tyrest";// l2Fzq4hIueXXP8EzgAlSzQ==

	static Key key;

	public Key getKey() {
		return key;
	}

	/**
	 * 根据参数生成 KEY
	 */
	private static void setKey() {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(str.getBytes());

			KeyGenerator kg = null;
			kg = KeyGenerator.getInstance("DES");
			kg.init(secureRandom);
			key = kg.generateKey();
			kg = null;
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		}
	}



	
	public static void main(String[] args) {
		System.out.println(md5ForAuth("a123456","ASDFGHJKLP"));
	}

	/**
	 * 加密以 byte[] 明文输入 ,byte[] 密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private static byte[] encryptByte(byte[] byteS) throws Exception{
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new Exception("Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private static byte[] decryptByte(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 采用 DES 算法加密文件 文件 file 进行加密并保存目标文件 destFile 中
	 * 
	 * @param file
	 *            要加密的文件 如 c:/test/srcFile.txt
	 * @param destFile
	 *            加密后存放的文件名 如 c:/ 加密后文件 .txt
	 */
	public static void encryptFile(String file, String destFile) throws Exception {
		setKey();
		Cipher cipher = Cipher.getInstance("DES");
		// cipher.init(Cipher.ENCRYPT_MODE, getKey());
		cipher.init(Cipher.ENCRYPT_MODE, key);
		InputStream is = new FileInputStream(file);
		OutputStream out = new FileOutputStream(destFile);
		CipherInputStream cis = new CipherInputStream(is, cipher);
		byte[] buffer = new byte[1024];
		int r;
		while ((r = cis.read(buffer)) > 0) {
			out.write(buffer, 0, r);
		}
		cis.close();
		is.close();
		out.close();
	}

	/**
	 * 采用 DES 算法解密文件
	 * 
	 * @param file
	 *            已加密的文件 如 c:/ 加密后文件 .txt *
	 * @param dest
	 *            解密后存放的文件名 如 c:/ test/ 解密后文件 .txt
	 */
	public static void decryptFile(String file, String dest) throws Exception {
		setKey();
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		InputStream is = new FileInputStream(file);
		OutputStream out = new FileOutputStream(dest);
		CipherOutputStream cos = new CipherOutputStream(out, cipher);
		byte[] buffer = new byte[1024];
		int r;
		while ((r = is.read(buffer)) >= 0) {
			cos.write(buffer, 0, r);
		}
		cos.close();
		out.close();
		is.close();
	}

	/**
	 * TODO.生成MD5加密的随机盐
	 * 
	 * @param length
	 * @return
	 */
	public static String generateSalt(int length) {
		char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuilder result = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			result.append(letters[random.nextInt(letters.length - 1)]);
		}
		return result.toString();
	}

	/**
	 * TODO.没有补位的MD5加密,统一使用大写
	 * 
	 * @param source
	 * @return
	 */
	public static String md5WithoutPadding(String source) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(source.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toUpperCase();
	}

	public static String md5ForAuth(String password, String salt) {
		return md5WithoutPadding(md5WithoutPadding(password) + salt);
	}
}