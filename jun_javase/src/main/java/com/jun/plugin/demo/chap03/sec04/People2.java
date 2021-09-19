package com.jun.plugin.demo.chap03.sec04;

public class People2 {


	// String������Ĭ��ֵ��Null
	private String name;
	// int������Ĭ��ֵ��0
	private int age;
	
	/**
	 * Ĭ�Ϲ��췽��  ���б�ע��ݷ�ʽ  ctrl+shift+/
	 */
	People2(){
		System.out.println("Ĭ�Ϲ��췽����");
	}
	
	/** 
	 * �в����Ĺ��췽�� ���췽��������
	 */
	People2(String name,int age){
		this();
		this.name=name;
		this.age=age;
		System.out.println("�в����Ĺ��췽����");
	}
	
	public void say(){
		System.out.println("�ҽУ�"+name+"���ҽ��꣺"+age+"���ˣ�");
	}
	
	public static void main(String[] args) {
		// People people=new People();
		People2 people2=new People2("����",23);
		people2.say();
	}
}
