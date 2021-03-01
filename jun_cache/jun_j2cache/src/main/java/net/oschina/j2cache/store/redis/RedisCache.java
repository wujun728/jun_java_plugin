package net.oschina.j2cache.store.redis;

import net.oschina.j2cache.Cache;
import net.oschina.j2cache.utils.CacheCodeUtils;
import net.oschina.j2cache.CacheException;
import net.oschina.j2cache.serializer.SerializerTools;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存的Redis实现
 */
public class RedisCache implements Cache {
	private String region;
	
	public RedisCache(String region) {
		this.region = region;
	}

	@Override
	public Object get(Object key) throws CacheException {
		Object result = null;
		// 取得连接实例
		Jedis jedis = RedisCacheProvider.getResource();
		if (jedis == null || StringUtils.isEmpty(key)) {
			return null;
		}
		try {
			// 1.如果确定了返回是字符串类型，就使用这个，但是这样会比较喽
//			result = jedis.get(createKey(key));
			// 2.如果需要自定义序列化实现使用下面的方法
			byte[] b = jedis.get(SerializerTools.serializeKey(CacheCodeUtils.createRedisKey(region, key)));
			if (b != null) {
				// your serialization utils
				result = SerializerTools.deserializeValue(b);
			}
		} catch (Exception e) {
			// 如果抛出了异常，打印日志并销毁该缓存
			if(e instanceof NullPointerException)
				evict(key);
		} finally {
			// 造作完成后将连接实例放回到线程池
			RedisCacheProvider.returnResource(jedis);
		}
		return result;
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		if (value == null)
			evict(key);
		else {
			Jedis jedis = RedisCacheProvider.getResource();
			// 将数据保存到缓存容器，1.key序列化，2.value序列化
			try {
				jedis.set(SerializerTools.serializeKey(CacheCodeUtils.createRedisKey(region, key)), SerializerTools.serializeValue(value));
			} catch (Exception e) {
				throw new CacheException(e);
			} finally {
				RedisCacheProvider.returnResource(jedis);
			}
		}
	}

	@Override
	public void evict(Object key) throws CacheException {
		Jedis jedis = RedisCacheProvider.getResource();
		try {
			jedis.del(SerializerTools.serializeKey(CacheCodeUtils.createRedisKey(region, key)));
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			RedisCacheProvider.returnResource(jedis);
		}
	}

	@Override
	public void evict(List keys) throws CacheException {
		if(keys == null || keys.size() == 0)
			return ;
		Jedis jedis = RedisCacheProvider.getResource();
		String[] _keys = new String[keys.size()];
		for (int i=0; i<keys.size(); i++) {
			_keys[i] = CacheCodeUtils.createRedisKey(region, keys.get(i));
		}
		try {
			jedis.del(_keys);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			RedisCacheProvider.returnResource(jedis);
		}
	}

	@Override
	public List keys() throws CacheException {
		Jedis jedis = RedisCacheProvider.getResource();
		List<String> listKey = new ArrayList<String>();
		try {
			listKey.addAll(jedis.keys(region+":*"));
			int nKeyPreLen = region.length() + 3;
			for (int i=0; i<listKey.size(); i++) {
				listKey.set(i, listKey.get(i).substring(nKeyPreLen));
			}
			return listKey;
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			RedisCacheProvider.returnResource(jedis);
		}
	}

	@Override
	public void clear() throws CacheException {
		Jedis jedis = RedisCacheProvider.getResource();
		try {
			jedis.del(region+":*");
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			RedisCacheProvider.returnResource(jedis);
		}
	}

	@Override
	public void destroy() throws CacheException {
		this.clear();
	}
}
