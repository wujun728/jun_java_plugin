package com.java1234.chap03.sec03;

class SanWei{
	int b;  // 胸围
	int w;  // 腰围
	int h;  // 臀围
}

public class Person7 {

	
	void speak(int age,SanWei sanWei){
		System.out.println("我今年"+age+"岁了");
		System.out.println("我的三围是："+sanWei.b+","+sanWei.w+","+sanWei.h);
		age=24;
		System.out.println("方法里age"+age);
		sanWei.b=80; // 胸围缩水
	}
	
	public static void main(String[] args) {
		Person7 zhangsan=new Person7();
		int age=23;
		SanWei sanWei=new SanWei();
		sanWei.b=90;
		sanWei.w=60;
		sanWei.h=90;
		zhangsan.speak(age,sanWei);
		System.out.println(age);
		System.out.println("调用地方，胸围"+sanWei.b);
	}
}
