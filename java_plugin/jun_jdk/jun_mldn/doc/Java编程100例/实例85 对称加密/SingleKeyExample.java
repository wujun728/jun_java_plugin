/*
 * Copyright (c) 2004 ???. All Rights Reserved.
 */

/*
 * @(#)DESExample.java 1.0 04/03/10
 */


import java.security.*;
import javax.crypto.*;

/**
 * 本例解释如何利用DES私钥加密算法加解密
 *
 * @author Devon
 * @version 1.0 04/03/10
 */
public class SingleKeyExample {

	public static void main(String[] args) {
		try {
			String algorithm = "DES"; //定义加密算法,可用 DES,DESede,Blowfish
			String message = "Hello World. 这是待加密的信息";

			// 生成个DES密钥
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(56); //选择DES算法,密钥长度必须为56位
			Key key = keyGenerator.generateKey(); //生成密钥

			// 生成Cipher对象
			Cipher cipher = Cipher.getInstance("DES");

			//用密钥加密明文(message),生成密文(cipherText)
			cipher.init(Cipher.ENCRYPT_MODE, key);  //操作模式为加密(Cipher.ENCRYPT_MODE),key为密钥
			byte[] cipherText = cipher.doFinal(message.getBytes());  //得到加密后的字节数组
			System.out.println("加密后的信息: " + new String(cipherText));

			//用密钥加密明文(plainText),生成密文(cipherByte)
			cipher.init(Cipher.DECRYPT_MODE, key);  //操作模式为解密,key为密钥
			byte[] sourceText = cipher.doFinal(cipherText); //获得解密后字节数组
			System.out.println("解密后的信息: " + new String(sourceText));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
