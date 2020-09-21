/**<p>项目名：</p>
 * <p>包名：	com.zttx.redis.utils</p>
 * <p>文件名：SerializerUtils.java</p>
 * <p>版本信息：</p>
 * <p>日期：2015年1月14日-下午5:29:47</p>
 * Copyright (c) 2015singno公司-版权所有
 */
package com.zttx.redis.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.Assert;

import com.zttx.redis.serializer.JdkSerializer;
import com.zttx.redis.serializer.RedisSerializer;

/**<p>名称：SerializerUtils.java</p>
 * <p>描述：</p>
 * <pre>
 *         
 * </pre>
 * @author 鲍建明
 * @date 2015年1月14日 下午5:29:47
 * @version 1.0.0
 */
public class SerializerUtils {

	@SuppressWarnings("rawtypes")
	private  final static RedisSerializer redisSerializer = new JdkSerializer();
	
	
	
	@SuppressWarnings("rawtypes")
	public static RedisSerializer getRedisserializer() {
		return redisSerializer;
	}

	//key 值 序列化
	@SuppressWarnings({ "unchecked" })
	public static byte[] rawKey(Object key)  {
		Assert.notNull(key, "non null key required");
		if (redisSerializer == null && key instanceof byte[]) {
			return (byte[]) key;
		}
		return redisSerializer.serialize(key);
	}
	
	//value值序列化
	@SuppressWarnings("unchecked")
	public static byte[] rawValue(Object value) {
		if (redisSerializer == null && value instanceof byte[]) {
			return (byte[]) value;
		}
		return redisSerializer.serialize(value);
	}
	
	//序列化集合
	public static byte[][] rawValues(Object... values) {
		final byte[][] rawValues = new byte[values.length][];
		int i = 0;
		for (Object value : values) {
			rawValues[i++] = rawValue(value);
		}
		return rawValues;
	}
	
	
	//序列化集合
	@SuppressWarnings({  "unchecked" })
	public static <T extends Collection<?>> T deserializeValues(Collection<byte[]> rawValues, Class<T> type,
			RedisSerializer<?> redisSerializer) {
		
		if (redisSerializer == null) {
			return (T) rawValues;
		}
		// connection in pipeline/multi mode
		if (rawValues == null) {
			return null;
		}

		Collection<Object> values = (List.class.isAssignableFrom(type) ? new ArrayList<Object>(rawValues.size())
				: new LinkedHashSet<Object>(rawValues.size()));
		for (byte[] bs : rawValues) {
			values.add(redisSerializer.deserialize(bs));
		}

		return (T) values;
	}
	
	@SuppressWarnings("unchecked")
	public static <HK> byte[] rawHashKey(HK hashKey) {
		Assert.notNull(hashKey, "non null hash key required");
		if (redisSerializer == null && hashKey instanceof byte[]) {
			return (byte[]) hashKey;
		}
		return redisSerializer.serialize(hashKey);
	}
	
	@SuppressWarnings("unchecked")
	public static <HV> byte[] rawHashValue(HV value) {
		if (redisSerializer == null & value instanceof byte[]) {
			return (byte[]) value;
		}
		return redisSerializer.serialize(value);
	}
	
	
	@SuppressWarnings("unchecked")
	public static <HK> byte[][] rawHashKeys(HK... hashKeys) {
		Assert.notNull(hashKeys, "non null key required");
		final byte[][] rawHashKeys = new byte[hashKeys.length][];
		int i = 0;
		for (HK hashKey : hashKeys) {
			rawHashKeys[i++] = rawHashKey(hashKey);
		}
		return rawHashKeys;
	}
	
}
