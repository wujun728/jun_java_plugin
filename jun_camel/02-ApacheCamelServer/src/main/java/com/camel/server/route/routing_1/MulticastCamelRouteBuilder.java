package com.camel.server.route.routing_1;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.MulticastDefinition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MulticastCamelRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// 这个线程池用来进行multicast中各个路由线路的并发执行
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		MulticastDefinition multicastDefinition = from("jetty:http://0.0.0.0:8282/multicastCamel").multicast();

		// multicast 中的消息路由可以顺序执行也可以并发执行
		// 这里我们演示并发执行
		multicastDefinition.setParallelProcessing(true);

		// 为并发执行设置一个独立的线程池
		multicastDefinition.setExecutorService(executorService);

		// 注意，multicast中各路由路径的Excahnge都是基于上一路由元素的excahnge复制而来
		// 无论前者Excahnge中的Pattern如何设置，其处理结果都不会反映在最初的Excahnge对象中
		multicastDefinition.to("log:helloworld1?showExchangeId=true", "log:helloworld2?showExchangeId=true")
					// 一定要使用end，否则OtherProcessor会被做为multicast中的一个分支路由
					.end()
					// 所以您在OtherProcessor中看到的Excahnge中的Body、Header等属性内容
					// 不会有“复制的Exchange”设置的任何值的痕迹
					.process(new OtherProcessor());
	}

}
