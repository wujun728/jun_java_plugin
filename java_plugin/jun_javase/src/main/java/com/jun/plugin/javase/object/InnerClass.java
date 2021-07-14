package com.jun.plugin.javase.object;

public class InnerClass {
	public static void main(String[] args) {
		/*Outer.Inner o=new Outer().new Inner();//��Ա�ڲ��ࡪ���ڲ��಻��static������ʹ�ô˷�������
		o.show();
		Outer o1=new Outer();
		o1.showInner();//�����ڲ���ı���*/
		
		
		/*Outer.Inner in=new Outer.Inner();//��Ա�ڲ��ࡪ�����ڲ��౻static������ʹ�ô˷�������
		in.show();*/
		
		//Object o=new Outer().method();//�ֲ��ڲ���
		//System.out.println(o);//�ֲ��ڲ����еķ����ͱ�������������ڸ÷��������ڲ�����
		Outer.Inner o1=new Outer().new Inner();
	}
}
class Outer{
	int number=5;
	class Inner{
		int number=6;
		void show(){
			int number=7;
			System.out.println("-----------"+number);
		}
	}
	/*
	void showInner(){
		Inner a1=new Inner();
		a1.show();//6
		System.out.println(a1.number);//7
	}*/
	
	static{
		System.out.println("�����ⲿ��ľ�̬��");
	}
	Outer(){
		System.out.println("�����ⲿ��Ĺ��캯��");
	}
	
	Object method(){
		class Inner{
			int a=12;
			/*static{
				System.out.println("�����ڲ���ľ�̬��");
			}*/
			Inner(){
				System.out.println("�����ڲ���Ĺ��캯��"+"-------a="+a);
			}
			void show(){
				System.out.println("�ⲿ���еı���number�ǣ�"+number);
				System.out.println("�ֲ��ڲ���ķ���");
			}
		}
		Inner in=new Inner();
		in.show();
		return in;
	}
}
//�ڲ���ļ���˳������:



