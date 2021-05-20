package com.java1234.chap03.sec18;

public class Singleton {

	/**
	 * 构造方法私有
	 */
	private Singleton(){
		
	}
	
	private static final Singleton single=new Singleton();
	
	/**
	 * 获取实例
	 * @return
	 */
	public static Singleton getInstance(){
		return single;
	}
}
