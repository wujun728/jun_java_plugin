package com.camel.server.route.dynamic.dynamic_3;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpMessage;

public class DirectRouteBRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:directRouteBRouteBuilder").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {

				// 因为很明确消息格式是http的，所以才使用这个类
				// 否则还是建议使用org.apache.camel.Message这个抽象接口
				HttpMessage message = (HttpMessage) exchange.getIn();
				String bodyStream = (String) message.getBody();
				System.out.println("B bodyStream  -- " + bodyStream);

				// 存入到exchange的out区域
				if (exchange.getPattern() == ExchangePattern.InOut) {
					Message outMessage = exchange.getOut();
					outMessage.setBody(bodyStream + " || out");
				}

			}
		}).to("log:directRouteBRouteBuilder?showExchangeId=true");
	}

}
