package com.java1234.chap07.sec04;

import java.lang.reflect.Field;

public class Test2 {

	public static void main(String[] args) {
		Class<?> c=null;
		try {
			c=Class.forName("com.java1234.chap07.sec04.Student");
			System.out.println(c.getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Object obj=c.newInstance();
			Field nameField=c.getDeclaredField("name");
			nameField.setAccessible(true); // 设置可以访问 包括私有属性
			nameField.set(obj, "小锋");
			System.out.println("name:"+nameField.get(obj));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
