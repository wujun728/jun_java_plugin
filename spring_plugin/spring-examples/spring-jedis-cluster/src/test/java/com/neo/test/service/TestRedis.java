package com.neo.test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.JedisCluster;

import com.neo.entity.UserEntity;
import com.neo.redis.CacheManager;

@ContextConfiguration(locations = { "classpath:/test*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedis {
    
    @Resource
	private JedisCluster jedisCluster;
        
    @Resource
    private CacheManager cacheManager;

	@Test
	public void testRedisCluster() {
	    jedisCluster.set("zx_hello", "hi  welocme babyxx!");
	    String value=jedisCluster.get("zx_hello");
	    System.out.println(value);
	}
	
	@Test
	public void testObject() {
	    UserEntity user=new UserEntity();
	    user.setId(2l);
	    user.setUserName("梅超风");
	    user.setPassWord("@#$%^&FG_");
	    cacheManager.putCache("MEI", user);
	    UserEntity userG=(UserEntity) cacheManager.getCache("MEI");
	    System.out.println(userG.toString());
	}
    
    

}

