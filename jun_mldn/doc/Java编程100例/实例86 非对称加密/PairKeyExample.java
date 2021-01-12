/*
 * Copyright (c) 2004 ???. All Rights Reserved.
 *
 * Version 1.0 , Created on 2004-3-10
 *
 */

/**
 * @author Devon
 */

import java.security.*;
import java.security.spec.*;
import javax.crypto.*;

public class PairKeyExample {

	public static void main(String argv[]) {
		try {
			String algorithm = "RSA"; //定义加密算法,可用 DES,DESede,Blowfish
			String message = "张三，你好，我是李四";

			//产生张三的密钥对(keyPairZhang)
			KeyPairGenerator keyGeneratorZhang =
				KeyPairGenerator.getInstance(algorithm);  //指定采用的算法
			keyGeneratorZhang.initialize(1024); //指定密钥长度为1024位
			KeyPair keyPairZhang = keyGeneratorZhang.generateKeyPair(); //产生密钥对
			System.out.println("生成张三的公钥对");

			// 张三生成公钥(publicKeyZhang)并发送给李四,这里发送的是公钥的数组字节
			byte[] publicKeyZhangEncode = keyPairZhang.getPublic().getEncoded();

			//通过网络或磁盘等方式,把公钥编码传送给李四
			//李四接收到张三编码后的公钥,将其解码
			KeyFactory keyFacoryLi = KeyFactory.getInstance(algorithm);  //得到KeyFactory对象
			X509EncodedKeySpec x509KeySpec =
				new X509EncodedKeySpec(publicKeyZhangEncode);  //公钥采用X.509编码
			PublicKey publicKeyZhang = keyFacoryLi.generatePublic(x509KeySpec); //将公钥的KeySpec对象转换为公钥
			System.out.println("李四成功解码,得到张三的公钥");

			//李四用张三的公钥加密信息，并发送给李四
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  //得到Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, publicKeyZhang);  //用张三的公钥初始化Cipher对象
			byte[] cipherMessage = cipher.doFinal(message.getBytes());  //得到加密信息
			System.out.println("加密后信息：" + new String(cipherMessage));
			System.out.println("加密完成，发送给李四...");

			//张三用自己的私钥解密从李四处收到的信息
			cipher.init(Cipher.DECRYPT_MODE, keyPairZhang.getPrivate()); //张三用其私钥初始化Cipher对象
			byte[] originalMessage = cipher.doFinal(cipherMessage);  //得到解密后信息
			System.out.println("张三收到信息，解密后为：" + new String(originalMessage));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
