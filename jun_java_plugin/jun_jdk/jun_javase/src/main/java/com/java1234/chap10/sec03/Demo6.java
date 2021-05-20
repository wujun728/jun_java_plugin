package com.java1234.chap10.sec03;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Demo6 {

	/**
	 * 缓冲
	 * @throws Exception
	 */
	public static void bufferStream()throws Exception{
		// 定义了一个带缓冲的字节输入流
		BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream("D://《一脚踹进J2SE》视频笔录2.doc"));
		// 定义了一个带缓冲的字节输出流
		BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream("F://赋值的文件2.doc"));
		int b=0;
		long startTime=System.currentTimeMillis(); // 开始时间
		while((b=bufferedInputStream.read())!=-1){
			bufferedOutputStream.write(b);
		}
		bufferedInputStream.close();
		bufferedOutputStream.close();
		long endTime=System.currentTimeMillis();  // 结束时间
		System.out.println("缓冲花费的时间是："+(endTime-startTime));
	}
	
	/**
	 * 非缓冲
	 * @throws Exception
	 */
	public static void stream() throws Exception{
		InputStream inputStream=new FileInputStream("D://《一脚踹进J2SE》视频笔录.doc");  // 定义一个输入流
		OutputStream outputStream=new FileOutputStream("F://赋值的文件.doc");
		int b=0;
		long startTime=System.currentTimeMillis(); // 开始时间
		while((b=inputStream.read())!=-1){
			outputStream.write(b);
		}
		inputStream.close();
		outputStream.close();
		long endTime=System.currentTimeMillis();  // 结束时间
		System.out.println("非缓冲花费的时间是："+(endTime-startTime));
	}
	
	public static void main(String[] args)throws Exception {
		stream();
		bufferStream();
	}
}
