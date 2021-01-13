package com.java1234.chap07.sec02;

import java.lang.reflect.Constructor;

public class Test4 {

	public static void main(String[] args) {
		Class<?> c=null;
		try {
			c=Class.forName("com.java1234.chap07.sec01.Student");
			System.out.println(c.getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Student s=null;
		Constructor<?>[] cons=c.getConstructors();
		try {
			s=(Student) cons[0].newInstance("Ð¡·æ",28);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(s);
	}
}
