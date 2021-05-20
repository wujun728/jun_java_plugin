package com.java1234.chap03.sec18;

public class Test {

	public static void main(String[] args) {
		Singleton single=Singleton.getInstance();
		Singleton single2=Singleton.getInstance();
		System.out.println(single==single2);
		
		Singleton2 single3=Singleton2.getInstance();
		Singleton2 single4=Singleton2.getInstance();
		System.out.println(single3==single4);
	}
}
