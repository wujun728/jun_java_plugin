package com.jun.plugin.demo.chap03.sec18;

public class Singleton1 {

	/**
	 * ���췽��˽��
	 */
	private Singleton1(){
		
	}
	
	/**
	 * ����ʽ����ʵ��
	 */
	private static final Singleton1 single1=new Singleton1();
	
	/**
	 * ��̬������ʽ
	 * 
	 */
	public static Singleton1 getInstance(){
		return single1;
	}
	
}
