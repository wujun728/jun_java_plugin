package com.camel.server.route.dynamic.dynamic_2;

import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpMessage;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.PropertyConfigurator;

/**
 * 模拟同时，发布两个http服务，不同的url，处理不同的业务逻辑
 * 
 * @author CYX
 * @time 2017年12月15日下午3:09:27
 */
public class DirectCamel {

	public static void main(String[] args) throws Exception {

		// 加载日志
		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		// 这是camel上下文对象，整个路由的驱动全靠它了。
		ModelCamelContext camelContext = new DefaultCamelContext();

		// 启动route
		camelContext.start();

		// 首先将两个完整有效的路由注册到Camel服务中
		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				// 连接路由：DirectRouteB
				from("jetty:http://0.0.0.0:8282/directCamelaa").process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {

						// 因为很明确消息格式是http的，所以才使用这个类
						// 否则还是建议使用org.apache.camel.Message这个抽象接口
						HttpMessage message = (HttpMessage) exchange.getIn();
						InputStream bodyStream = (InputStream) message.getBody();
						String inputContext = IOUtils.toString(bodyStream, "UTF-8");

						System.out.println("A inputContext -- " + inputContext);

						bodyStream.close();

						// 存入到exchange的out区域
						if (exchange.getPattern() == ExchangePattern.InOut) {
							Message outMessage = exchange.getOut();
							outMessage.setBody(inputContext + " || aaa");
						}

					}
				}).to("log:directRouteARouteBuilder?showExchangeId=true");

			}
		});

		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("jetty:http://0.0.0.0:8282/directCamelbb").process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {

						// 因为很明确消息格式是http的，所以才使用这个类
						// 否则还是建议使用org.apache.camel.Message这个抽象接口
						HttpMessage message = (HttpMessage) exchange.getIn();
						InputStream bodyStream = (InputStream) message.getBody();
						String inputContext = IOUtils.toString(bodyStream, "UTF-8");

						System.out.println("B inputContext  -- " + inputContext);

						// 存入到exchange的out区域
						if (exchange.getPattern() == ExchangePattern.InOut) {
							Message outMessage = exchange.getOut();
							outMessage.setBody(bodyStream + " || outbbbb");
						}

					}
				}).to("log:directRouteBRouteBuilder?showExchangeId=true");
			}
		});

		// 通用没有具体业务意义的代码，只是为了保证主线程不退出
		synchronized (DirectCamel.class) {
			DirectCamel.class.wait();
		}

	}
}
