package org.smartboot.integration.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smartboot.integration.cache.CacheClient;
import org.smartboot.shared.SmartException;
import org.smartboot.sosa.rpc.serial.SerializableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * @author Wujun
 * @version v0.1 2015年11月06日 下午1:23 Seer Exp.
 */
@Component("cacheClient")
public class RedisCacheClientImpl implements CacheClient {
	private static final Logger LOGGER = LogManager.getLogger(RedisCacheClientImpl.class);
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Value("${redis.cacheName:smart-boot}")
	private String name;

	@Override
	public void putObject(final String key, final Object object, final long exprie) {
		try {
			redisTemplate.execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] keyb = getKeyBytes(key);
					connection.set(keyb, toByteArray(object));
					if (exprie > 0) {
						connection.expire(keyb, exprie);
					}
					return 1L;
				}
			});
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("cache data key:" + name + "." + key + ", data:" + object + ",expire :" + exprie);
			}
		} catch (Exception e) {
			LOGGER.catching(e);
		}
	}

	@Override
	public boolean setNX(final String key, final String object, final long exprie) {
		boolean flag = false;
		try {
			flag = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] keyb = getKeyBytes(key);
					boolean flag = connection.setNX(keyb, object.getBytes());
					if (flag && exprie > 0) {
						connection.expire(keyb, exprie);
					}
					return flag;
				}
			});
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(flag ? "key has ready exists,key:" + key
						: "cache data key:" + key + ", data:" + object + ",expire :" + exprie);
			}
		} catch (Exception e) {
			LOGGER.catching(e);
		}
		return flag;
	}

	@Override
	public void set(String key, String str, long exprie) {
		try {
			redisTemplate.execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] keyb = getKeyBytes(key);
					connection.set(keyb, str.getBytes());
					if (exprie > 0) {
						connection.expire(keyb, exprie);
					}
					return 1L;
				}
			});
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("cache data key:" + key + ", data:" + str + ",expire :" + exprie);
			}
		} catch (Exception e) {
			LOGGER.catching(e);
		}
	}

	@Override
	public <T> T getObject(String key) {
		T object = null;
		try {
			object = redisTemplate.execute(new RedisCallback<T>() {
				public T doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] value = connection.get(getKeyBytes(key));
					if (value == null) {
						return null;
					}
					T t = toObject(value);
					// 反序列失败则失效缓存
					if (t == null) {
						remove(key);
					}
					return t;

				}
			});
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("get cache data, key:" + name + "." + key + ", data:" + object);
			}
		} catch (Exception e) {
			LOGGER.catching(e);
		}
		return object;
	}

	@Override
	public String getString(String key) {
		String object = null;
		try {
			object = redisTemplate.execute(new RedisCallback<String>() {
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] value = connection.get(getKeyBytes(key));
					if (value == null) {
						return null;
					}
					return new String(value);

				}

			});
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("get cache data, key:" + (name + "." + key) + ", data:" + object);
			}
		} catch (Exception e) {
			LOGGER.catching(e);
		}

		return object;
	}

	/**
	 * 描述 : <Object转byte[]>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 *
	 * @param obj
	 * @return
	 */
	private byte[] toByteArray(Object obj) {
		Schema<SerializableBean> schema = RuntimeSchema.getSchema(SerializableBean.class);
		// 缓存buff
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		// 序列化成protobuf的二进制数据
		SerializableBean bean = new SerializableBean();
		bean.setBean(obj);
		return ProtobufIOUtil.toByteArray(bean, schema, buffer);
		// RedisSerializer<Object> reidsSerializer = (RedisSerializer<Object>)
		// redisTemplate.getValueSerializer();
		// return reidsSerializer.serialize(obj);
	}

	private byte[] getKeyBytes(String key) {
		return redisTemplate.getStringSerializer().serialize(name + "." + key);
	}

	/**
	 * 描述 : <byte[]转Object>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 *
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T toObject(byte[] bytes) {
		try {
			SerializableBean bean = new SerializableBean();
			Schema<SerializableBean> schema = RuntimeSchema.getSchema(SerializableBean.class);
			ProtobufIOUtil.mergeFrom(bytes, bean, schema);
			return (T) bean.getBean();
			// return redisTemplate.getValueSerializer().deserialize(bytes);
		} catch (Exception e) {
			LOGGER.catching(e);
		}
		return null;
	}

	@Override
	public Long remove(final String key) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("remove cache data, key:" + key);
		}
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.del(getKeyBytes(key));
				}
			});
		} catch (Exception e) {
			LOGGER.catching(e);
		}
		return -1l;
	}

	@Override
	public Long incr(String key) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Increment value of key, key:" + key);
		}
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.incr(getKeyBytes(key));
				}
			});
		} catch (Exception e) {
			LOGGER.catching(e);
		}
		return null;
	}

	@Override
	public Long decr(String key) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("decr value of key, key:" + key);
		}
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.decr(getKeyBytes(key));
				}
			});
		} catch (Exception e) {
			LOGGER.catching(e);
		}
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<String> keys(String pattern) {
		List<String> object = null;
		try {
			object = redisTemplate.execute(new RedisCallback<List<String>>() {
				public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
					Set<byte[]> value = connection.keys(getKeyBytes(pattern));
					if (CollectionUtils.isEmpty(value)) {
						return null;
					}
					List<String> keyList = new ArrayList<>(value.size());
					for (byte[] b : value) {
						keyList.add(StringUtils.substringAfter(new String(b), name + "."));
					}
					return keyList;

				}

			});
		} catch (Exception e) {
			LOGGER.catching(e);
		}

		return object;
	}

	@Override
	public boolean exists(String key) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("decr value of key, key:" + key);
		}
		try {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.exists(getKeyBytes(key));
				}
			});
		} catch (Exception e) {
			LOGGER.catching(e);
			throw new SmartException(e);
		}
	}

}
