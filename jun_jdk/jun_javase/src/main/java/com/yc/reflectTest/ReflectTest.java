package com.yc.reflectTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {
	public static void main(String[] args) {
		try {
			Class cls=Class.forName("com.yc.reflectTest.User");
			Field[] field=cls.getDeclaredFields();
			for(Field f:field){
				System.out.println(f);
			}
			System.out.println("------------");
			Method[] method=cls.getDeclaredMethods();
			for(Method m:method){
				System.out.println(m);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
