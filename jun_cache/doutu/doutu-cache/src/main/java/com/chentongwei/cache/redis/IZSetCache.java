package com.chentongwei.cache.redis;

import java.util.Set;

/**
 * Redis的zset类型接口
 * 
 * @author TongWei.Chen 2017-5-30 10:33:51
 */
public interface IZSetCache {
	
	/**
	 * 缓存带分数的zset
	 * 
	 * @param key：key
	 * @param value：值
	 * @param score：分数
	 * @return
	 */
	boolean cacheZSet(final String key, final Object value, final double score);
	
	/**
	 * 按照下标查询zset
	 * 
	 * @param key：key
	 * @param start:开始下标
	 * @param end:结束下标
	 * @return
	 */
	Set<Object> getZSet(final String key, final long start, final long end);
	
	/**
	 * 查询全部zset
	 * 
	 * @param key：key
	 * @return
	 */
	Set<Object> getZSet(final String key);
	
	/**
	 * 获取key下的value数量
	 * 
	 * @param key：key
	 * @return
	 */
	long zCard(final String key);
	
	/**
	 * 移除key下的某个value
	 * 
	 * @param key：key
	 * @param value：值
	 * @return
	 */
	long remove(final String key, final Object value);
}
