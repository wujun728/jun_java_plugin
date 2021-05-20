/*
 * Copyright (c) 2004 ???. All Rights Reserved.
 *
 * Version 1.0 , Created on 2004-3-10
 *
 */

/**
 * @author Devon
 */

 /**
  *  如何产生和保存密钥
  */

import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import java.io.*;

public class KeyGeneratorExample {
	public static void main(String[] args)	{
		try{
		   //产生单钥加密的密钥(myKey)
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede"); //采用DESede算法
			keyGenerator.init(168); //选择DESede算法,密钥长度为112位或168位
			Key myKey = keyGenerator.generateKey(); //生成密钥
			System.out.println("得到单钥加密密钥");

			//产生双钥的密钥对(keyPair)
			KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA"); //采用RSA算法
			keyPairGenerator.initialize(1024); //指定密钥长度为1024位
			KeyPair keyPair = keyPairGenerator.generateKeyPair();  //生成密钥对
			System.out.println("生成张三的公钥对");

			//保存公钥的字节数组
			File f = new File("publicKey.dat");  //保存公钥到文件publicKey.dat
			FileOutputStream fout = new FileOutputStream(f);
			fout.write(keyPair.getPublic().getEncoded()); //得到公钥的字节数组
			fout.close();  //关闭文件输出流
			System.out.println("保存公钥到文件: "+f.getAbsolutePath());

			//用Java对象序列化保存私钥,通常应对私钥加密后再保存
			ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream("privateKey.dat")); //保存私钥到文件privateKey.dat
			oout.writeObject(keyPair.getPrivate()); //序列化私钥
			oout.close();  //关闭输出流
			System.err.println("保存私钥到: privateKey.dat");

			//从文件中得到公钥编码的字节数组
			FileInputStream fin = new FileInputStream("publicKey.dat");  //打天publicKey.dat
			ByteArrayOutputStream baout = new ByteArrayOutputStream();  //用于写入文件的字节流
			int aByte = 0;
			while ((aByte = fin.read())!= -1)  //从文件读取一个字节
			{
			   baout.write(aByte);   //写入一个字节
			}
			fin.close();  //关闭文件输入流
			byte[] keyBytes = baout.toByteArray();  //得到公钥的字节数组
			baout.close();  //关闭字节数组输出流

			//从字节数组解码公钥
			X509EncodedKeySpec x509KeySpec =new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");  //指定算法RSA,得到一个KeyFactory的实例
			PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);  //解码公钥
            System.out.println("从文件中成功得到公钥");
		}catch (Exception ex){ex.printStackTrace();
		}
	}
}