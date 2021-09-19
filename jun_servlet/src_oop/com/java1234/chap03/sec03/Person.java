package com.java1234.chap03.sec03;

public class Person {

	void speak(){
		System.out.println("我是张三");
	}
	
	public static void main(String[] args) {
		Person person=new Person();
		person.speak();
	}
}
