package aop.impl.advice;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestJUnit {

//	@Test
//	public void testBefore(){
//	  	String file="aop-impl.xml";
//	  	ConfigurableApplicationContext context=
//	  			     new ClassPathXmlApplicationContext(file);
//		Waiter waiter=(Waiter)context.getBean("waiterBefore");
//        waiter.leadCustomer("zhou");
//		context.close();
//	}
//	@Test
	public void testAfter(){
		String file="aop-impl.xml";
	  	ConfigurableApplicationContext context=
	  			     new ClassPathXmlApplicationContext(file);
		Waiter waiter=(Waiter)context.getBean("waiterAfter");
        waiter.serveCutomer("zhou");
		context.close();
	}
//	@Test
	public void testRound(){
		String file="aop-impl.xml";
	  	ConfigurableApplicationContext context=
	  			     new ClassPathXmlApplicationContext(file);
		Waiter waiter=(Waiter)context.getBean("waiterRound");
        waiter.serveCutomer("zhou");
		context.close();
	}
	@Test
	public void testAdvisor(){
	  	String file="aop-impl.xml";
	  	ConfigurableApplicationContext context=
	  			     new ClassPathXmlApplicationContext(file);
	  	//FMark:get waiter directly,not Proxy
		Waiter waiter=(Waiter)context.getBean("target");
        waiter.leadCustomer("zhou");
		context.close();
	}
}
