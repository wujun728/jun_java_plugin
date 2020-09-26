package com.camel.server.route.dynamic.dynamic_4;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.log4j.PropertyConfigurator;

/**
 * 处理Dynamic Recipient List，感觉缺少jar包
 * 
 * @author CYX
 * @time 2017年12月18日上午11:11:28
 */
public class DirectMain {

	/**
	 * {"data":{"routeName":"direct:directRouteB,direct:directRouteC"},"token":"d9c33c8f-ae59-4edf-b37f-290ff208de2e","desc":""}
	 * */
	public static void main(String[] args) throws Exception {

		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		// 这是camel上下文对象，整个路由的驱动全靠它了。
		ModelCamelContext camelContext = new DefaultCamelContext();

		// 启动route
		camelContext.start();

		// 将我们编排的一个完整消息路由过程，加入到上下文中
		camelContext.addRoutes(new DirectRouteA());
		camelContext.addRoutes(new DirectRouteB());
		camelContext.addRoutes(new DirectRouteC());

		// 通用没有具体业务意义的代码，只是为了保证主线程不退出
		synchronized (DirectMain.class) {
			DirectMain.class.wait();
		}

	}

}
