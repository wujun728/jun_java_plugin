/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		proxy.Test.java
 * Class:			Test
 * Date:			2012-5-7
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package proxy;


/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-5-7 上午11:04:43 
 */

public class Test {

	/**  
	 * 描述
	 * @param args  
	 */
	public static void main(String[] args) {
		RealSubject subject = new RealSubject();
		subject.request();
		Proxy proxy = new Proxy(subject);
		proxy.request();
		
		// 动态代理
		DynamicProxy dynamicProxy = new DynamicProxy();
		Subject proxySubject = dynamicProxy.getProxyInterface(subject);
		proxySubject.request();
		
	}

}
