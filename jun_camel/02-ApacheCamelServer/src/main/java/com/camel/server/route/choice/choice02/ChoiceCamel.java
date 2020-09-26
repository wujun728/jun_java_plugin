package com.camel.server.route.choice.choice02;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.log4j.PropertyConfigurator;

/**
 * 基于内容的路由
 * 
 * @author CYX
 * @time 2017年12月15日下午3:23:11
 */
public class ChoiceCamel {

	public static void main(String[] args) throws Exception {

		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		ModelCamelContext camelContext = new DefaultCamelContext();

		camelContext.start();

		//camelContext.addRoutes(new ChoiceCamelRouteBuilder());
		camelContext.addRoutes(new ChoiceCamelRouteBuilder2());

		// 通用没有具体业务意义的代码，只是为了保证主线程不退出
		synchronized (ChoiceCamel.class) {
			ChoiceCamel.class.wait();
		}
	}

}
