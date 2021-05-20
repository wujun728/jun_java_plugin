package com.jun.plugin.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyThreadMap {
	private static Map<Thread,Object> map = new HashMap<Thread, Object>();
	
	public static Object getObj(){
		Thread thread = Thread.currentThread();//以Thread的引用来保存数据当做key
		Object o = map.get(thread);
		if(o==null){
			o = new Random().nextInt(1000);
			//放到map
			map.put(thread,o);
		}
		return o;
	}
	public static void remove(){
		map.remove(Thread.currentThread());//
	}
}
