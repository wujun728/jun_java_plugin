package aop2.vip;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import ioc.Conn;

public class TestJUnit {

	RegexpMethodPointcutAdvisor t=null;
	ProxyFactoryBean f=null;
	@Test
	public void test(){		
		ConfigurableApplicationContext context=
				new ClassPathXmlApplicationContext("spring-aop.xml");	
		Action action=(Action) context.getBean("action");
		String result=action.out("123",123);
//		action.say("123");
		System.out.println(result+"test");
		context.close();
	}
	
	
	
}
