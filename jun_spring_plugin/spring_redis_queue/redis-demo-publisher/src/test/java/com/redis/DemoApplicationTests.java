package com.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private  Publisher publisher;

	@Test
	public void startPublisher() {
		System.out.println("发布消息");
		//Person person = new Person("redis","10","x");
		publisher.publish("testChannel","渠道1消息");
		publisher.publish("testChannel2","渠道2消息");
	}

}

