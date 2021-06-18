package net.oschina.j2cache.store.redis;

import net.oschina.j2cache.Cache;
import net.oschina.j2cache.CacheException;
import net.oschina.j2cache.CacheExpiredListener;
import net.oschina.j2cache.CacheProvider;
import net.oschina.j2cache.utils.PropertiesLoader;
import net.oschina.j2cache.utils.StrExtUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Redis 缓存实现 -- 容器的实例构建
 */
public class RedisCacheProvider implements CacheProvider {

    /** 用于存储每个region对应的cache instance */
    private final ConcurrentHashMap<String, RedisCache> cacheInstances = new ConcurrentHashMap<String, RedisCache>();
	
	private static JedisPool pool;

	@Override
	public String name() {
		return "redis";
	}

    @Override
    public void start(Properties props) throws CacheException {

    }

    /**
	 * 从池中取出一个连接实例
	 * @return {Jedis}
	 */
	public static Jedis getResource() {
		return pool.getResource();
	}

	/**
	 * 释放资源
	 * @param jedis  jedis instance
	 */
	public static void returnResource(Jedis jedis) {
		if(null == jedis)
			return;
		pool.returnResourceObject(jedis);
	}

	@Override
	public Cache buildCache(String regionName, boolean autoCreate, CacheExpiredListener listener) throws CacheException {
        if (cacheInstances.get(regionName) == null) {
            cacheInstances.put(regionName, new RedisCache(regionName));
        }
		return cacheInstances.get(regionName);
	}

    @Override
    public void start(String confFilePath) throws CacheException {
        if (pool != null) return ;
        if (confFilePath == null || "".equals(confFilePath)) {
            throw new CacheException(String.format("缓存的Redis配置文件路径错误 : %s", confFilePath));
        }

        // 载入REDIS配置
        Properties props = new PropertiesLoader(confFilePath).getProperties();

        try {
            if (props.isEmpty()) {
                throw new Exception(String.format("读取缓存Redis配置文件错误 : %s", confFilePath));
            }

            // Redis链接配置
            JedisPoolConfig config = new JedisPoolConfig();
            String host = getProperty(props, "cache.redis.hostname", "127.0.0.1");

            String password = props.getProperty("cache.redis.password", null);
            if (StrExtUtils.isNullOrEmpty(password)) password = null;

            int port = getProperty(props, "cache.redis.port", 6379);
            int timeout = getProperty(props, "cache.redis.timeout", 2000);
            int database = getProperty(props, "cache.redis.database", 0);

            pool = new JedisPool(config, host, port, timeout, password, database);

            // 序列化工具类名
            // serializer = props.getProperty("cache.serializer_class", SnappySerializer.class.getName());

        } catch (Exception e) {
            throw new CacheException("创建CacheProvider实例失败.", e);
        }

    }

    @Override
	public void stop() {
		pool.destroy();
	}
	
	private static String getProperty(Properties props, String key, String defaultValue) {
		return props.getProperty(key, defaultValue).trim();
	}

	/**
	 * 取配置数据 默认为int类型的字段
	 * @param props 配置属性
	 * @param key 名称
	 * @param defaultValue 默认值
	 * @return 对应的值
	 */
	private static int getProperty(Properties props, String key, int defaultValue) {
		try{
			return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)).trim());
		}catch(Exception e){
			return defaultValue;
		}
	}

	/**
	 * 去配置数据 默认为boolean类型的字段
	 * @param props 配置属性
	 * @param key 名称
	 * @param defaultValue 默认值
	 * @return 对应的值
	 */
	private static boolean getProperty(Properties props, String key, boolean defaultValue) {
		return "true".equalsIgnoreCase(props.getProperty(key, String.valueOf(defaultValue)).trim());
	}

}
