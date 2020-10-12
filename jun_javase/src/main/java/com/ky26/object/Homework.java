package com.ky26.object;

public class Homework {
	public static void main(String[] args) {
		new Son("小明",11);
	}
}
class Grandpa{
	String name;
	int age;
	static Son s1=new Son("小红",12);
	static Son s2=new Son("小红",12);
	static{
		System.out.println("我是爷爷的静态块");
	}
	{
		System.out.println("我是爷爷的构造代码块");
	}
	Grandpa(String name,int age){
		System.out.println("我是爷爷的构造函数");
		this.name=name;
		this.age=age;
	}
}
class Father11 extends Grandpa{
	static Son s1=new Son("小红",12);
	static Son s2=new Son("小红",12);
	static {
		System.out.println("我是父亲的静态块");
	}
	{
		System.out.println("我是父亲的构造代码块");
	}
	Father11(String name,int age){
		super(name,age);
		System.out.println("我是父亲的构造函数");
	}
}
class Son extends Father11{
	int number=10;
	static {
		System.out.println("我是儿子的静态块");
	}
	{	
		System.out.println(number);
		System.out.println("我是儿子的构造代码块");
	}
	Son(String name,int age){
		super(name,age);
		System.out.println("我是儿子的构造函数");
	}
}
/*每实例化一次对象（每调一次构造函数）都会向上搜索至基类，从基类开始执行一次构造函数，静态成员在加载类的时候已经加载完毕，
此后不再加载，构造函数每new一次都会执行一次；构造代码块和构造函数都是和对象相关的，静态成员和类相关；先加载完所有的类（执行所有的静态区域，从父到子），再为成员变量赋值
最后实例化对象（执行构造块和构造函数，从父到子）;静态成员只执行一次*/
