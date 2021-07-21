package com.neo.cache.service.jedis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neo.cache.core.CacheManager;
@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:spring/spring-cache.xml") // 加载配置
public class JedisCacheManagerTest {
	
	@Resource
	private CacheManager cacheManager;
	
	@Test
	public void testFor() {
		for(int i=0;i<100000;i++){
			String key=i+"fly";
			cacheManager.putCache(key, "neo String", 1);
			Object r = cacheManager.getCache(key);
			System.out.println(r);
			
			if(cacheManager.exists(key)){
				System.out.println("存在"+key);
			}
			cacheManager.setKeyExpire(key, 36000);
			
			CacheObject co = new CacheObject();
			co.setKey(i);
			co.setValue(key+"  CacheObject");
			cacheManager.putCache("cacheObject", co);
			CacheObject cacheObject2 = (CacheObject) cacheManager.getCache("cacheObject");
			System.out.println("key : " + cacheObject2.getKey());
			System.out.println("value : " + cacheObject2.getValue());
			
			System.out.println("已经执行了 "+i+" 多少个");

		}
		
	}

	
	@Test
	public void testSigel() {
		    int i=1;
			String key=i+"xiaoming";
			cacheManager.putCache(key, "neo String", 1);
			Object r = cacheManager.getCache(key);
			System.out.println(r);
			
			if(cacheManager.exists(key)){
				System.out.println("存在");
			}
			cacheManager.setKeyExpire(key, 36000);
			
		/*	CacheObject co = new CacheObject();
			co.setKey(1243);
			co.setValue("ssss");
			cacheManager.putCache("cacheObject", co);
			CacheObject cacheObject2 = (CacheObject) cacheManager.getCache("cacheObject");
			System.out.println("key : " + cacheObject2.getKey());
			System.out.println("value : " + cacheObject2.getValue());*/
			
			System.out.println("已经执行了 "+i+" 多少个");
		
	}
	@Test
	public void listTest () {
		String cacheKey = "listTest";
//		cacheManager.removeCache(cacheKey);
//		showCacheValue(cacheKey);
//		for (int i = 1; i <= 5; i++) {
//			CacheObject co = new CacheObject();
//			co.setKey(i);
//			co.setValue("value" + i);
//			cacheManager.putListCache(cacheKey, co);
//		}
		showCacheValue(cacheKey);
		
		CacheObject co = new CacheObject();
		co.setKey(88);
		co.setValue("addValue" + 88);
		cacheManager.putListCache(cacheKey, co, -1);
		cacheManager.putListCache(cacheKey, co);
		showCacheValue(cacheKey);
	}
	
	private void showCacheValue (String cacheKey) {
		Object objValue = cacheManager.getListCache(cacheKey);
		System.out.println(objValue);
	}
}
