package com.jun.plugin.javase.object;

public class Homework {
	public static void main(String[] args) {
		new Son("С��",11);
	}
}
class Grandpa{
	String name;
	int age;
	static Son s1=new Son("С��",12);
	static Son s2=new Son("С��",12);
	static{
		System.out.println("����үү�ľ�̬��");
	}
	{
		System.out.println("����үү�Ĺ�������");
	}
	Grandpa(String name,int age){
		System.out.println("����үү�Ĺ��캯��");
		this.name=name;
		this.age=age;
	}
}
class Father11 extends Grandpa{
	static Son s1=new Son("С��",12);
	static Son s2=new Son("С��",12);
	static {
		System.out.println("���Ǹ��׵ľ�̬��");
	}
	{
		System.out.println("���Ǹ��׵Ĺ�������");
	}
	Father11(String name,int age){
		super(name,age);
		System.out.println("���Ǹ��׵Ĺ��캯��");
	}
}
class Son extends Father11{
	int number=10;
	static {
		System.out.println("���Ƕ��ӵľ�̬��");
	}
	{	
		System.out.println(number);
		System.out.println("���Ƕ��ӵĹ�������");
	}
	Son(String name,int age){
		super(name,age);
		System.out.println("���Ƕ��ӵĹ��캯��");
	}
}
/*ÿʵ����һ�ζ���ÿ��һ�ι��캯���������������������࣬�ӻ��࿪ʼִ��һ�ι��캯������̬��Ա�ڼ������ʱ���Ѿ�������ϣ�
�˺��ټ��أ����캯��ÿnewһ�ζ���ִ��һ�Σ���������͹��캯�����ǺͶ�����صģ���̬��Ա������أ��ȼ��������е��ִࣨ�����еľ�̬���򣬴Ӹ����ӣ�����Ϊ��Ա������ֵ
���ʵ��������ִ�й����͹��캯�����Ӹ����ӣ�;��̬��Աִֻ��һ��*/
