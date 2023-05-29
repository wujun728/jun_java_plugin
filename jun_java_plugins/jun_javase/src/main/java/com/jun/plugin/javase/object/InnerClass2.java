package com.jun.plugin.javase.object;

public class InnerClass2 {
	public static void main(String[] args) {
		new Demo11(){
			void show(){
				System.out.println("��д�����෽��");
			}
			void show1(){
				System.out.println("������з���");
			}
		}.show1();
	}
}
abstract class Demo11{
	abstract void show();
}
class Outer1{
	void Method(){
		/*new Demo11(){
			void show(){
				System.out.println("��д�����෽��");
			}
			void show1(){
				System.out.println("������з���");
			}
		}.show1();//���ø������֣���������ʵ��������ȿ��Է��ʸ��෽����Ҳ���Է������෽��������ת�ͣ���̬��
		*/
		
		/*Demo11 d=new Demo11(){
			void show(){
				System.out.println("��д�����෽��");
			}
			void show1(){
				System.out.println("������з���");
			}
		};//����һ��������ʵ���������������з���show1�ᱨ��
		d.show();
		d.show1();*/
	}
}



