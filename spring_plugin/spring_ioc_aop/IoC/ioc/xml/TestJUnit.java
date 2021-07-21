package ioc.xml;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJUnit {

	@Test
	public void testXmlIoC(){
		final String files[]={"ioc-xml.xml"};
		ClassPathXmlApplicationContext context=
		  new ClassPathXmlApplicationContext(files);
	  Student stu1=(Student) context.getBean("student1");	
	  Student stu2=(Student) context.getBean("student2");	
	  System.out.println(stu1.getBook().getId());
	  System.out.println(stu2.getBook().getName());
	  context.close();
	}
}
