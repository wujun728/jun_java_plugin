package com.java1234.chap02;

public class Demo05 {

	public static void main(String[] args) {
		// 自动类型转换
		short s=1;
		int i;
		// 自动类型转换，short类型转换成int类型 安全的
		i=s;
		System.out.println("i="+i);
		
		// 强制类型转换
		double d=1.333;
		float f;
		f=(float)d;
		System.out.println("f="+f);
	}
}
