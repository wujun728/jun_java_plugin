package com.ky26.object;

public class StaticKey {
	public static void main(String[] args) {
		System.out.println("============");
		Person1 p1=new Person1("张三",22);
		//Person1 p2=new Person1("李四",21);
		//Person1 p3=new Person1("王麻子",23);
		p1.pop();
		//p1.speak();//对象访问静态方法,容易混淆静态成员和非静态成员，不建议使用
		//p2.pop();
		//p3.pop();
		Person1.speak();//类名访问静态方法,若speak方法未被static修饰，则不能使用类名调用
		/*System.out.println("对象访问:"+p1.contry);
		System.out.println("类访问:"+Person1.contry);*/
		
		
		
	}
}

class Person1{
	String name;
	int age=40;
	static String contry="CN";
	static{
		contry="cq";
		System.out.println("我是静态区域");
	}
	Person1(){
		
	}
	Person1(String name,int age){
		System.out.println("我是构造函数");
		contry="cd";
		this.name=name;
		this.age=age;
	}
	public void pop(){
		System.out.println(name+","+age+","+contry);
	}
	public static void speak(){
		//System.out.println(contry);
		System.out.println("我是静态方法");
	}
}

//执行顺序：静态区域——构造函数——静态方法，构造函数和静态方法的执行顺序不一定，，根据创建对象和调用静态方法的顺序不同而不同
