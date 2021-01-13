package com.java1234.chap07.sec03;

public class Student {

	private String name;
	private Integer age;
	
	
	
	public Student(String name) {
		super();
		this.name = name;
	}
	public Student(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public void say(){
		System.out.println("ÎÒµÄĞÕÃû£º"+name);
	}
	
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + "]";
	}
}
