package com.java1234.chap10.sec03;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Demo5 {

	public static void main(String[] args) throws Exception {
		File file=new File("D://测试文件.txt");
		OutputStream out=new FileOutputStream(file,true);
		String str="你好，我好，大家好，Java好2321312312";
		byte b[]=str.getBytes();
		out.write(b); //  将b字节数组写入到输出流
		out.close();  // 关闭输出流
	}
}
