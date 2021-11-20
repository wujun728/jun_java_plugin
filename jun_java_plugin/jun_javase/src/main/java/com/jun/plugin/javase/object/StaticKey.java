package com.jun.plugin.javase.object;

public class StaticKey {
	public static void main(String[] args) {
		System.out.println("============");
		Person1 p1=new Person1("����",22);
		//Person1 p2=new Person1("����",21);
		//Person1 p3=new Person1("������",23);
		p1.pop();
		//p1.speak();//������ʾ�̬����,���׻�����̬��Ա�ͷǾ�̬��Ա��������ʹ��
		//p2.pop();
		//p3.pop();
		Person1.speak();//�������ʾ�̬����,��speak����δ��static���Σ�����ʹ����������
		/*System.out.println("�������:"+p1.contry);
		System.out.println("�����:"+Person1.contry);*/
		
		
		
	}
}

class Person1{
	String name;
	int age=40;
	static String contry="CN";
	static{
		contry="cq";
		System.out.println("���Ǿ�̬����");
	}
	Person1(){
		
	}
	Person1(String name,int age){
		System.out.println("���ǹ��캯��");
		contry="cd";
		this.name=name;
		this.age=age;
	}
	public void pop(){
		System.out.println(name+","+age+","+contry);
	}
	public static void speak(){
		//System.out.println(contry);
		System.out.println("���Ǿ�̬����");
	}
}

//ִ��˳�򣺾�̬���򡪡����캯��������̬���������캯���;�̬������ִ��˳��һ���������ݴ�������͵��þ�̬������˳��ͬ����ͬ
