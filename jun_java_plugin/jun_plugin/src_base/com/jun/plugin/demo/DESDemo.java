package com.jun.plugin.demo;

import java.security.*;
import javax.crypto.*;
public class DESDemo {
	public static void main(String[] args) throws Exception {
		// ����
		byte[] plainText = "Hello World!".getBytes();
		// �õ�DES˽Կ
		System.out.println("\nStart generating DES key");
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		keyGen.init(56);
		Key key = keyGen.generateKey();
		System.out.println("Finish generating DES key");
		// �õ�DES cipher ���󣬲���ӡ���㷨���ṩ��
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		System.out.println("\n" + cipher.getProvider().getInfo());
		// ʹ����Կ�����Ľ��м���
		System.out.println("\nStart encryption");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(plainText);
		System.out.println("Finish encryption: ");
		System.out.println(new String(cipherText, "UTF8"));
		// ʹ��ͬһ����Կ�����Ľ��н���
		System.out.println("\nStart decryption");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] newPlainText = cipher.doFinal(cipherText);
		System.out.println("Finish decryption: ");
		System.out.println(new String(newPlainText, "UTF8"));
	}
}
