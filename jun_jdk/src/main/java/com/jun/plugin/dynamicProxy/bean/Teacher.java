package com.jun.plugin.dynamicProxy.bean;

import com.jun.plugin.dynamicProxy.preson.Preson;

public class Teacher implements Preson {

	@Override
	public void goToSchool() {
		
		System.out.println("开车去学校");
		
	}

	@Override
	public void eat() {

		System.out.println("我吃小堂，味道还不错哦");
		
	}

}
