package com.java1234.chap03.sec04;

public class People2 {


	// String类属性默认值是Null
	private String name;
	// int类属性默认值是0
	private int age;
	
	/**
	 * 默认构造方法  多行备注快捷方式  ctrl+shift+/
	 */
	People2(){
		System.out.println("默认构造方法！");
	}
	
	/** 
	 * 有参数的构造方法 构造方法的重载
	 */
	People2(String name,int age){
		this();
		this.name=name;
		this.age=age;
		System.out.println("有参数的构造方法！");
	}
	
	public void say(){
		System.out.println("我叫："+name+"，我今年："+age+"岁了！");
	}
	
	public static void main(String[] args) {
		// People people=new People();
		People2 people2=new People2("张三",23);
		people2.say();
	}
}
