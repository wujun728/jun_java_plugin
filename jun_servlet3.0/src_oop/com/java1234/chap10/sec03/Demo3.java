package com.java1234.chap10.sec03;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Demo3 {

	public static void main(String[] args) throws Exception {
		File file=new File("C://测试文件.txt");
		InputStream inputStream=new FileInputStream(file);
		int fileLength=(int) file.length();
		byte b[]=new byte[fileLength];
		int len=0;
		int temp=0;
		while((temp=inputStream.read())!=-1){
			b[len++]=(byte) temp;
		}
		inputStream.close();
		System.out.println("读取的内容是："+new String(b));
	}
}
