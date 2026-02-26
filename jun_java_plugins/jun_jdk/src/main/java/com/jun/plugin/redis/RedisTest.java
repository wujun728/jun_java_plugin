// This file is commented out — functionality moved to jun_redis module.
// See jun_redis for Redis client examples.
/*
package com.jun.plugin.redis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisTest {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1");
		System.out.println("连接成功");
		System.out.println("服务正在运行: " + jedis.ping());

		jedis.lpush("list", "redis");
		jedis.lpush("list", "java");
		jedis.lpush("list", "mysql");
		List<String> list = jedis.lrange("list", 0, 2);
		for (int i = 0, j = list.size(); i < j; i++) {
			System.out.println("list的输出结果:" + list.get(i));
		}

		jedis.set("rst", "redisStringTest");
		System.out.println("redis 存储的字符串为: " + jedis.get("rst"));

		jedis.sadd("setTest1", "abc");
		jedis.sadd("setTest1", "abcd");
		jedis.sadd("setTest1", "abcde");
		Set<String> keys = jedis.keys("*");
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			System.out.println(key);
		}

	}

}
*/
