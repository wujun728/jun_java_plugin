package com.jun.plugin.javase.winterHomeWork;

public class TeacherTest {
	public static void main(String[] args) {
		Teacher t1=new Teacher("����ʦ",28);
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
			System.out.println("�������벻�Ϸ�");
			return;
		};
		System.out.println("��ʦ�������ǣ�"+name+",�����ǣ�"+age);
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
