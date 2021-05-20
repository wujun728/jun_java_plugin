package com.puff.storage.redis;

import java.io.IOException;

import com.puff.session.Serialize;

import redis.clients.jedis.BinaryJedisCluster;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

/**
 */
public class Redis {

	private JedisPool jedisPool;
	private BinaryJedisCluster jedisCluster;
	private boolean cluster = false;

	private static class Inner {
		private static final Redis REDIS = new Redis();
	}

	private Redis() {

	}

	public static Redis getInstance() {
		return Inner.REDIS;
	}

	protected void init(JedisPool jedisPool, BinaryJedisCluster jedisCluster) {
		this.jedisPool = jedisPool;
		this.jedisCluster = jedisCluster;
		cluster = (jedisPool == null && jedisCluster != null);
	}

	public String set(String key, Object value) {
		if (cluster) {
			if (value == null) {
				jedisCluster.del(keyToBytes(key));
				return null;
			} else {
				return jedisCluster.set(keyToBytes(key), valueToBytes(value));
			}
		} else {
			Jedis jedis = getJedis();
			try {
				if (value == null) {
					jedis.del(keyToBytes(key));
					return null;
				} else {
					return jedis.set(keyToBytes(key), valueToBytes(value));
				}
			} finally {
				close(jedis);
			}
		}
	}

	public String setex(String key, int seconds, Object value) {
		if (cluster) {
			if (value == null) {
				jedisCluster.del(keyToBytes(key));
				return null;
			}
			return jedisCluster.setex(keyToBytes(key), seconds, valueToBytes(value));
		} else {
			Jedis jedis = getJedis();
			try {
				if (value == null) {
					jedis.del(keyToBytes(key));
					return null;
				}
				return jedis.setex(keyToBytes(key), seconds, valueToBytes(value));
			} finally {
				close(jedis);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		if (cluster) {
			return (T) valueFromBytes(jedisCluster.get(keyToBytes(key)));
		} else {
			Jedis jedis = getJedis();
			try {
				return (T) valueFromBytes(jedis.get(keyToBytes(key)));
			} finally {
				close(jedis);
			}
		}
	}

	private byte[] keyToBytes(String key) {
		return SafeEncoder.encode(key);
	}

	private byte[] valueToBytes(Object object) {
		return Serialize.serialize(object);
	}

	private <T> T valueFromBytes(byte[] bytes) {
		if (bytes != null)
			return Serialize.deserialize(bytes);
		return null;
	}

	public Jedis getJedis() {
		return jedisPool.getResource();
	}

	private void close(Jedis jedis) {
		jedis.close();
	}

	public void remove(String key) {
		if (cluster) {
			jedisCluster.del(keyToBytes(key));
		} else {
			Jedis jedis = getJedis();
			try {
				jedis.del(keyToBytes(key));
			} finally {
				close(jedis);
			}
		}
	}

	public void expire(String key, int expiredTime) {
		byte[] bkey = keyToBytes(key);
		if (cluster) {
			jedisCluster.expire(bkey, expiredTime);
		} else {
			Jedis jedis = getJedis();
			try {
				jedis.expire(bkey, expiredTime);
			} finally {
				close(jedis);
			}
		}
	}

	public void close() {
		if (cluster) {
			try {
				jedisCluster.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			jedisPool.close();
		}
	}

}
