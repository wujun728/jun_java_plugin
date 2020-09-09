package com.github.ghsea.rpc.demo.client;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.github.ghsea.rpc.demo.server.service.Hello;

public class HelloTest {

	@Test
	public void testHello() throws InterruptedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("rpc-demo-client.xml");
		Hello helloClient = (Hello) ctx.getBean("helloClient");
		System.out.println("Client:" + JSON.toJSONString(helloClient));
		String ret = helloClient.sayHello();
		System.out.println(ret);
		System.out.println("Client is sleeping");
		TimeUnit.MINUTES.sleep(3);
		System.out.println("Client is to exist");
	}
}
