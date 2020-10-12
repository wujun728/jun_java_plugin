package com.ky26.object;

public class ConstructT {
	public static void main(String[] args) {
		Construct student=new Construct();//新自定义建立有参构造函数后，原来的无参构造函数就消失了，因此再使用无参的形式创建对象则会报错
		student.setName("zahngsan");
		student.setAge(12);
		student.print();
		
		/*Construct student2=new Construct("李四",20);
		student2.print();*/
		
		
		
		Construct s3=student;
		s3.age=99;
		s3.name="王二";
		s3.print();
		student.print();//对象的引用
		
	}
}

class Construct{
	String name;
	int age;
	public Construct(){
		//System.out.println("我是构造函数");
		name="张三";
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

