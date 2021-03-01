package com.java1234.chap03.sec03;

public class People {

	/**
	 * 最简单的一个方法定义
	 */
	void speak(){
		System.out.println("我叫张三");
	}
	
	public static void main(String[] args) {
		People zhangsan=new People();
		zhangsan.speak();
	}
}
