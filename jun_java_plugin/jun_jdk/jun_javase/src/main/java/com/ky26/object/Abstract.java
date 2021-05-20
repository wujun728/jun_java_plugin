package com.ky26.object;

public class Abstract {
	public static void main(String[] args) {
		Manager m1=new Manager("产品经理",28);
		//m1.setAge(32);
		//System.out.println(m1.getAge());
//		m1.show();
//		m1.work();
//		
//		Coder c1=new Coder("程序员",23);
//		c1.show();
//		c1.work();
//		
//		Coder c2=new Coder("张五",27,"项目A");
//		c2.show();
//		c2.work();
//		c2.showProject();
		
		
		new Employee("王麻子",21){
			void work(){
				System.out.println("大佬");
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
	abstract void show();//因为两个子类都要继承本类，因此两个子类公共的方法抽象化放在父类中，而coder类特有的showProject方法则放在接口中，coder单独去继承和重写该方法
}

interface showImpl{
	void showProject();
}

class Manager extends Employee{
	Manager(String name,int age){
		super(name,age);
	}
	void work(){
		System.out.println("大佬");
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
		System.out.println("码农");
	}
	void show(){
		System.out.println(getName()+"======"+getAge());
	} 
	public void showProject(){
		System.out.println(getProject());
	}
}
