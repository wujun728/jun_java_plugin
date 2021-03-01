package com.java1234.chap02.sec04;

public class Demo3 {

	public static void main(String[] args) {
		int a=1;
		// a++ 表示先做赋值操作，后自增
		/*int b=a++;
		System.out.println("b="+b);
		System.out.println("a="+a);*/
		
		// a++ 表示先自增，后做赋值操作
		int b=++a;
		System.out.println("b="+b);
		System.out.println("a="+a);
	}
}
