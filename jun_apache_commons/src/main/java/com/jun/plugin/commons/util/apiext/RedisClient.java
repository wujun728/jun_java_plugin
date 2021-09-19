package com.jun.plugin.commons.util.apiext;

import java.util.Map;
import java.util.Properties;

import com.jun.plugin.commons.util.Conf;
import com.jun.plugin.commons.util.exception.ProjectException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClient {
	private static JedisPool jedisPool;// 非切片连接池

	private final static Object lockObj = new Object();

	/****
	 * 通过配置得到 Jedis
	 * 
	 * @param connProp
	 * @return
	 * @throws ProjectException
	 */
	public static Jedis getConnection(Properties connProp) {
		if (connProp == null || connProp.size() == 0) {
			return null;
		}
		if (jedisPool == null) {
			synchronized (lockObj) {
				String appName = (String) connProp.get("defaultRedisName");
				Map<String, String> confMap = Conf.getRedisServerPropByKey(
						connProp, appName);
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(Integer.parseInt(confMap.get("maxTotal")));
				config.setMaxIdle(Integer.parseInt(confMap.get("maxIdle")));
				config.setMaxWaitMillis(Integer.parseInt(confMap
						.get("maxWaitMillis")));
				config.setTestOnBorrow(Boolean.parseBoolean(confMap
						.get("testOnBorrow")));
				jedisPool = new JedisPool(config, confMap.get("host"),
						Integer.parseInt(confMap.get("port")));
			}
		}
		return jedisPool.getResource();
	}

	/***
	 * 放资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(Jedis jedis) {
		jedis.close();
		if (jedisPool != null) {
			jedisPool.close();
		}
	}

}
