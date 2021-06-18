package com.java1234.chap06.sec01;

public class Test {

	public static void main(String[] args) {
		// begin test c1 c2
		C1 c1=new C1(1);
		c1.print();
		int i=c1.getA();
		System.out.println("i="+i);
		
		C2 c2=new C2("Hi");
		c2.print();
		String s=c2.getA();
		System.out.println("s="+s);
		// end test c1 c2
		
		// begin test c12
		C12 c12=new C12(1); // 向上转型
		c12.print();
		int i2=(Integer) c12.getObject(); // 向下转型
		System.out.println("i2="+i2);
		
		C12 c122=new C12("你好");
		c122.print();
		String s2=(String) c122.getObject();
		System.out.println("s2="+s2);
		// end test c12
		
		// begin test cc
		CC<Integer> cc=new CC<Integer>(1);
		cc.print();
		int i3=cc.getT();
		System.out.println("i3="+i3);
		
		CC<String> cc2=new CC<String>("我是泛型，好简单");
		cc2.print();
		String s3=cc2.getT();
		System.out.println("s3="+s3);
		// end test cc
	}
}
