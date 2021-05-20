package com.puff.storage.redis;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.puff.storage.Storage;

import redis.clients.jedis.BinaryJedisCluster;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis储存
 * @author Wujun
 *
 */
public class RedisStorage implements Storage {

	private final Redis redis = Redis.getInstance();

	@Override
	public void start(Properties props) {
		props = getProviderProperties(props);
		Integer timeout = getProperty(props, "timeout", 2000);
		String password = getProperty(props, "password", null);
		Integer database = getProperty(props, "database", 1);
		String clientName = getProperty(props, "clientName", null);
		JedisPoolConfig jpc = new JedisPoolConfig();
		boolean testWhileIdle = getProperty(props, "testWhileIdle", false);
		jpc.setTestWhileIdle(testWhileIdle);
		long minEvictableIdleTimeMillis = getProperty(props, "minEvictableIdleTimeMillis", 1000);
		jpc.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		long timeBetweenEvictionRunsMillis = getProperty(props, "timeBetweenEvictionRunsMillis", 10);
		jpc.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		int numTestsPerEvictionRun = getProperty(props, "numTestsPerEvictionRun", 10);
		jpc.setNumTestsPerEvictionRun(numTestsPerEvictionRun);

		jpc.setLifo(getProperty(props, "lifo", false));
		jpc.setMaxTotal(getProperty(props, "maxTotal", 500));
		jpc.setMinIdle(getProperty(props, "minIdle", 20));
		jpc.setMaxIdle(getProperty(props, "maxIdle", 200));
		jpc.setMaxWaitMillis(getProperty(props, "maxWaitMillis", 3000));

		jpc.setTestWhileIdle(getProperty(props, "testWhileIdle", false));
		jpc.setTestOnBorrow(getProperty(props, "testOnBorrow", true));
		jpc.setTestOnReturn(getProperty(props, "testOnReturn", false));
		jpc.setTestOnCreate(getProperty(props, "testOnCreate", false));
		Integer maxRedirections = getProperty(props, "maxRedirections", 20);

		JedisPool jedisPool = null;
		BinaryJedisCluster jedisCluster = null;

		String address = getProperty(props, "address", "127.0.0.1:6379");
		String[] arr = address.split(",");
		if (arr != null && arr.length > 1) {
			Set<HostAndPort> set = new HashSet<HostAndPort>();
			for (String server : arr) {
				String[] tmp = server.split(":");
				if (tmp.length >= 2) {
					set.add(new HostAndPort(tmp[0], Integer.parseInt(tmp[1])));
				}
			}
			jedisCluster = new BinaryJedisCluster(set, timeout, maxRedirections, jpc);
		} else {
			String[] server = arr[0].split(":");
			String host = server[0];
			Integer port = Integer.parseInt(server[1]);
			if (port != null && timeout != null && password != null && database != null && database != -1 && clientName != null)
				jedisPool = new JedisPool(jpc, host, port, timeout, password, database, clientName);
			else if (port != null && timeout != null && password != null && database != null && database != -1)
				jedisPool = new JedisPool(jpc, host, port, timeout, password, database);
			else if (port != null && timeout != null && password != null)
				jedisPool = new JedisPool(jpc, host, port, timeout, password);
			else if (port != null && timeout != null)
				jedisPool = new JedisPool(jpc, host, port, timeout);
			else
				jedisPool = new JedisPool(jpc, host, port);
		}
		redis.init(jedisPool, jedisCluster);
	}

	@Override
	public void close() {
		redis.close();
	}

	@Override
	public void put(String key, Object value, int expiredTime) {
		if (expiredTime <= 0) {
			redis.set(key, value);
		} else {
			redis.setex(key, expiredTime, value);
		}
	}

	@Override
	public <T> T get(String key) {
		return redis.get(key);
	}

	@Override
	public void remove(String key) {
		redis.remove(key);
	}

	@Override
	public void expire(String key, int expiredTime) {
		redis.expire(key, expiredTime);
	}

	private final static Properties getProviderProperties(Properties props) {
		Properties new_props = new Properties();
		Enumeration<Object> keys = props.keys();
		String prefix = "redis.";
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (key.startsWith(prefix)) {
				new_props.setProperty(key.substring(prefix.length()), props.getProperty(key));
			}
		}
		return new_props;
	}

	private static String getProperty(Properties props, String key, String defaultValue) {
		String value = props.getProperty(key, defaultValue);
		return value == null ? value : value.trim();
	}

	private static int getProperty(Properties props, String key, int defaultValue) {
		try {
			String value = props.getProperty(key);
			return value == null ? defaultValue : Integer.parseInt(value.trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	private static boolean getProperty(Properties props, String key, boolean defaultValue) {
		return "true".equalsIgnoreCase(props.getProperty(key, String.valueOf(defaultValue)).trim());
	}

}
