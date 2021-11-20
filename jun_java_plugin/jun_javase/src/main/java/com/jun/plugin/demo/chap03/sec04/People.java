package com.jun.plugin.demo.chap03.sec04;

public class People {

	// String������Ĭ��ֵ��Null
	private String name;
	// int������Ĭ��ֵ��0
	private int age;
	
	/**
	 * Ĭ�Ϲ��췽��  ���б�ע��ݷ�ʽ  ctrl+shift+/
	 */
	public People(){
		System.out.println("Ĭ�Ϲ��췽����");
	}
	
	/** 
	 * �в����Ĺ��췽�� ���췽��������
	 */
	People(String name2,int age2){
		name=name2;
		age=age2;
		System.out.println("�в����Ĺ��췽����");
	}
	
	public void say(){
		System.out.println("�ҽУ�"+name+"���ҽ��꣺"+age+"���ˣ�");
	}
	
	public static void main(String[] args) {
		// People people=new People();
		 People people2=new People("����",23);
		people2.say();
	}
}
