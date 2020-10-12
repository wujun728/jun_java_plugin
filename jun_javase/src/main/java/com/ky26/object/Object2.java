package com.ky26.object;

public class Object2 {
	public static void main(String[] args) {
		int a=12;
		change(a);
		System.out.println(a);
		
		Demo test=new Demo();
		test.n=12;
		test.change(test.n);
	}//一个是复制，一个是引用；
	
	static void change(int n){
		n=10;
	}
}

class Demo{
	int n;
	void change(int n){
		n=10;
		System.out.println(n);
	}
}
