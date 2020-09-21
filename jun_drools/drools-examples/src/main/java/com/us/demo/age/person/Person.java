package com.us.demo.age.person;

/**
 * The Class Person.
 *
 * @Project: drools-test01
 * @Title: Person.java
 * @Package: com.us.demo.age.pojo
 * @author: sean
 * @date: 2016
 * @version: V1.0
 */
public class Person {
	private String name;
	private int age;
	private String desc;
	
	public Person(String name,int age){
		this.name=name;
		this.age=age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String toString(){
		return "[name="+name+",age:"+age+",desc"+desc+"]";
	}
	
}
