package com.java1234.chap03.sec03;

public class Demo2 {

	void fun1(){
		System.out.println("这是一个普通方法");
	}
	
	static void fun2(){
		System.out.println("这是一个静态方法");
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Demo2 demo2=new Demo2();
		// 调用普通方法  对象.方法
		demo2.fun1();
		
		// 调用静态方法  类名.方法
		Demo2.fun2();
		// 调用静态方法  对象.方法
		demo2.fun2();
	}
}
