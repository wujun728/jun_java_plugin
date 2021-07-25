package org.ssm.dufy.aspectj;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 添加AOP功能
 * 
 * 1》pom.xml 加入AOP需要的Jar包
 * 2》在applicationContext.xml 和 Spring-mvc.xml 中加入AOP的命名空间并加入
 * 	 <aop:aspectj-autoproxy proxy-target-class="true"/>
 * 3》写AOP进行测试
 * 
 * @author dufy
 * 
 */
@Aspect
@Component
public class ShiroAOP {
	private static Logger log = Logger.getLogger(ShiroAOP.class);
	
	/**
	 * 以决定是否能调用相应的功能
	 * 只拦截Controller中定义方法的请求
	 * @param jp
	 * @throws Exception 
	 */
	/*@Before("execution(* org.ssm.dufy.web.UserController*.*(..))")*/
	public void hasPermitsXML(JoinPoint jp) throws Exception  {

		System.out.println("------------------------");
		String str = "";
		if(str  == ""){
			throw new Exception();
		}
		
	}

	

}