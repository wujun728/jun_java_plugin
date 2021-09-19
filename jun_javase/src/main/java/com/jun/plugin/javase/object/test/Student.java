package com.jun.plugin.javase.object.test;

public class Student {
	private String name;
	private int age;
	private String sex;
	private String dept;
	
	Student(String name,int age){
		this.name=name;
		this.age=age;
		this.sex="��";
		this.dept="Computer Science";
	}
	Student(String name,int age,String sex,String dept){
		this.name=name;
		this.age=age;
		this.sex=sex;
		this.dept=dept;
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
	void setSex(String sex){
		this.sex=sex;
	}
	String getSex(){
		return sex;
	}
	void setDept(String dept){
		this.dept=dept;
	}
	String getDept(){
		return dept;
	}
	void introduce(Student s1){
		if(s1.getSex()==null||s1.getDept()==null){
			s1.setSex("��");
			s1.setDept("Computer Science");
		}
		System.out.println("�ҽ�"+getName()+","+getAge()+"��,�Ա�:"+getSex()+",רҵ:"+getDept());
	}
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", sex=" + sex+ ", dept=" + dept + "]";
	}
	
}
