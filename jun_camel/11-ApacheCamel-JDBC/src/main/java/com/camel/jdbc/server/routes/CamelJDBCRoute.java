package com.camel.jdbc.server.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class CamelJDBCRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("timer://queryAward?period=3s").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {

				Message message = exchange.getOut();
				message.setBody(
						"insert into test(id,name,age,phone,des) values ('1','tom','12','110','0000');");
			}
		}).to("jdbc:DataSource").to("log:JDBCRoutesTest?showExchangeId=true");

	}

}
