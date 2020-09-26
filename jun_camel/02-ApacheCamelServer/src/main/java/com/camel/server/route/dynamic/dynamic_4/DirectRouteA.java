package com.camel.server.route.dynamic.dynamic_4;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class DirectRouteA extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("jetty:http://0.0.0.0:8282/directMain")
				.setExchangePattern(ExchangePattern.InOut)
				.recipientList().jsonpath("$.data.routeName")
				.delimiter(",")
				.end()
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {

						Message message = exchange.getIn();

						System.out.println("OtherProcessor中的exchange" + exchange);

						InputStream body = (InputStream) message.getBody();
						String str = IOUtils.toString(body, "UTF-8");

						System.out.println("OtherProcessor str : " + str);

						// 存入到exchange的out区域
						if (exchange.getPattern() == ExchangePattern.InOut) {
							Message outMessage = exchange.getOut();
							outMessage.setBody(str + " || 被OtherProcessor处理");
						}

					}
				});
	}

}
