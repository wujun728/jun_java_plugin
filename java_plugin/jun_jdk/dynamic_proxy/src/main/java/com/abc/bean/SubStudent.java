package com.abc.bean;

public class SubStudent extends Student {

	@Override
	public void goToSchool() {
		System.out.println("等公交");
		super.goToSchool();
		System.out.println("坐公交好辛苦！");
	}

	
	
	
}
