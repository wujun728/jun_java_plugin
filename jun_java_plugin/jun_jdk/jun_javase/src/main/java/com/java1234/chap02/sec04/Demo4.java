package com.java1234.chap02.sec04;

public class Demo4 {

	public static void main(String[] args) {
		// && 与  前后两个操作数必须都是true才返回true,否则返回false
		boolean b1=(5<3)&&(4>5);
		System.out.println("b1="+b1);
		
		// & 不短路与
		boolean b2=(5<3)&(4>5);
		System.out.println("b2="+b2);
		
		// 一般都用&& 短路  
		// 原因：效率高
		
		// || 或 只要两个操作数中有一个是true，就返回true，否则返回false
		boolean b3=(2<3)||(4>5);
		System.out.println("b3="+b3);
		
		// | 不短路 或
		boolean b4=(2<3)|(4>5);
		System.out.println("b4="+b4);
		
	    // ! 非 ，如果操作数为true，返回false，否则，返回true
		boolean b5=!(3<4);
		System.out.println("b5="+b5);
		
		// ^ 异或 ，当两个操作数不相同时返回true，返回false
		boolean b6=(5>4)^(4>5);
		System.out.println("b6="+b6);
	}
}
