package com.camel.jdbc.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class CamelJDBCProcesser implements Processor {

	public static final Logger logger = Logger.getLogger(CamelJDBCProcesser.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		String str = exchange.getIn().getBody().toString();
		System.out.println("str : " + str);

		Object obj = exchange.getIn().getBody();
		System.out.println("obj : " + obj.getClass());
		System.out.println("obj : " + obj);
		if(exchange.getPattern() == ExchangePattern.InOut){
			exchange.getOut().setBody(exchange.getIn().getBody());
		}
	}

}
