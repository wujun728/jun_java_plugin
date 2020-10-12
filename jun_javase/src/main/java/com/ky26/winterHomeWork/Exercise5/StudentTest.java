package com.ky26.winterHomeWork.Exercise5;

public class StudentTest {
	public static void main(String[] args) {
		Student s1=new Student();
		Student s2=new Student();
		Student s3=new Student();
		Student.showCount();
		Student s4=new Student(1,"zs","nv","5班");
		Student s5=new Student(2,"zs","nv","6班");
		Student s6=new Student(3,"zs","nv","7班");
		Student.showCount();
		s1.show();
		s2.show();
		s3.show();
		s4.show();
		s5.show();
		s6.show();
	}
}
class Student{
	private int sno;
	private String name;
	private String sex;
	private String clazz;
	static int count;
	Student(){
		count++;
	}
	Student(int sno,String name,String sex,String clazz){
		this.sno=sno;
		this.name=name;
		this.sex=sex;
		this.clazz=clazz;
		count++;
	}
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public void show() {
		System.out.println("学号:"+getSno()+"，姓名:"+getName()+"，性别:"+getSex()+"，班级:"+getClazz());
	}
	public static void showCount() {
		System.out.println("已经创建"+Student.count+"个对象");
	}
	
}