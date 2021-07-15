package com.jun.plugin.jdbc.test;

import java.util.Random;

public class MyThreadLocal {
	//声明一个唯一的ThreadLocal
	private static ThreadLocal<Object> tl = new ThreadLocal<Object>();
	public static Object getObject(){
		//先从tl中读取数据
		Object o = tl.get();// 如果没有保存过，map.get(Thread.currentThread());
		if(o==null){
			//生成一个随机
			o = new Random().nextInt(100);
			//放到tl
			tl.set(o);
		}
		return o;
	}
	public static void remove(){
		tl.remove();
	}
}
