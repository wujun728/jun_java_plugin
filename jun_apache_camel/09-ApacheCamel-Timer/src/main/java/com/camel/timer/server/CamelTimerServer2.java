package com.camel.timer.server;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CamelTimerServer2 {

	public static final Logger logger = Logger.getLogger(CamelTimerServer2.class);

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

					from("timer://foo?period=2s").process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							System.out.println("Hello world  :" + System.currentTimeMillis());
						}
					});

				}
			});

			// 没有具体业务意义的代码，只是为了保证主线程不退出
			synchronized (CamelTimerServer2.class) {
				CamelTimerServer2.class.wait();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
