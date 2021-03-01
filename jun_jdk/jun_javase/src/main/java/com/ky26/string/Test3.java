package com.ky26.string;

public class Test3 {
	public static void main(String[] args) {
		StringBuilder b1=new StringBuilder("hello ");
		StringBuilder b2=new StringBuilder("java");
		show(b1,b2);
		
		String s1="hello";
		String s2="java";
		show2(s1,s2);
		System.out.println(s1+"----"+s2);
	}
	static void show(StringBuilder s1,StringBuilder s2){
		System.out.println(s1.append(s2));
	}
	static void show2(String str,String str1){
		str.replace('e', 'o');
		str=str1;
	}
}
