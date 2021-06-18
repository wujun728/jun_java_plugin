package com.ky26.object;

public class IntegerDemo {
	int a;
	Integer b;
	void show(){
		System.out.println("a «"+a);
		System.out.println("b «"+b);
	}
	public static void main(String[] args) {
		byte b=5;
		Byte bb=b;
		bb=b;
		b=bb;
		System.out.println(b+"---------"+bb);
		System.out.println(b==bb);
		new IntegerDemo().show();
		
		
	}
}
