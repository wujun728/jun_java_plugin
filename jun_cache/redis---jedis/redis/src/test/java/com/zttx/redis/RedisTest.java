package com.zttx.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zttx.redis.bean.U;
import com.zttx.redis.core.RedisTemplate;

/**
 * Unit test for simple App.
 */
public class RedisTest 
    extends TestCase
{
	private ApplicationContext app;  
	private RedisTemplate redisTemplate;
	
	@Before  
	public void before() throws Exception {  
	    app = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");  
	}  
	  
	@Test  
	public void test() throws InterruptedException {  
		 app = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");  
		 redisTemplate = (RedisTemplate) app.getBean("redisTemplate");  
		 
		 //清空所有
		// redisTemplate.clearAll();
		// System.out.println(redisTemplate.get("123"));
		 //普通字符串
		 redisTemplate.set("123", "redis1");
		 //TimeUnit.SECONDS.sleep(3);
		 System.out.println(redisTemplate.getLifeTime("123"));
		 Object o = redisTemplate.get("123");
		 System.out.println(o);

		 //对象
		 U u = new U();
		 u.setId("111");
		 u.setName("测试");
		 redisTemplate.set("u", u);
		 u = (U) redisTemplate.get("u");
		 System.out.println(u);
		 
		 //List字符串
		 List<String> list = new ArrayList<>();
		 list.add("6");list.add("7");list.add("8");
		 Long l = redisTemplate.lPush("list", list.toArray());
		 System.out.println(redisTemplate.lrange("list", 0, l));
		 List<String> list2 = new ArrayList<>();
		 list2.add("55");
		 Long l2 = redisTemplate.lPush("list", list2.toArray());
		 System.out.println(redisTemplate.lrange("list", 0, l2));
		 
		 
		 
		 //List对象
		 U u1 = new U();
		 u1.setId("111");
		 u1.setName("测试");
		 U u2 = new U();
		 u2.setId("111");
		 u2.setName("测试");
		 U u3 = new U();
		 u3.setId("111");
		 u3.setName("测试");
		 List<U> li = new ArrayList<>();
		 li.add(u1);li.add(u2);li.add(u3);
		 //System.out.println(li);
		 Long ul = redisTemplate.lPush("ulist", li.toArray());
		 System.out.println(redisTemplate.lrange("ulist", 0, ul));
		 
		 Map<String, Object> map = new HashMap<>();
		 map.put("123", "你好啊");
		 redisTemplate.hset("map", map);
		 
		 boolean f = redisTemplate.hisExists("map", "321");
		 System.out.println(f);
	} 
	
	

}
