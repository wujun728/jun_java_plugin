package com.java1234.chap10.sec03;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Demo5 {

	public static void main(String[] args) throws Exception {
		File file=new File("C://测试文件.txt");
		OutputStream out=new FileOutputStream(file,true);
		String str="你好，我好，大家好 ，爱生活，爱拉芳322好啊";
		byte b[]=str.getBytes();
		out.write(b);
		out.close();
	}
}
