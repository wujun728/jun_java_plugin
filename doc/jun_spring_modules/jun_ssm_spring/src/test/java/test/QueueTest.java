package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springrain.frame.queue.SendMessage;
import org.springrain.frame.queue.RedisMessageDelegateListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class QueueTest  {
	
	
	@Resource
	 SendMessage sendMessage;
	
	@Resource
	RedisMessageDelegateListener redisMessageDelegateListener;
	
	@Test
	public void sendMessage() throws Exception{
		
		
		sendMessage.sendMessage("springrainqueue", "hello1");
		sendMessage.sendMessage("springrainqueue", "hello2");
		
		Thread.sleep(100000);
		
	
		
		System.out.println("----------------------");
	
	}
	
	
}
