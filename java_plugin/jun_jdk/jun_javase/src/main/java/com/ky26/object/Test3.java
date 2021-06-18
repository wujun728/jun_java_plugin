package com.ky26.object;



public class Test3 {
	static void show(){
		System.out.println(1);
	}
	void show(int n){
		
	}
}
class TestF extends Test3{
	static void show(){
		System.out.println(2);
	}//隐藏了父类方法，而不是重写???static修饰的成员放在方法区中，所有实例共享，所以才会有隐藏父类的说法???
	void show(int n){
		System.out.println(n);
	}
}
