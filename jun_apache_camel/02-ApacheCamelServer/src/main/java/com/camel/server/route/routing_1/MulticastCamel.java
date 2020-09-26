package com.camel.server.route.routing_1;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.log4j.PropertyConfigurator;

public class MulticastCamel {

	public static void main(String[] args) throws Exception {

		// 日志
		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		// 这是camel上下文对象，整个路由的驱动全靠它了。
		ModelCamelContext camelContext = new DefaultCamelContext();

		// 启动route
		camelContext.start();

		// 将我们编排的一个完整消息路由过程，加入到上下文中
		camelContext.addRoutes(new MulticastCamelRouteBuilder());

		// 通用没有具体业务意义的代码，只是为了保证主线程不退出
		synchronized (MulticastCamel.class) {
			MulticastCamel.class.wait();
		}

	}
}
