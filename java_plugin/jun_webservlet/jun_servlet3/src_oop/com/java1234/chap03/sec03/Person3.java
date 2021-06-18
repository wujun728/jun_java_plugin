package com.java1234.chap03.sec03;

public class Person3 {

	/**
	 * 
	 * @param name 姓名参数
	 * @param age 年龄参数
	 */
	void speak(String name,int age){
		System.out.println("我是"+name+",我今年"+age+"岁了");
	}
	
	public static void main(String[] args) {
		Person3 zhangsan=new Person3();
		zhangsan.speak("张三",23);
	}
}
