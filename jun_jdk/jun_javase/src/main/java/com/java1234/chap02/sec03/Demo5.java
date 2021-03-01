package com.java1234.chap02.sec03;

public class Demo5 {

	public static void main(String[] args) {
		// 自动类型转换
		short s=1;
		int i;
		// 自动类型转换   short类型转成int类型
		i=s;
		System.out.println("i="+i);
		
		// 强制类型转换
		double d=1.333;
		float f;
		// 把double类型的数据强制转换成float类型
		f=(float)d;
		System.out.println("f="+f);
	}
}
