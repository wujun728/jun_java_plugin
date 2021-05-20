package com.java1234.chap02;

public class Demo03 {

	public static void main(String[] args) {
		// 定义一个单个字符
		char c1='A';
		char c2='b';
		char c3='3';
		
		System.out.println("c1="+c1);
		System.out.println("c2="+c2);
		System.out.println("c3="+c3);
		
		// 转义字符
		char c4='\'';
		char c5='\\';
		System.out.println("c4="+c4);
		System.out.println("c5="+c5);
		
		// 用Unicode编码输出自己的名字
		char c6='\u66f9';
		char c7='\u950b';
		System.out.println(c6+""+c7);
		
	}
}
