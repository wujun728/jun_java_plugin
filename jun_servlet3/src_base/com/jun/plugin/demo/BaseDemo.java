package com.jun.plugin.demo;

import org.junit.Test;

public class BaseDemo {
	@Test
	public void testThreadLocal() throws Exception{
		Object o1 = MyThreadMap.getObj();//main线程调用getobject,
		Object o2 = MyThreadMap.getObj();//main线程又去调用getObject
		System.err.println("o1:"+o1+",o2:"+o2);//一样
		
		Thread th = new Thread(){
			public void run() {
				Object o4 = MyThreadMap.getObj();
				Object o5 = MyThreadMap.getObj();
				System.err.println("o4 is:"+o4+","+o5);
				aa("Thread-1");
			};
		};
		th.setName("main");
		th.start();
		Thread.sleep(1000);
		aa("main");//main线程
	}
	
	
	public void aa(String name){
		Object o3 = MyThreadMap.getObj();//mmain
		System.err.println(name+" o3 is:"+o3);
	}
	
	
	
	public static void main(String[] args) {
		//声明Map<Object key,Object value>
		//Object是值，key是当前线程的引用＝Thread.currentThread();
		ThreadLocal<Object> tl = new ThreadLocal<Object>();
		//保存数据
		tl.set("Helllo");
		//获取数据
		Object val = tl.get();
		System.err.println(val);
	}
}
