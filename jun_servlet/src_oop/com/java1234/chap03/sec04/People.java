package com.java1234.chap03.sec04;

/**
 * 人类
 * @author user
 *
 */
public class People {

	// 定义属性 
	private String name; // 实例化对象的时候，默认值是null
	private int age; // 实例化对象的时候，默认值是0
	
	/**
	 * 默认的构造方法
	 */
	public People(){
		System.out.println("默认构造方法");
	}
	
	/**
	 * 有参数的构造方法
	 * @param name2
	 * @param age2
	 */
	People(String name,int age){
		this();// 调用无参数的构造方法
		System.out.println("调用的是有参数的构造方法");
		this.name=name;
		this.age=age;
	}
	
	
	public void say(){
		System.out.println("我叫："+name+"，我今年："+age);
	}
	
	public static void main(String[] args) {
		// People zhangsan=new People();
		People zhangsan=new People("张三",20);
		zhangsan.say();
	}
}
