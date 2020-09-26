package com.camel.server.route.file.file_3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.MulticastDefinition;
import org.apache.log4j.PropertyConfigurator;

/**
 * Apache Camel File组件<br>
 * File组件-使用线程池
 * 
 * @author CYX
 * @time 2018年1月2日下午2:28:54
 */
public class CamelFileThreadPool {

	public static void main(String[] args) throws Exception {

		// 日志
		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		final ExecutorService executor = new ThreadPoolExecutor(10, 15, 3, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());

		// 这是camel上下文对象，整个路由的驱动全靠它了。
		ModelCamelContext camelContext = new DefaultCamelContext();

		// 启动route
		camelContext.start();

		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				MulticastDefinition multicastDefinition = from("file:D:\\A\\inbox?delay=3000&delete=false&charset=UTF-8")
						.multicast();

				multicastDefinition.setParallelProcessing(true);

				multicastDefinition.setExecutorService(executor);

				multicastDefinition.process(new Processor() {

					public void process(Exchange exchange) throws Exception {

						Message fileMessage = exchange.getIn();
						log.info("文件名 : " + fileMessage.getBody());

						// 读取文件名
						// System.out.println("文件名 : " + fileMessage.getBody());
						// System.out.println("文件内容 : " +
						// fileMessage.getBody(String.class));

					}
				});// .to("log:CamelFileComponent_2?showExchangeId=true");
			}
		});

		// 通用没有具体业务意义的代码，只是为了保证主线程不退出
		synchronized (CamelFileThreadPool.class) {
			CamelFileThreadPool.class.wait();
		}

	}

}
