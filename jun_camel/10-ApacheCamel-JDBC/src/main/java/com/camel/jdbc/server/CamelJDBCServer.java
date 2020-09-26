package com.camel.jdbc.server;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.model.ModelCamelContext;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CamelJDBCServer {

	public static final Logger logger = Logger.getLogger(CamelJDBCServer.class);

	public static void main(String[] args) {

		final String url = "jdbc:mysql://120.79.190.67:3306/test";

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUsername("root");
		basicDataSource.setPassword("qwe!@#");
		basicDataSource.setUrl(url);
		SimpleRegistry simpleregistry = new SimpleRegistry();
		simpleregistry.put("DataSource", basicDataSource);

		// 日志
		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		try {

			ModelCamelContext camelContext = new DefaultCamelContext(simpleregistry);
			camelContext.start();

			camelContext.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("timer://queryAward?period=1s").setBody(constant("select * from test where id > 0 "))
							.to("jdbc:DataSource?outputType=SelectList").process(new Processor() {

								@Override
								public void process(Exchange exchange) throws Exception {

									System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
									System.out.println(exchange.toString());

									String str = exchange.getIn().getBody().toString();
									System.out.println("str : " + str);

									Object obj = exchange.getIn().getBody();
									System.out.println("obj : " + obj.getClass());
									System.out.println("obj : " + obj);

								}
							}).to("log:JDBCRoutesTest?showExchangeId=true");
				}
			});

			// 没有具体业务意义的代码，只是为了保证主线程不退出
			synchronized (CamelJDBCServer.class) {
				CamelJDBCServer.class.wait();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
