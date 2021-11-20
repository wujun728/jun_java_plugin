package com.jun.plugin.demo.chap03.sec18;

public class TestSingleton {

	public static void main(String[] args) {
	  //	Singleton1 single=new Singleton1();
		
		Singleton1 single1=Singleton1.getInstance();
		Singleton1 single2=Singleton1.getInstance();
		System.out.println("����ʽ :"+(single1==single2));
		
		TestSingleton t=new TestSingleton();
		TestSingleton t2=new TestSingleton();
		System.out.println(t==t2);
		
		Singleton2 single3=Singleton2.getInstance();
		Singleton2 single4=Singleton2.getInstance();
		System.out.println("����ʽ :"+(single3==single4));
	}
}
