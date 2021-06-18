package com.abc.bean;

import com.abc.preson.Preson;

public class SumTeacher implements Preson {
	private Preson teacher;

	public SumTeacher(Preson teacher) {
		super();
		this.teacher = teacher;
	}




	@Override
	public void eat() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void goToSchool() {
			System.out.println("下楼就开车");
			teacher.goToSchool();
			System.out.println("开车去学校真爽！");
	}

}
