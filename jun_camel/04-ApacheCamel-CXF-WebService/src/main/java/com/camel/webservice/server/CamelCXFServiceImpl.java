package com.camel.webservice.server;

public class CamelCXFServiceImpl implements CamelCXFServiceInter {

	@Override
	public String queryInfomation(String arg0) {
		System.out.println("queryInfomation : " + arg0);
		return "hhhhhh";
	}

	@Override
	public String sayHello(String arg0) {
		System.out.println("sayHello : " + arg0);
		return "xxxxxxxx";
	}

}
