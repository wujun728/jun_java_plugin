package com.jun.plugin.javase.object;

public class Abstract {
	public static void main(String[] args) {
		Manager m1=new Manager("��Ʒ����",28);
		//m1.setAge(32);
		//System.out.println(m1.getAge());
//		m1.show();
//		m1.work();
//		
//		Coder c1=new Coder("����Ա",23);
//		c1.show();
//		c1.work();
//		
//		Coder c2=new Coder("����",27,"��ĿA");
//		c2.show();
//		c2.work();
//		c2.showProject();
		
		
		new Employee("������",21){
			void work(){
				System.out.println("����");
			}
			void show(){
				System.out.println(getName()+"======"+getAge());
			}
		}.show();
		
		
	}
}


abstract class Employee{
	private String name;
	private int age;
	Employee(String name,int age){
		this.name=name;
		this.age=age;
	}
	void setName(String name){
		this.name=name;
	}
	String getName(){
		return name;
	}
	void setAge(int age){
		this.age=age;
	}
	int getAge(){
		return age;
	}
	
	void print(){
		System.out.println(1);
	}
	abstract void work();
	abstract void show();//��Ϊ�������඼Ҫ�̳б��࣬����������๫���ķ������󻯷��ڸ����У���coder�����е�showProject��������ڽӿ��У�coder����ȥ�̳к���д�÷���
}

interface showImpl{
	void showProject();
}

class Manager extends Employee{
	Manager(String name,int age){
		super(name,age);
	}
	void work(){
		System.out.println("����");
	}
	void show(){
		System.out.println(getName()+"======"+getAge());
	}
}
class Coder extends Employee implements showImpl{
	private String project;
	Coder(String name,int age){
		super(name,age);
	}
	Coder(String name,int age,String project){
		this(name,age);
		this.project=project;
	}
	void setProject(String project){
		this.project=project;
	}
	String getProject(){
		return project;
	}
	void work(){
		System.out.println("��ũ");
	}
	void show(){
		System.out.println(getName()+"======"+getAge());
	} 
	public void showProject(){
		System.out.println(getProject());
	}
}
