package com.yc;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class Test {
	public static void main(String[] args) {
		BeanFactory bf=new ClassPathXmlApplicationContext("spring-cfg.xml");
		B b=(B) bf.getBean("sb");//һBʵ
		b.getA().a();//ͨBgetA()ȡһAʵaķ
		/*B b=(B) bf.getBean("sb");
		b.getA().a();*/
		/*A a=(A) bf.getBean("a");
		A a1=(A) bf.getBean("a");
		System.out.println(a==a1);*/
		
		
	}
}
