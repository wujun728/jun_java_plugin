package com.java1234.chap03.sec16;

public class Test {

	public void test(A a){
		a.say();
	}
	
	public static void main(String[] args) {
		Test t=new Test();
		t.test(new B());
		
		t.test(new A(){

			@Override
			public void say() {
				System.out.println("匿名内部类，一次性使用");
			}
			
		});
	}
}
