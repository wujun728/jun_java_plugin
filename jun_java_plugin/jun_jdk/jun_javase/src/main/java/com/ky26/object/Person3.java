package com.ky26.object;

import java.util.Arrays;

public class Person3 {
	String name;
	int age;
	static String contry;
	static{
		contry="cq";
		System.out.println("我是静态区域");
	}
	{
		System.out.println("我是构造代码块");
	}
	Person3(){
		
	}
	Person3(String name,int age){
		System.out.println("我是构造函数");
		//contry="cd";
		this.name=name;
		this.age=age;
	}
	public void pop(){
		System.out.println(name+","+age+","+contry);
	}
	public static void speak(){
		System.out.println(contry);
		System.out.println("我是静态方法");
	}
	
	public String toString() {
		return "Person3 [name=" + name + ", age=" + age + "]";
	}
	
	
	public static void main(String[] args) {
		System.out.println("========");
		Person3 p1=new Person3("张三",20);
		p1.pop();
		Person3 p2=new Person3("李四",22);
		Person3 p3=new Person3("李四",22);
		p2.pop();
		
		
		Person3 []arr={p1,p2,p3};
		System.out.println(Arrays.toString(arr));
		
		
	}
	
}
