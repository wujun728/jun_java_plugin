package com.camel.webservice.server;

import javax.xml.ws.Endpoint;

public class Server {

	public void start() {

		String address = "http://localhost:9022/camel-cxf/greeter-service";
		Endpoint.publish(address, new CamelCXFServiceImpl());
		System.out.println("WebService 发布成功 , address : " + address);

	}

}
