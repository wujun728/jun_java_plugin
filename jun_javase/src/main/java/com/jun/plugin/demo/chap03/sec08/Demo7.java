package com.jun.plugin.demo.chap03.sec08;

public class Demo7 {

	public static void main(String[] args) {
		// subString��ʽʹ��
		String str="������ÿһ��,������";
		String newStr=str.substring(1);
		
		String newStr2=str.substring(1, 6);
		System.out.println(str);
		System.out.println(newStr);
		System.out.println(newStr2);
	}
}
