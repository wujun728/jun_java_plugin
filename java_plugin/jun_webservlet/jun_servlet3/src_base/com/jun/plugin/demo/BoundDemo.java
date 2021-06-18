package com.jun.plugin.demo;

public class BoundDemo {
	public static void main(String[] args) {
		A111 a = new B();
		System.err.println(a.age);//100
		System.err.println(a.num);//100
		a.abc();//abc...1000 所有非静态分开方法都是在运行时绑定，动态绑定，其他的都是静态绑定
				//静态绑定是指在编译时已经指定引用的就是静态绑定
		a.aaa();//aaa..100
	}
}
class A111{
	public int age=100;
	public static int num = 100;
	public void abc(){
		System.err.println("abc....100");
	}
	public static void aaa(){
		System.err.println("aaa....100");
	}
}
class B extends A111{
	public int age=1000;
	public static int num = 1000;
	public void abc(){
		System.err.println("abc....1000");
	}
	public static void aaa(){
		System.err.println("aaa....1000");
	}
}
