package com.java1234.chap03.sec18;

public class Singleton2 {

	/**
	 * 构造方法私有
	 */
	private Singleton2(){
		
	}
	
	private static Singleton2 single=null;
	
	/**
	 * 获取实例
	 * @return
	 */
	public synchronized static Singleton2 getInstance(){
		if(single==null){
			System.out.println("第一次调用");
			single=new Singleton2();
		}
		return single;
	}
}
