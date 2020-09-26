package com.camel.timer.server;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CamelTimerServer {

	public static final Logger logger = Logger.getLogger(CamelTimerServer.class);

	public static void main(String[] args) {

		// 日志
		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		try {

			ModelCamelContext camelContext = new DefaultCamelContext();

			camelContext.start();

			camelContext.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("timer://myTimer?period=1s").setBody().simple("Current time is ${header.firedTime}").to("log:CamelTimerServer?showExchangeId=true");
				}
			});

			// 没有具体业务意义的代码，只是为了保证主线程不退出
			synchronized (CamelTimerServer.class) {
				CamelTimerServer.class.wait();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
