package com.jun.plugin.demo.chap10.sec03;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Demo3 {

	public static void main(String[] args) throws Exception {
		File file=new File("D://�����ļ�.txt");
		InputStream inputStream=new FileInputStream(file);  // ʵ����FileInputStream
		int fileLength=(int)file.length();
		byte b[]=new byte[fileLength];
		int temp=0;
		int len=0;
		while((temp=inputStream.read())!=-1){
			// һ���ֽ�һ���ֽڶ�ȡ���ŵ�b�ֽ�������
			b[len++]=(byte)temp;
		}
		inputStream.close(); // �ر�������
		System.out.println("��ȡ�������ǣ�"+new String(b));
	}
}
