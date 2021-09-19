package com.jun.plugin.demo.chap03.sec08;

public class Demo5 {

	public static void main(String[] args) {
		String name="����";
		char ming=name.charAt(1);
		System.out.println(ming);
		
		String str="�����й���";
		// �����ַ���
		for(int i=0;i<str.length();i++){
			System.out.println(str.charAt(i));
		}
	}
}
