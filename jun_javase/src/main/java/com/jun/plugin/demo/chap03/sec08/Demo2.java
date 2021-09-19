package com.jun.plugin.demo.chap03.sec08;

public class Demo2 {

	public static void main(String[] args) {
		String name1="����"; // ֱ�Ӹ�ֵ��ʽ
		String name2=new String("����"); // new�ķ�ʽ
		String name3=name2;  // ��������
		
		// ==�Ƚϵ�������
		System.out.println("name1==name2:"+(name1==name2));
		System.out.println("name1==name3:"+(name1==name3));
		System.out.println("name2==name3:"+(name2==name3));
		
		// equals�Ƚϵ�������
		System.out.println("name1.equals(name2):"+(name1.equals(name2)));
		System.out.println("name1.equals(name3):"+(name1.equals(name3)));
		System.out.println("name2.equals(name3):"+(name2.equals(name3)));
	}
}
