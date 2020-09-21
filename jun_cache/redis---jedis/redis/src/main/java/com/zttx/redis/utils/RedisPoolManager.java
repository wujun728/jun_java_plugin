/**<p>项目名：</p>
 * <p>包名：	com.zttx.redis.utils</p>
 * <p>文件名：RedisPoolManager.java</p>
 * <p>版本信息：</p>
 * <p>日期：2015年1月14日-上午10:39:22</p>
 * Copyright (c) 2015singno公司-版权所有
 */
package com.zttx.redis.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**<p>名称：RedisPoolManager.java</p>
 * <p>描述：</p>
 * <pre>
 *         
 * </pre>
 * @author 鲍建明
 * @date 2015年1月14日 上午10:39:22
 * @version 1.0.0
 */
public class RedisPoolManager {

	
	private ShardedJedisPool shardedJedisPool;
	
	public RedisPoolManager(ShardedJedisPool shardedJedisPool){
		this.shardedJedisPool = shardedJedisPool;
	}
	

	public ShardedJedis getReids(){
		return shardedJedisPool.getResource();
	}
	
	public ShardedJedisPool getPool(){
		return this.shardedJedisPool;
	}
	
	
	public void close(ShardedJedis shardedJedis){
		if(shardedJedis != null){
			shardedJedis.disconnect();
		}
	}
	
	public Jedis getJedis(byte[] key){
		return getReids().getShard(key);
	}
	
	
	/**
	 * 描述：
	 * <pre>
	 * 收回资源
	 * </pre>
	 * @param shardedJedis
	 * @param recovery
	 */
	public void recovery(ShardedJedis shardedJedis, boolean recovery){
		if(recovery){
			shardedJedisPool.returnBrokenResource(shardedJedis);
		}else{
			shardedJedisPool.returnResource(shardedJedis);
		}
	}
	
	
	/*public boolean ping(ShardedJedis shardedJedis){
		shardedJedis.pipelined().;创建一个分布式ID
	}*/
	

	
}
