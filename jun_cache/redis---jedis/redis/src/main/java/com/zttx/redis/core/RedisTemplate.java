/**<p>项目名：</p>
 * <p>包名：	com.zttx.redis.core</p>
 * <p>文件名：RedisTemplate.java</p>
 * <p>版本信息：</p>
 * <p>日期：2015年1月14日-上午10:38:50</p>
 * Copyright (c) 2015singno公司-版权所有
 */
package com.zttx.redis.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;

import com.zttx.redis.callback.RedisCallback;
import com.zttx.redis.callback.RedisNoResultCall;
import com.zttx.redis.utils.RedisPoolManager;
import com.zttx.redis.utils.SerializerUtils;

/**<p>名称：RedisTemplate.java</p>
 * <p>描述：</p>
 * <pre>
 *         
 * </pre>
 * @author 鲍建明
 * @date 2015年1月14日 上午10:38:50
 * @version 1.0.0
 */
public class RedisTemplate {

//	private static Logger logger = LoggerFactory.getLogger(RedisTemplate.class);
	
	
	abstract class BaseTemplate{
		
	}
	
	private RedisPoolManager redisPoolManager;
	
	
	public RedisTemplate(ShardedJedisPool shardedJedisPool){
		this.redisPoolManager = new RedisPoolManager(shardedJedisPool);
	}

	

	/**
	 * 描述：
	 * <pre>
	 * 	放入redis缓存
	 * </pre>
	 * @param k
	 * @param v
	 */
	public void set(String k, Object v){
		final byte[] key = SerializerUtils.rawKey(k);
		final byte[] value = SerializerUtils.rawValue(v);
		execute( new RedisNoResultCall(){
			@Override
			public void action(ShardedJedis shardedJedis) {
				shardedJedis.set(key, value);
			}} );
	}
	


	/**
	 * 描述：
	 * <pre>
	 * 	放入缓存
	 * </pre>
	 * @param k
	 * @param v
	 * @param seconds 秒
	 */
	public void set(String k, Object v, final int seconds){
		final byte[] key = SerializerUtils.rawKey(k);
		final byte[] value = SerializerUtils.rawValue(v);
		execute(new RedisNoResultCall(){
			@Override
			public void action(ShardedJedis shardedJedis) {
				shardedJedis.setex(key, seconds, value);
			}
		});
	}
	
	/**
	 * 描述：
	 * <pre>
	 * 	获取某个缓存的存活时间
	 * </pre>
	 * @param k
	 * @return
	 */
	public long getLifeTime(String k){
		final byte[] key = SerializerUtils.rawKey(k);
		return (Long) execute(new RedisCallback<Long>() {
			public Long doInRedis(ShardedJedis shardedJedis) throws Exception {
				return shardedJedis.ttl(key);
			}
		});
		
	}
	
	/**
	 * 描述：
	 * <pre>
	 * 		获取值
	 * </pre>
	 * @param k
	 * @return
	 */
	public Object get(String k){
		final byte[] key = SerializerUtils.rawKey(k);
		byte[] value = (byte[]) execute(new RedisCallback<byte[]>(){
			public byte[] doInRedis(ShardedJedis shardedJedis) throws Exception {
				JedisShardInfo info = shardedJedis.getShardInfo(key);
				System.out.println("info:host->>" + info.getHost() + ",name-->" + info.getName() + ", port" + info.getPort());
				return shardedJedis.get(key);
			}
		});
		return SerializerUtils.getRedisserializer().deserialize(value);
	}
	
	/**
	 * 描述：
	 * <pre>
	 * 	像现有的元素追加元素
	 * </pre>
	 * @param k
	 * @param v
	 */
	public void append(String k, Object v){
		final byte[] key = SerializerUtils.rawKey(k);
		final byte[] value = SerializerUtils.rawValue(v);
		execute(new RedisNoResultCall(){
			@Override
			public void action(ShardedJedis shardedJedis) {
				shardedJedis.append(key, value);
			}});
	}
	
	/**
	 * 描述：
	 * <pre>
	 * 	List型数据存储，数据向前累加
	 * 默认leftPush
	 * </pre>
	 * @param k
	 * @param v
	 * @return
	 */
	public Long lPush(String k, Object ... v){
		final byte[] key = SerializerUtils.rawKey(k);
		final byte[][] value = SerializerUtils.rawValues(v);
		return (Long) execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(ShardedJedis shardedJedis) throws Exception {
				return shardedJedis.lpush(key, value);
			}
		});
	}
	
	/**
	 * 描述：
	 * <pre>
	 * 		List数据存储，
	 * 		rightPush
	 * </pre>
	 * @param k
	 * @param v
	 * @return
	 */
	public Long rPush(String k, Object ... v){
		final byte[] key = SerializerUtils.rawKey(k);
		final byte[][] value = SerializerUtils.rawValues(v);
		return execute(new RedisCallback<Long>(){
			@Override
			public Long doInRedis(ShardedJedis shardedJedis) throws Exception {
				return shardedJedis.rpush(key, value);
			}});
	}
	
	/**
	 * 描述：
	 * <pre>
	 * 	排序
	 * </pre>
	 * @param k
	 * @param sortingParameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> sort(String k, final SortingParams sortingParameters){
		final byte[] key = SerializerUtils.rawKey(k);
		List<byte[]> b =  execute(new RedisCallback<List<byte[]>>() {
			@Override
			public List<byte[]> doInRedis(ShardedJedis shardedJedis) throws Exception {
				if(sortingParameters == null){
					return shardedJedis.sort(key);
				}
				return shardedJedis.sort(key, sortingParameters);
			}
		});
		return SerializerUtils.deserializeValues(b, List.class, SerializerUtils.getRedisserializer());
	}
	
	
	/**
	 * 描述：
	 * <pre>
	 * Set集合新增
	 * </pre>
	 * @param k
	 * @param v
	 * @return
	 */
	public Long sadd(String k, Object ... v){
		final byte[] key = SerializerUtils.rawKey(k);
		final byte[][] value = SerializerUtils.rawValues(v);
		return execute(new RedisCallback<Long>(){
			@Override
			public Long doInRedis(ShardedJedis shardedJedis) throws Exception {
				return shardedJedis.sadd(key, value);
			}});
	}
	
	/**
	 * 描述：
	 * <pre>
	 * 
	 * Set集合中，删除某些元素
	 * </pre>
	 * @param k
	 * @param v
	 * @return
	 */
	public  Long sdelete(String k, Object ... v){
		final byte[] key = SerializerUtils.rawKey(k);
		final byte[][] value = SerializerUtils.rawValues(v);
		return execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(ShardedJedis shardedJedis) throws Exception {
				return shardedJedis.srem(key, value);
			}
		});
	}
	
	/**
	 * 描述：
	 * <pre>
	 * 	通过key来获取对应的set集合
	 * </pre>
	 * @param k
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Set<T> sgetAll(String k){
		final byte[] key = SerializerUtils.rawKey(k);
		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {
			@Override
			public Set<byte[]> doInRedis(ShardedJedis shardedJedis)
					throws Exception {
				return shardedJedis.smembers(key);
			}
		});
		return SerializerUtils.deserializeValues(rawValues, Set.class, SerializerUtils.getRedisserializer());
	}
	
	
	/**
	 * 描述：
	 * <pre>
	 * 		Set集合 
	 * 		判断指定元素在该集合中是否存在
	 * </pre>
	 * @param k
	 * @param v
	 * @return
	 */
	public boolean sisExist(String k, Object v){
		final byte[] key = SerializerUtils.rawKey(k);
		final byte[] value = SerializerUtils.rawValue(v);
		return execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(ShardedJedis shardedJedis)
					throws Exception {
				return shardedJedis.sismember(key, value);
			}
		});
	}
	
	
	/**
	 * 描述：
	 * <pre>
	 * 		HashMap集合存储
	 * </pre>
	 * @param k
	 * @param m
	 */
	public void hset(String k, Map<?, ?> m ){
		if (m == null || m.isEmpty()) {
			return;
		}
		final byte[] key = SerializerUtils.rawKey(k);
		final Map<byte[], byte[]> hashes = new LinkedHashMap<byte[], byte[]>(m.size());
		for (Map.Entry<?, ? > entry : m.entrySet()) {
			hashes.put(SerializerUtils.rawHashKey(entry.getKey()), SerializerUtils.rawHashValue(entry.getValue()));
		}
		execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(ShardedJedis shardedJedis)
					throws Exception {
				return shardedJedis.hmset(key, hashes);
			}
		});
	}
	
	/**
	 * 描述：
	 * <pre>
	 * 		HashMap删除对应的 KEY数据
	 * </pre>
	 * @param k
	 * @param hashK
	 */
	public void hdelete(String k, Object ... hashK){
		final byte[] key = SerializerUtils.rawKey(k);
		final byte[][] hashKey = SerializerUtils.rawHashKeys(hashK);
		execute(new RedisCallback<Object>() {
			public Object doInRedis(ShardedJedis shardedJedis) {
				shardedJedis.hdel(key, hashKey);
				return null;
			}
		});
	}
	
	
	/**
	 * 描述：
	 * <pre>
	 * 	在Map中查询该值是否存在
	 * </pre>
	 * @param k
	 * @param hashK
	 * @return
	 */
	public boolean hisExists(String k, Object hashK){
		final byte[] rawKey = SerializerUtils.rawKey(k);
		final byte[] rawHashKey = SerializerUtils.rawHashKey(hashK);
		return execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(ShardedJedis shardedJedis) {
				return shardedJedis.hexists(rawKey, rawHashKey);
			}
		});
	}
	
	
	
	/**
	 * 描述：
	 * <pre>
	 * 		获取指定的list集合
	 * </pre>
	 * @param k
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> lrange(String k, final long start, final long end){
		final byte[] key = SerializerUtils.rawKey(k);
		return (List<Object>) execute(new RedisCallback<List<Object>>() {
			@Override
			public List<Object> doInRedis(ShardedJedis shardedJedis) throws Exception {
				List<byte[]> rawValues = shardedJedis.lrange(key, start, end);
				return SerializerUtils.deserializeValues(rawValues, List.class, SerializerUtils.getRedisserializer());
			}
		});
	}
	
	
	
	
	

	/**
	 * 描述：
	 * <pre>
	 * 清空数据
	 * </pre>
	 */
	public void clearAll(){
		execute(new RedisNoResultCall(){
			@Override
			public void action(ShardedJedis shardedJedis) {
				for (Jedis jedis : shardedJedis.getAllShards()) {
					jedis.flushDB();
				}
			}});
	}
	
	/**
	 * 描述：
	 * <pre>
	 * 	删除缓存
	 * </pre>
	 * @param k
	 */
	public void delete(String k){
		final byte[] key = SerializerUtils.rawKey(k);
		execute(new RedisNoResultCall(){
			@Override
			public void action(ShardedJedis shardedJedis) {
				shardedJedis.del(key);
			}
		});
	}
	
	
	//执行方法
	@SuppressWarnings("unchecked")
	public <T> T execute(RedisCallback<?> callback){
		boolean recovery = false;
		ShardedJedis dj = null;
		try {
			dj = redisPoolManager.getReids();
			return (T) callback.doInRedis(dj);
		} catch (Exception e) {
			recovery= true;
			e.printStackTrace();
		}finally {
			redisPoolManager.recovery(dj, recovery);
			//dj.close();
		}
		return null;
	}

	
}
