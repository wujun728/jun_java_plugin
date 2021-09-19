package com.jun.plugin.javase.object;

public class Object2 {
	public static void main(String[] args) {
		int a=12;
		change(a);
		System.out.println(a);
		
		Demo test=new Demo();
		test.n=12;
		test.change(test.n);
	}//һ���Ǹ��ƣ�һ�������ã�
	
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
