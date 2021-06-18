package com.ky26.object.test;

public class Worker extends Person {
	private int gongzi;
	Worker(String name,int age,int gongzi){
		super(name,age);
		this.gongzi=gongzi;
	}
	
	void setGongzi(int gongzi){
		this.gongzi=gongzi;
	}
	int getGongzi(){
		return gongzi;
	}
	void show(){
		System.out.println(getName()+","+getAge()+","+gongzi);
	}
}
