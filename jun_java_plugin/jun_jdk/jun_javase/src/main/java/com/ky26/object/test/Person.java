package com.ky26.object.test;

public class Person {
	private String name;
	private int age;
	Person(String name,int age){
		this.name=name;
		this.age=age;
	}
	
	void setName(String name){
		this.name=name;
	}
	String getName(){
		return name;
	}
	void setAge(int age){
		this.age=age;
	}
	int getAge(){
		return age;
	}
	void show(){
		System.out.println(name+","+age);
	}
	
}
