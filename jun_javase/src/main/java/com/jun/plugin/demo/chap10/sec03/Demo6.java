package com.jun.plugin.demo.chap10.sec03;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Demo6 {

	/**
	 * ����
	 * @throws Exception
	 */
	public static void bufferStream()throws Exception{
		// ������һ����������ֽ�������
		BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream("D://��һ���߽�J2SE����Ƶ��¼2.doc"));
		// ������һ����������ֽ������
		BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream("F://��ֵ���ļ�2.doc"));
		int b=0;
		long startTime=System.currentTimeMillis(); // ��ʼʱ��
		while((b=bufferedInputStream.read())!=-1){
			bufferedOutputStream.write(b);
		}
		bufferedInputStream.close();
		bufferedOutputStream.close();
		long endTime=System.currentTimeMillis();  // ����ʱ��
		System.out.println("���廨�ѵ�ʱ���ǣ�"+(endTime-startTime));
	}
	
	/**
	 * �ǻ���
	 * @throws Exception
	 */
	public static void stream() throws Exception{
		InputStream inputStream=new FileInputStream("D://��һ���߽�J2SE����Ƶ��¼.doc");  // ����һ��������
		OutputStream outputStream=new FileOutputStream("F://��ֵ���ļ�.doc");
		int b=0;
		long startTime=System.currentTimeMillis(); // ��ʼʱ��
		while((b=inputStream.read())!=-1){
			outputStream.write(b);
		}
		inputStream.close();
		outputStream.close();
		long endTime=System.currentTimeMillis();  // ����ʱ��
		System.out.println("�ǻ��廨�ѵ�ʱ���ǣ�"+(endTime-startTime));
	}
	
	public static void main(String[] args)throws Exception {
		stream();
		bufferStream();
	}
}
