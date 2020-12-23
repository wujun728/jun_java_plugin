package com.java1234.chap10.sec04;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class Demo1 {

	public static void main(String[] args) throws Exception {
		File file=new File("D://测试文件.txt");
		Reader reader=new FileReader(file);
		char c[]=new char[1024]; // 字符数组
		int len=reader.read(c);
		reader.close();  // 关闭输入流
		System.out.println("读取的内容是："+new String(c,0,len));
	}
}
