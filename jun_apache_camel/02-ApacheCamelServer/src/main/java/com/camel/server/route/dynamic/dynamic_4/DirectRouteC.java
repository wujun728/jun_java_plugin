package com.camel.server.route.dynamic.dynamic_4;

import org.apache.camel.builder.RouteBuilder;

public class DirectRouteC extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// 第二个路由和第三个路由的代码都相似
		// 唯一不同的是类型
		from("direct:directRouteC").to("log:DirectRouteC?showExchangeId=true");
	}

}
