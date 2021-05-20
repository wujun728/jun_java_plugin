package com.ky26.winterHomeWork;

public class TeacherTest {
	public static void main(String[] args) {
		Teacher t1=new Teacher("王老师",28);
//		t1.setAge(21);
		t1.introduce();
	}
}
class Teacher{
	private String name;
	private int age;
	
	Teacher(){
		
	}
	Teacher(String name,int age){
		this.name=name;
		this.age=age;
	}
	void setName(String name) {
		this.name=name;
	}
	String getName() {
		return this.name;
	}
	void setAge(int age) {
		this.age=age;
	}
	int getAge() {
		return this.age;
	}
	public void introduce() {
		if(!isLegal(this.age)) {
			System.out.println("年龄输入不合法");
			return;
		};
		System.out.println("老师的姓名是："+name+",年龄是："+age);
	}
	public boolean isLegal(int age) {
		if(age>=25) {
			return true;
		}
		else {
			return false;
		}
	}
}
