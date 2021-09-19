package com.jun.plugin.javase.object;

public class ExtendsKey {
	public static void main(String[] args) {
//		Son s=new Son();
		
		Father f2=new Father();
		
	}
}


class Father{
	
	static Father f1=new Father();
	
	Father(){
		System.out.println("���캯��");
		System.out.println(f1);
	}
	void show(){
		
	}
}
/*class Son extends Father{
	int num=8;
	Son(){
		System.out.println(num);
		num=10;
		System.out.println(num);
	}
	void show(){
		System.out.println("Son����"+"========="+num);
	}
}*/


