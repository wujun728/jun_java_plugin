package com.jun.plugin.dynamicProxy.bean;

import com.jun.plugin.dynamicProxy.preson.Preson;

public class Student implements Preson {
	private String name;
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Student(String name) {
		super();
		this.name = name;
	}


	@Override
	public void goToSchool() {
		
		System.out.println("坐车去学校");
		
	}

	@Override
	public void eat() {
		
		System.out.println(name+"我吃大饭堂，好难吃！");
		
	}

}
