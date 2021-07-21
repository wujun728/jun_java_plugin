package aop.no.impl;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import aop.impl.advice.Waiter;


public class TestJUnit {

	@Test
	public void testAspect(){
		String file="aop-no-impl.xml";
		ConfigurableApplicationContext context=
				new ClassPathXmlApplicationContext(file);
		Waiter waiter=(Waiter)context.getBean("waiter");
		waiter.leadCustomer("Jason");
		context.close();		
	}
}
