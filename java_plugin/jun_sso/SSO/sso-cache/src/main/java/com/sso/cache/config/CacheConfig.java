package com.sso.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import com.sso.cache.adapt.GuavaCacheManager;
import com.sso.data.redis.service.RedisCommonService;
import com.sso.data.redis.service.impl.RedisCacheTemplate;
import com.sso.data.redis.service.impl.RedisCommonServiceImpl;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *  缓存模块相关bean注册类
 */
//@Configuration
@EnableCaching
@ComponentScan("com.sso.cache.strategy,com.sso.cache.aspect")
// @PropertySource(value = "classpath:cache-config.properties",
// ignoreResourceNotFound = true)
public class CacheConfig {

    @Value("${redis.pool.maxTotal:10}")
    private int maxTotal; // 最大连接数
    @Value("${redis.pool.maxIdle:10}")
    private int maxIdle; // 最大空闲连接数
    @Value("${redis.pool.minIdle:0}")
    private int minIdle; // 最小空闲连接数
    @Value("${redis.pool.maxWaitMillis:-1}")
    private long maxWaitMillis; // 最大建立连接等待时间
    @Value("${redis.pool.testOnBorrow:false}")
    private boolean testOnBorrow; // 获取连接时检查有效性
    @Value("${redis.pool.testWhileIdle:false}")
    private boolean testWhileIdle; // 空闲时检查有效性

    @Value("${redis.hostName:127.0.0.1}")
    private String hostName; // 主机名
    @Value("${redis.port:6379}")
    private int port; // 监听端口
    @Value("${redis.password:}")
    private String password; // 密码
    @Value("${redis.timeout:2000}")
    private int timeout; // 客户端连接时的超时时间（单位为秒）

    @Value("${redis.cache.defaultExpiration:3600}")
    private long defaultExpiration; // 缓存时间，单位为秒（默认为0，表示永不过期）

    @Value("${guava.cache.expireAfterWrite:1h}")
    private String expireAfterWrite; // 缓存时间（m:分钟；h:小时；d:天）

    /**
     * 构造jedis连接池配置对象
     *
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestWhileIdle(testWhileIdle);
        return config;
    }

    @Bean
    public JedisPool jedisPool() {
        JedisPool pool = new JedisPool(jedisPoolConfig(), hostName, port, timeout);
        return pool;
    }

    /**
     * 构造jedis连接工厂
     *
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory(jedisPoolConfig());
        factory.setHostName(hostName);
        factory.setPort(port);
        //factory.setPassword(password);
        factory.setTimeout(timeout);
        factory.afterPropertiesSet();
        return factory;
    }

    /**
     * 注入redis template
     *
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new JdkSerializationRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 注入redis cache manager
     *
     * @return
     */
    @Bean
    @Primary
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
        cacheManager.setDefaultExpiration(defaultExpiration);
        return cacheManager;
    }

    @Bean
    public RedisCacheTemplate redisCacheTemplate() {
        return new RedisCacheTemplate();
    }

    /**
     * 注入guava cache manager
     *
     * @return
     */
    @Bean
    public GuavaCacheManager guavaCacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        StringBuilder spec = new StringBuilder();
        spec.append("expireAfterWrite=" + expireAfterWrite);
        spec.append(",");
        // String maximumSize = env.getProperty("guava.cache.maximumSize");
        // if (maximumSize != null) {
        // spec.append("maximumSize=" + maximumSize);
        // spec.append(",");
        // }
        if (spec.length() > 0) {
            spec = spec.deleteCharAt(spec.length() - 1);
        }
        cacheManager.setCacheSpecification(spec.toString());
        return cacheManager;
    }

    @Bean
    public RedisCommonService redisCommonService() {
        return new RedisCommonServiceImpl();
    }
}
