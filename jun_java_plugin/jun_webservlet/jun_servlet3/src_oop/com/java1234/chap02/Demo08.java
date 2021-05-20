package com.java1234.chap02;

public class Demo08 {

	public static void main(String[] args) {
		int a1=1;
		// a1++ ；表示先做赋值操作，后自增
		int b1=a1++;
		System.out.println("b1="+b1);
		System.out.println("a1="+a1);
		
		System.out.println("================");
		
		int a2=1;
		// ++a2 ；表示先自增，然后做赋值操作
		int b2=++a2;
		System.out.println("b2="+b2);
		System.out.println("a2="+a2);
	}
}
