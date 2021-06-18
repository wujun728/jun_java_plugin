package com.java1234.chap03.sec04;

public class People {

	// String类属性默认值是Null
	private String name;
	// int类属性默认值是0
	private int age;
	
	/**
	 * 默认构造方法  多行备注快捷方式  ctrl+shift+/
	 */
	public People(){
		System.out.println("默认构造方法！");
	}
	
	/** 
	 * 有参数的构造方法 构造方法的重载
	 */
	People(String name2,int age2){
		name=name2;
		age=age2;
		System.out.println("有参数的构造方法！");
	}
	
	public void say(){
		System.out.println("我叫："+name+"，我今年："+age+"岁了！");
	}
	
	public static void main(String[] args) {
		// People people=new People();
		 People people2=new People("张三",23);
		people2.say();
	}
}
