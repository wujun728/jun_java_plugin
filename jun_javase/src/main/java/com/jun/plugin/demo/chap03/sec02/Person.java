package com.jun.plugin.demo.chap03.sec02;

/**
 * Person��
 * @author caofeng
 *
 */
public class Person {

	String name;  // �����У�����һ������name�ַ�������
	int age;      // �����У�����һ������age����
	
	
	public void speak(){
		System.out.println("�ҽ�"+name+" �ҽ���"+age+"����");
	}
	
	public static void main(String[] args) {
		// ����һ��Person��Ķ���zhangsna
		Person zhangsan;
		// ʵ��������
		zhangsan=new Person();
		// �������name���Ը�ֵ
		zhangsan.name="����";
		zhangsan.age=23;
		zhangsan.speak();
	}
}
