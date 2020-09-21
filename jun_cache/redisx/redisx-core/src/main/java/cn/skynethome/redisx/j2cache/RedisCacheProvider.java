package cn.skynethome.redisx.j2cache;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
  * 项目名称:[redisx-core]
  * 包:[cn.skynethome.redisx.j2cache]    
  * 文件名称:[RedisCacheProvider]  
  * 描述:[Redis 缓存实现]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月11日 上午9:42:59]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月11日 上午9:42:59]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class RedisCacheProvider implements CacheProvider {
	
	private static JedisPool pool;
	
	public RedisCacheProvider()
	{
	    
	}
	
	protected ConcurrentHashMap<String, RedisCache> caches = new ConcurrentHashMap<>();
	
	public String name() {
		return "redis";
	}
    
	// 这个实现有个问题,如果不使用RedisCacheProvider,但又使用RedisCacheChannel,这就NPE了
    public static Jedis getResource() {
    	return pool.getResource();
    }

	@Override
	public Cache buildCache(String regionName, boolean autoCreate, CacheExpiredListener listener) throws CacheException {
		// 虽然这个实现在并发时有概率出现同一各regionName返回不同的实例
		// 但返回的实例一次性使用,所以加锁了并没有增加收益
		RedisCache cache = caches.get(regionName);
		if (cache == null) {
			cache = new RedisCache(regionName, pool);
			caches.put(regionName, cache);
		}
		return cache;
    }

	@Override
	public void start(Properties props) throws CacheException {

		
	}

	@Override
	public void stop() {
		pool.destroy();
		caches.clear();
	}

}
