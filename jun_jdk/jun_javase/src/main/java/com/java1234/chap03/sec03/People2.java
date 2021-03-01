package com.java1234.chap03.sec03;

public class People2 {

	// 形参，入参
	void speak(String name){
		System.out.println("我叫"+name);
	}
	
	public static void main(String[] args) {
		People2 zhangsan=new People2();
		zhangsan.speak("张三");
	}
}
