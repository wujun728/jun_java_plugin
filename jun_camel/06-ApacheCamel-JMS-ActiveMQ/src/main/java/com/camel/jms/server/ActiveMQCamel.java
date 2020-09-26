package com.camel.jms.server;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.PropertyConfigurator;

import javax.jms.ConnectionFactory;
import java.io.*;

/**
 * 从本地目录读取文件，然后发送至mq队列中
 * 
 * @author CYX
 * @time 2017年12月20日下午2:16:40
 */
public class ActiveMQCamel {

	private static String user = "user";//ActiveMQConnection.DEFAULT_USER;

	private static String password = "123";//ActiveMQConnection.DEFAULT_PASSWORD;

	//临时测试
	public static final String DESTINATION = "spring-queue";

//	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static String url = "failover://tcp://127.0.0.1:61616";

	public static void main(String[] args) throws Exception {

		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		CamelContext context = new DefaultCamelContext();

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);

		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		System.out.println(url + " >>>>>>>>>>>>>>>>>>>>>>>>>>> " + user + " >>>>>>>>>>>>>>>>>>>>>>>>>>> " + password);

		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {// 发送过后就会被移动到.camel文件夹
				from("file:D:\\A\\inbox?delay=3000").process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						FileInputStream body = new FileInputStream((File)exchange.getIn().getBody(GenericFile.class).getFile());
						System.out.println(analysisMessage(body));
						System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::");
					}

					/**
					 * 从stream中分析字符串内容
					 *
					 * @param bodyStream
					 * @return
					 */
					private String analysisMessage(InputStream bodyStream) throws IOException {
						ByteArrayOutputStream outStream = new ByteArrayOutputStream();
						byte[] contextBytes = new byte[4096];
						int realLen;
						while ((realLen = bodyStream.read(contextBytes, 0, 4096)) != -1) {
							outStream.write(contextBytes, 0, realLen);
						}

						// 返回从Stream中读取的字串
						try {
							return new String(outStream.toByteArray(), "UTF-8");
						} finally {
							outStream.close();
							bodyStream.close();// 关掉才删的掉
						}
					}
				}).to("jms:"+DESTINATION);
			}
		});

		context.start();

		// 通用没有具体业务意义的代码，只是为了保证主线程不退出
		synchronized (ActiveMQCamel.class) {
			ActiveMQCamel.class.wait();
		}

	}

}
