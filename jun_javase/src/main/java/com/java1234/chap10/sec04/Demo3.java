package com.java1234.chap10.sec04;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class Demo3 {

	public static void main(String[] args) throws Exception {
		File file=new File("D://测试文件.txt");
		Writer out=new FileWriter(file);
		String str="我爱中华";
		out.write(str);  // 将字符串写入输出流
		out.close();  // 关闭输出流
	}
}
