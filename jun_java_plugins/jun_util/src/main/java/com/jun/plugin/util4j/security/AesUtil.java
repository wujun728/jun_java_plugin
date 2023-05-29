package com.jun.plugin.util4j.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * 编码工具类
 * 1.将byte[]转为各种进制的字符串
 * 2.base 64 encode
 * 3.base 64 decode
 * 4.获取byte[]的md5值
 * 5.获取字符串md5值
 * 6.结合base64实现md5加密
 * 7.AES加密
 * 8.AES加密为base 64 code
 * 9.AES解密
 * 10.将base 64 code AES解密
 */
public class AesUtil extends SecurityUtil{
	
	/**
	 * AES加密
	 * @param content 待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static byte[] AesEncryptToBytes(byte[] content,byte[] encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(encryptKey);
		kgen.init(128,secureRandom);
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		return cipher.doFinal(content);
	}
	
	/**
	 * AES解密
	 * @param encryptBytes 待解密的byte[]
	 * @param decryptKey 解密密钥
	 * @return 解密后的
	 * @throws Exception
	 */
	public static byte[] AesDecryptByBytes(byte[] encryptBytes,byte[] decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(decryptKey);
		kgen.init(128,secureRandom);
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		return decryptBytes;
	}

	/**
	 * AES加密为base 64 code
	 * @param content 待加密的内容
	 * @param encryptKey 加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String AesEncryptToBase64Str(byte[] content,byte[] encryptKey) throws Exception {
		return Base64Encode(AesEncryptToBytes(content, encryptKey));
	}
	
	/**
	 * 将base 64 code AES解密
	 * @param encryptStr 待解密的base 64 code
	 * @param decryptKey 解密密钥
	 * @return 解密后的
	 * @throws Exception
	 */
	public static byte[] AesDecryptByBase64Str(String base64String,byte[] decryptKey) throws Exception {
		byte[] content=Base64Decode(base64String);
		return AesDecryptByBytes(content, decryptKey);
	}

	public static void main(String[] args) throws Exception {
		String content = "上海晋展";
		System.out.println("加密前：" + content);
	
		String key = "cdos";
		System.out.println("加密密钥和解密密钥：" + key);
		
		String encrypt = AesEncryptToBase64Str(content.getBytes(), key.getBytes());
		System.out.println("加密后：" + encrypt);
		
		String decrypt = new String(AesDecryptByBase64Str(encrypt, key.getBytes()));
		System.out.println("解密后：" + decrypt);
	}
}
