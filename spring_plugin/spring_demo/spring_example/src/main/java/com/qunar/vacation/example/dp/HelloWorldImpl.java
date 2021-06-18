package com.qunar.vacation.example.dp;

import java.lang.reflect.Proxy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class HelloWorldImpl implements HelloWorld{

	@Track
	@Override
	public void hello() {
		System.out.println("hello");
	}

	@Override
	public void hi() {
		System.out.println("hi");
	}
	
	public static void main(String[] args) {
		//some aop code to use jdk dynamic proxy
		HelloWorld helloWorld = new HelloWorldImpl();
		HelloWorld proxy = (HelloWorld) Proxy.newProxyInstance(helloWorld.getClass().getClassLoader(), 
							HelloWorldImpl.class.getInterfaces(), new MethodTracker(helloWorld));
		proxy.hello();
		proxy.hi();
		
		//some aop code to use aspectj 
		/*ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		HelloWorld helloWorld = context.getBean(HelloWorld.class);
		helloWorld.hello();
		helloWorld.hi();*/
	}
}
