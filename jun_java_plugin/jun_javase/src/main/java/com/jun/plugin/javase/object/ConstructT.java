package com.jun.plugin.javase.object;

public class ConstructT {
	public static void main(String[] args) {
		Construct student=new Construct();//���Զ��彨���вι��캯����ԭ�����޲ι��캯������ʧ�ˣ������ʹ���޲ε���ʽ����������ᱨ��
		student.setName("zahngsan");
		student.setAge(12);
		student.print();
		
		/*Construct student2=new Construct("����",20);
		student2.print();*/
		
		
		
		Construct s3=student;
		s3.age=99;
		s3.name="����";
		s3.print();
		student.print();//���������
		
	}
}

class Construct{
	String name;
	int age;
	public Construct(){
		//System.out.println("���ǹ��캯��");
		name="����";
		age=21;
	}
	public Construct(String name1,int age1){
		age=age1;
		name=name1;
	}
	public void setName(String name1){
		name=name1;
	}
	public void setAge(int age1){
		age=age1;
	}
	public void print(){
		System.out.println(name+"===="+age);
	}
}

