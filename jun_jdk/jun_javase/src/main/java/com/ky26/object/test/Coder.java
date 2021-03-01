package com.ky26.object.test;

public class Coder extends Person{
	private String yuyan;
	Coder(String name,int age,String yuyan){
		super(name,age);
		this.yuyan=yuyan;
	}
	
	void setYuyan(String yuyan){
		this.yuyan=yuyan;
	}
	String getYuyan(){
		return yuyan;
	}
	void show(){
		System.out.println("ĞÕÃû:"+getName()+","+"ÄêÁä:"+getAge()+","+"ÓïÑÔ:"+yuyan);
	}
}
