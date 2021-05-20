package com.abc.app;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.abc.bean.Student;
import com.abc.preson.Preson;

public class MainTest2 {
	
	public static void main(String[] args) {
		
		Student student = new Student("老王");
		student.eat();
		Preson studentProxy = (Preson) Proxy.newProxyInstance(MainTest2.class.getClassLoader(),
				student.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("访问方法前，记录日志");
				Object invoke = method.invoke(student, args);
				System.out.println("访问方法后，记录日志");
				return invoke;
			}
		});
		studentProxy.eat();
	}
}
