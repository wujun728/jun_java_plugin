package com.java1234.chap03.sec02;

/**
 * Person类  文件名和类名必须一致
 * @author user
 *
 */
public class Person {

	String name; // 在类中，定义一个姓名name字符串属性，可以存放字符串  "张三"
	int age; // 在类中 ，定义一个年龄age属性
	
	/**
	 * 定义一个方法
	 */
	public void speak(){
		System.out.println("我叫"+name+" 我今年"+age);
	}
	
	public static void main(String[] args) {
		// 定义一个Person类的对象zhangsan
		Person zhangsan; 
		// 实例化对象
		zhangsan=new Person();
		zhangsan.name="张三";  // 给对象的name属性赋值
		zhangsan.age=1; // 给对象的age属性赋值
		zhangsan.speak(); // 调用对象的方法
	}
}
