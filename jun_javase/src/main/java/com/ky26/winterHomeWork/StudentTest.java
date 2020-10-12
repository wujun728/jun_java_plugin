package com.ky26.winterHomeWork;

public class StudentTest {
	public static void main(String[] args) {
		Student s1=new Student("张三",12);
		s1.introduce();
	}
}
class Student{
	private String name;
	private int age;
	private String sex;
	private String dept;
	Student(){
		
	}
	Student(String name,int age){
		this.name=name;
		this.age=age;
//		this.sex="男";
//		this.dept="Computer Science";//初始化方式1
	}
	Student(String name,int age,String sex,String dept){
		this.name=name;
		this.age=age;
		this.sex=sex;
		this.dept=dept;
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
	void setSex(String sex) {
		this.sex=sex;
	}
	String getSex() {
		return this.sex;
	}
	void setDept(String dept) {
		this.dept=dept;
	}
	String getDept() {
		return this.dept;
	}
	
	public void introduce() {
		if(this.sex==null||this.dept==null) {
			setSex("男");
			setDept("Computer Science");
		}//初始化方式2
		System.out.println("我叫"+name+" "+age+"岁 "+sex+" "+dept);
	}
	
	
	
	
}
