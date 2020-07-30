package com.abc.app;

import java.lang.reflect.Proxy;

import com.abc.bean.LogHandler;
import com.abc.bean.LogInterceptor;
import com.abc.bean.Manager;
import com.abc.bean.Student;
import com.abc.bean.SubStudent;
import com.abc.bean.SumTeacher;
import com.abc.bean.Teacher;
import com.abc.preson.Preson;
import com.abc.proxy.CglibProxy;
import com.abc.proxy.JdkProxy;

import net.sf.cglib.proxy.Enhancer;

public class MainTest {
	
	public static void main(String[] args) {
		//静态代理
		Preson p = new Student();
		Preson p1 = new SubStudent();
		Preson p2 = new Teacher();
		Preson p3 = new SumTeacher(new Teacher());
		Preson p4 = new SumTeacher(new Student());
		
		p.goToSchool();
		p1.goToSchool();
		p2.goToSchool();
		p3.goToSchool();
		p4.goToSchool();
		
		System.out.println("===========jdk动态代理============");
		//jdk实现
		Student s = new Student();
		LogHandler logHandler = new LogHandler(s);
		Class<?> sc = s.getClass();
		Preson proxyStudent = (Preson) Proxy.newProxyInstance(sc.getClassLoader(),sc.getInterfaces(), logHandler);
		proxyStudent.goToSchool();
		//实现方法2
		Preson stu = (Preson) new JdkProxy().getInstance(new Student());
		stu.goToSchool();
		
		System.out.println("===========cglib动态代理============");
		
		//cglib实现动态代理，需提前导入cglib jar包
		//Enhancer是CGLib的字节码增强器，可以方便的对类进行扩展，内部调用GeneratorStrategy.generate方法生成代理类的字节码，通过以下方式可以生成class文件。
		Enhancer enhancer = new Enhancer();
		//继承代理类
		enhancer.setSuperclass(Manager.class);
		enhancer.setCallback(new LogInterceptor());
		//通过字节码技术动态创建子类实例  
		Manager m = (Manager) enhancer.create();
		m.goToSchool();
		//实现方法2
		Manager instance = (Manager) new CglibProxy().getInstance(new Manager());
		instance.goToSchool();
		
	}
	
	

}
