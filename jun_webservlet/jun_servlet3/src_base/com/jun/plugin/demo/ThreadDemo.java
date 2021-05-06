package com.jun.plugin.demo;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * 
 */
/**
 * @author ZhangHao
 * @date 2013-3-20
 */
public class ThreadDemo extends Thread {
	private int start = 0;
	private int end = 10;

	public ThreadDemo(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public void run() {
		while (end >= start) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(end);// 这里改成处理方法，现在只是打印表示一下
			end--;
		}
	}

	public static void RandomCard() {
		Thread thread = new ThreadDemo(0, 9);
		Thread thread1 = new ThreadDemo(10, 19);
		Thread thread2 = new ThreadDemo(20, 29);
		Thread thread3 = new ThreadDemo(30, 39);
		Thread thread4 = new ThreadDemo(40, 53);
		thread.start();
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}

	public static void TestBigDecimal() {
		BigDecimal b1 = new BigDecimal(1258.00).setScale(2);
		System.out.println(b1.equals(new BigDecimal("1258.00").setScale(2)));
	}

	public static void TestBufferOS() throws IOException {
		File file = new File("c:\\1.txt");
		BufferedOutputStream out = new BufferedOutputStream(new  FileOutputStream(file));
		OutputStreamWriter ow = new OutputStreamWriter(out);
		ow.write("fffffffffffffffffffffffffff");
		System.out.println("ffffff"); 
		
/*			System.out.println(args[0]);
		out.write(args[0].getBytes());
		out.close();*/
		ow.close();
	}
	
	public static void TestCharSet() {
		
		Charset charset = Charset.forName("UTF-8");
		CharsetEncoder encoder = charset.newEncoder();
		//encoder.encode(((ByteBuffer.wrap("张浩".getBytes())).asCharBuffer());
		System.out.println(encoder.canEncode("张"));
	}
	
	public static void main(String[] args) throws IOException {
//		RandomCard();
//		TestBigDecimal();
//		TestBufferOS();
//		TestCharSet();
	}
}
