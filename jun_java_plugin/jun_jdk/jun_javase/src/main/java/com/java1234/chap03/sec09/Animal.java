package com.java1234.chap03.sec09;

/**
 * 动物类
 * @author caofeng
 *
 */
public class Animal {

	// 属性姓名
	private String name;
	// 属性年龄
	private int age;
	
	/**
	 * 无参父类构造方法
	 */
	public Animal(){
		System.out.println("无参父类构造方法");
	}
	
	/**
	 * 有参父类构造方法
	 * @param name 名字
	 * @param age  年龄
	 */
	public Animal(String name,int age){
		System.out.println("有参父类构造方法");
		this.name=name;
		this.age=age;
	}
	
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	// 方法say
	public void say(){
		System.out.println("我是一个动物，我叫："+this.getName()+"我的年龄是："+this.getAge());
	}
	
	
}
