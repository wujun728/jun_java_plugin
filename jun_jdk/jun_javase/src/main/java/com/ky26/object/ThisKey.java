package com.ky26.object;

public class ThisKey {
	public static void main(String[] args) {
		new C();
	}
}
class A{
	D d=new D("A");
	static{
		System.out.println("load A");
	}
	public A(){
		System.out.println("create A");
	}
}
class B extends A{
	D d=new D("B");
	static{
		System.out.println("load B");
	}
	public B(){
		System.out.println("create B");
	}
}
class C extends B{
	D d=new D("C");
	static{
		System.out.println("load C");
	}
	public C(){
		System.out.println("create C");
	}
}
class D{
	static{
		System.out.println("load D");
	}
	D(String str){
		System.out.println("D在类"+str+"中初始化");
	}
}
