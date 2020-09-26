package com.camel.cxf.client;

import org.apache.hello_world_soap_http.Greeter;
import org.apache.hello_world_soap_http.SOAPService;

public class TestClient {

	public static void main(String[] args) throws Exception {

		SOAPService soapService = new SOAPService();

		Greeter greeter = null;

//		greeter = soapService.getSoapOverHttp();

		 greeter = soapService.getSoapOverHttpRouter();
		String result1 = greeter.greetMe("1234");
		greeter.greetMeOneWay("1234567");
		greeter.pingMe("1234567890");
		String result2 = greeter.sayHi();

		System.out.println("result1 : " + result1);
		System.out.println("result2 : " + result2);

	}

}
