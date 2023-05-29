package com.jun.plugin.demo;
// DigitalSignDemo.java

import java.io.*;
import java.security.*;
import java.security.interfaces.*;

public class DigitalSignDemo{
	
	public static void generateKey() {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(1024);
			KeyPair kp = kpg.genKeyPair();
			PublicKey pbkey = kp.getPublic();
			PrivateKey prkey = kp.getPrivate();
			
			// ���湫Կ
			FileOutputStream f1 = new FileOutputStream("pubkey.dat");
			ObjectOutputStream b1 = new ObjectOutputStream(f1);
			b1.writeObject(pbkey);

			// ����˽Կ
			FileOutputStream f2 = new FileOutputStream("privatekey.dat");
			ObjectOutputStream b2 = new ObjectOutputStream(f2);
			b2.writeObject(prkey);
		} catch (Exception e) {
		}
	}
	
	public static void sign() throws Exception{
		//��ȡҪǩ������
		FileInputStream f = new FileInputStream("msg.dat");
		int num = f.available();
		byte[] data = new byte[num];
		f.read(data);
		
		// ��ȡ˽Կ
		FileInputStream f2 = new FileInputStream("privatekey.dat");
		ObjectInputStream b = new ObjectInputStream(f2);
		RSAPrivateKey prk = (RSAPrivateKey)b.readObject();
		Signature s = Signature.getInstance("MD5WithRSA");
		s.initSign(prk);
		s.update(data);
		System.out.println("");
		byte[] signeddata = s.sign();
		
		// ��ӡǩ��
		for(int i=0; i<data.length; i++){
			System.out.print(signeddata[i] + ",");
		}
		
		// ����ǩ��
		FileOutputStream f3 = new FileOutputStream("sign.dat");
		f3.write(signeddata);
	}
	
	public static void checkSign() throws Exception{
		FileInputStream f = new FileInputStream("msg.dat");
		int num = f.available();
		byte[] data = new byte[num];
		f.read(data);
		
		// ��ǩ��
		FileInputStream f2 = new FileInputStream("sign.dat");
		int num2 = f2.available();
		byte[] signeddata = new byte[num2];
		f2.read(signeddata);
		
		// ����Կ
		FileInputStream f3 = new FileInputStream("pubkey.dat");
		ObjectInputStream b = new ObjectInputStream(f3);
		RSAPublicKey pbk = (RSAPublicKey)b.readObject();
		
		// ��ȡ����
		Signature s = Signature.getInstance("MD5WithRSA");
		
		//��ʼ��
		s.initVerify(pbk);
		
		// ����ԭʼ���
		s.update(data);
		boolean ok = false;
		try{
			//��ǩ����֤ԭʼ���
			ok = s.verify(signeddata);
			System.out.println(ok);
		}
		catch(SignatureException e){
			System.out.println(e);
		}
		System.out.println("Check Over!");
	}
	
	public static void main(String args[]) throws Exception{
		sign();
		checkSign();
	}
}
