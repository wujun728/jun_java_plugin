package com.jun.plugin.utils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 */
@Service("springContextUtil")
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
	}

	private static void checkApplicationContext() {
		if (applicationContext == null)
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
	}
	//*****************************************************************************
	//*****************************************************************************
//private static ApplicationContext context;
//	
//	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-datasource.xml");
//	
//	public static void setApplicationContext(ApplicationContext arg0) throws BeansException {
//		SpringUtil.context = arg0;
//	}
//	
//	public static ApplicationContext getApplicationContext() {
//		return context;     
//	}
//	
//	public static Object getBean(String beanName) {
//		return context.getBean(beanName);
//	}  
	
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
	//*****************************************************************
	//*****************************************************************
	/*private static final ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");

	public static ApplicationContext getApplicationContext() {
		return ac;
	}

	public static Object getBean(String beanName) {
		return ac.getBean(beanName);
	}*/
	//*****************************************************************
	//*****************************************************************
	//*****************************************************************
	//*****************************************************************
	//*****************************************************************
	//*****************************************************************
	//*****************************************************************
/*private static ApplicationContext context;
	
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-datasource.xml");
	
	public static void setApplicationContext(ApplicationContext arg0) throws BeansException {
		SpringUtil.context = arg0;
	}
	
	public static ApplicationContext getApplicationContext() {
		return context;     
	}
	
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}  */
	
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

	 // Spring应用上下文环境   
//	private static ApplicationContext applicationContext;   
//
//	/**  
//	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境  
//	 *   
//	 * @param applicationContext  
//	 */  
//	public void setApplicationContext1(ApplicationContext applicationContext) {   
//	    SpringUtil.applicationContext = applicationContext;   
//	}   
//
//	/**  
//	 * @return ApplicationContext  
//	 */  
//	public static ApplicationContext getApplicationContext1() {   
//	    return applicationContext;   
//	}   
//
//	/**  
//	 * 获取对象  
//	 * 这里重写了bean方法，起主要作用  
//	 * @param name  
//	 * @return Object 一个以所给名字注册的bean的实例  
//	 * @throws BeansException  
//	 */  
//	public static Object getBean1(String name) throws BeansException {   
//	    return applicationContext.getBean(name);   
//	}
	
	
	//*****************************************************************************
	//*****************************************************************************
	//*****************************************************************************
	/*private static ApplicationContext context;

	//隐藏构造器
	private SpringBeanUtils(){}
	
	public static ApplicationContext init(String path){
		if(context == null){
			context = new ClassPathXmlApplicationContext(path);
		}
		return context;
	}
	
	public static Object getBean(String beanName){
		return context.getBean(beanName);
	}
	
	public static <T>T getBean(String beanName, Class<T> clazz){
		return (T) context.getBean(beanName, clazz);
	}*/
	//*****************************************************************************
	//*****************************************************************************
	//*****************************************************************************
}
