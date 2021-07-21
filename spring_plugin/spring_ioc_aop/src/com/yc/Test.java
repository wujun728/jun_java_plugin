package com.yc;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class Test {
	public static void main(String[] args) {
		BeanFactory bf=new ClassPathXmlApplicationContext("spring-cfg.xml");
		B b=(B) bf.getBean("sb");//创建一个B的实例
		b.getA().a();//通过B的getA()方法获取一个A的实例，调用a的方法
		/*B b=(B) bf.getBean("sb");
		b.getA().a();*/
		/*A a=(A) bf.getBean("a");
		A a1=(A) bf.getBean("a");
		System.out.println(a==a1);*/
		
		
	}
}
