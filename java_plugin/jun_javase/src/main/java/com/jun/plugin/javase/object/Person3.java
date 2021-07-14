package com.jun.plugin.javase.object;

import java.util.Arrays;

public class Person3 {
	String name;
	int age;
	static String contry;
	static{
		contry="cq";
		System.out.println("���Ǿ�̬����");
	}
	{
		System.out.println("���ǹ�������");
	}
	Person3(){
		
	}
	Person3(String name,int age){
		System.out.println("���ǹ��캯��");
		//contry="cd";
		this.name=name;
		this.age=age;
	}
	public void pop(){
		System.out.println(name+","+age+","+contry);
	}
	public static void speak(){
		System.out.println(contry);
		System.out.println("���Ǿ�̬����");
	}
	
	public String toString() {
		return "Person3 [name=" + name + ", age=" + age + "]";
	}
	
	
	public static void main(String[] args) {
		System.out.println("========");
		Person3 p1=new Person3("����",20);
		p1.pop();
		Person3 p2=new Person3("����",22);
		Person3 p3=new Person3("����",22);
		p2.pop();
		
		
		Person3 []arr={p1,p2,p3};
		System.out.println(Arrays.toString(arr));
		
		
	}
	
}
