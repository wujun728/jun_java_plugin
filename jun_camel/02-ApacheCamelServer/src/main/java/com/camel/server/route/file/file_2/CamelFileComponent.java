package com.camel.server.route.file.file_2;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.log4j.PropertyConfigurator;

/**
 * Apache Camel File组件<br>
 * File组件测试
 * 
 * @author CYX
 * @time 2018年1月2日下午2:28:27
 */
public class CamelFileComponent {

	public static void main(String[] args) throws Exception {

		// 日志
		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		// 这是camel上下文对象，整个路由的驱动全靠它了。
		ModelCamelContext camelContext = new DefaultCamelContext();

		// 启动route
		camelContext.start();

		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:D:\\A\\inbox?delay=3000&delete=false&charset=UTF-8").process(new Processor() {

					public void process(Exchange exchange) throws Exception {

						// 读取文件名
						Message fileMessage = exchange.getIn();
						System.out.println("文件名 : " + fileMessage);
						System.out.println("exchange.toString() : " + exchange.toString());

						// 读取文件中的内容
						String fileInfomation = fileMessage.getBody(String.class);
						System.out.println("文件内容 ：" + fileInfomation);

					}
				}).to("log:CamelFileComponent?showExchangeId=true");
			}
		});

		// 仅为了保证主线程不退出
		synchronized (CamelFileComponent.class) {
			CamelFileComponent.class.wait();
		}

	}

}
