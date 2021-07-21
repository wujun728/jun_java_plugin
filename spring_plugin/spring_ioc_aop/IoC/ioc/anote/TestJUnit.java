package ioc.anote;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJUnit {

	@Test
	public void JTest1(){
	    final String file="ioc-anote.xml";		
		ConfigurableApplicationContext 
		        appContext=new  ClassPathXmlApplicationContext(file);
		Person p=(Person) appContext.getBean("person");
		String carColor=p.getCar().getColor();
		System.out.println(carColor);
		appContext.close();
	}
}
