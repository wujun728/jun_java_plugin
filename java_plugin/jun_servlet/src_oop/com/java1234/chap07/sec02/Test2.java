package com.java1234.chap07.sec02;

public class Test2 {

	public static void main(String[] args) {
		try {
			Class<?> c=Class.forName("com.java1234.chap07.sec01.Student");
			System.out.println(c.getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
