package com.java1234.chap03.sec18;

public class Singleton2 {

	/**
	 * 构造方法私有
	 */
	private Singleton2(){
		
	}
	
	/**
	 * 懒汉式单例实现  在第一次调用的时候实例化
	 */
	private static Singleton2 single;
	
	/**
	 * 工厂
	 */
	public synchronized static Singleton2 getInstance(){
		if(single==null){
			// 第一次调用的时候实例化
			System.out.println("第一次调用的时候实例化");
			single=new Singleton2();
		}
		return single;
	}
}
