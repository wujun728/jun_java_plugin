package com.jun.plugin.demo.chap03.sec08;

public class Demo3 {

	public static void main(String[] args) {
		String name1="����";
		String name2="����";
		String name3=new String("����");
		String name4=new String("����");
		
		System.out.println("name1==name2:"+(name1==name2));
		System.out.println("name1==name3:"+(name1==name3));
		System.out.println("name3==name4:"+(name3==name4));
	}
}
