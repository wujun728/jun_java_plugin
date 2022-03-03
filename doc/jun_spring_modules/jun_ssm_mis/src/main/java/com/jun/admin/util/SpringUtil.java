package com.jun.admin.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {

	private static ApplicationContext context;
	
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-datasource.xml");
	
	public static void setApplicationContext(ApplicationContext arg0) throws BeansException {
		SpringUtil.context = arg0;
	}
	
	public static ApplicationContext getApplicationContext() {
		return context;     
	}
	
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}  
	
	// private static ApplicationContext ctx1 = new ClassPathXmlApplicationContext("applicationContext.xml");
	// private static ApplicationContext ctx2 = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml","applicationContext-datasource.xml"});
//	 private static ApplicationContext ctx2 = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml.xml","applicationContext-datasource.xml"});
	
	// Resource resource = new ClassPathResource("appcontext.xml");
	// BeanFactory factory = new XmlBeanFactory(resource);
	
	// ClassPathXmlApplicationContext使用了file前缀是可以使用绝对路径的
	// ApplicationContext factory = new ClassPathXmlApplicationContext("file:F:/workspace/example/src/appcontext.xml");
	
	// ApplicationContext factory = new FileSystemXmlApplicationContext("src/appcontext.xml");
	// ApplicationContext factory = new FileSystemXmlApplicationContext("webRoot/WEB-INF/appcontext.xml");
	
	// ApplicationContext factory = new FileSystemXmlApplicationContext("classpath:appcontext.xml");
	// ApplicationContext factory = new FileSystemXmlApplicationContext("file:F:/workspace/example/src/appcontext.xml");
	
//    private static ApplicationContext ctx3 = new ClassPathXmlApplicationContext("classpath:application*.xml");
    
//    public static Object getBean(String beanName){
//         return ctx3.getBean(beanName);
//    }    
    
}
