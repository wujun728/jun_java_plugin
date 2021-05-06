package com.java1234.chap03.sec03;

public class Demo02 {

	void fun1(){
		System.out.println("这是一个普通方法");
	}
	
	static void fun2(){
		System.out.println("这是一个静态方法");
	}
	
	public static void main(String[] args) {
		Demo02 demo=new Demo02();
		// 调用普通方法，对象.方法
		demo.fun1();
		
		// 调用静态方法  类名.方法
		Demo02.fun2();
		demo.fun2();
	}
}
