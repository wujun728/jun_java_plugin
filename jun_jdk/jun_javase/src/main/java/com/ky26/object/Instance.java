package com.ky26.object;

public class Instance {
	public static void main(String[] args) {
		SingleDemo s1=SingleDemo.getInstance();
		SingleDemo s2=SingleDemo.getInstance();
		
		System.out.println(s1==s2);
		
	}
}
class SingleDemo{
	private static SingleDemo s=new SingleDemo();
	private SingleDemo(){}
	public static SingleDemo getInstance(){
		return s;
	}
}
class Car1{
	private static Car1 c=new Car1();
	private Car1(){
		
	}
	public static Car1 getInstance(){
		return c;
	}
}

