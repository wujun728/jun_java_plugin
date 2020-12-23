package com.java1234.chap02.sec03;

public class Demo3 {

	public static void main(String[] args) {
		// 定义一个单个字符
		char c1='A';
		System.out.println("c1="+c1);
		
		// 定义一个反斜杠字符
		char c2='\\';
		System.out.println("c2="+c2);
		
		// 用Unicode编码输出自己的名字
		char c3='\u66f9';
		char c4='\u950b';
		System.out.println(c3);
		System.out.println(c4);
		
		char c5='\u738b';
		System.out.println(c5);
	}
}
