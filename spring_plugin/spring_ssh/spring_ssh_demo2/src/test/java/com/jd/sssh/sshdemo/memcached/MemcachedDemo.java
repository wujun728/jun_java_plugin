/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jd.sssh.sshdemo.memcached;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.jd.ssh.sshdemo.memcached.SpyMemcachedClient;
import com.jd.ssh.sshdemo.test.spring.SpringTransactionalTestCase;

@ContextConfiguration(locations = { "/cache/applicationContext-memcached.xml", "/applicationContext.xml" })
public class MemcachedDemo extends SpringTransactionalTestCase {

	@Autowired
	private SpyMemcachedClient spyMemcachedClient;

	@Test
	public void normal() {

		String key = "consumer:1";
		String value = "admin";

		spyMemcachedClient.set(key, 60 * 60 * 1, value);

		String result = spyMemcachedClient.get(key);
		
		assertEquals("equals",result,value);
		logger.info("result: " + result);
		spyMemcachedClient.delete(key);
		result = spyMemcachedClient.get(key);
		logger.info("result: " + result);
		assertNull("null",result);
		//assertNotNull();
	}

	@Test
	public void safeDelete() {
		String key = "consumer:1";
		spyMemcachedClient.set(key, 60, "admin");
		assertTrue(spyMemcachedClient.safeDelete(key));
		assertFalse(spyMemcachedClient.safeDelete("consumer:1"));
	}

	@Test
	public void getBulk() {

		String key1 = "consumer:1";
		String value1 = "admin";

		String key2 = "consumer:2";
		String value2 = "calvin";

		String key3 = "invalidKey";

		spyMemcachedClient.set(key1, 60 * 60 * 1, value1);
		spyMemcachedClient.set(key2, 60 * 60 * 1, value2);

		Map<String, String> result = spyMemcachedClient.getBulk(Lists.newArrayList(key1, key2));
		
		assertEquals(result.get(key1),value1);
		assertEquals(result.get(key2),value2);
		assertNull(result.get(key3));
		
	}

	@Test
	public void incr() {
		String key = "incr_key";

		// 注意,incr返回的数值使用long表达
		long result = spyMemcachedClient.incr(key, 2, 1);
		assertEquals(result,1);
		//assertThat(result).isEqualTo(1);
		// 注意,get返回的数值使用字符串表达
		assertEquals(spyMemcachedClient.get(key),"1");
		//assertThat(spyMemcachedClient.get(key)).isEqualTo("1");
		
		assertEquals(spyMemcachedClient.incr(key, 2, 1),3);
		assertEquals(spyMemcachedClient.get(key),"3");
		
		//assertThat(spyMemcachedClient.incr(key, 2, 1)).isEqualTo(3);
		//assertThat(spyMemcachedClient.get(key)).isEqualTo("3");

		key = "set_and_incr_key";
		// 注意,set中的数值必须使用字符串,后面的incr操作结果才会正确.
		spyMemcachedClient.set(key, 60 * 60 * 1, "1");
		//assertThat(spyMemcachedClient.incr(key, 2, 1)).isEqualTo(3);
		assertEquals(spyMemcachedClient.incr(key, 2, 1),3);
	}
}
