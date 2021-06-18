package redis.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration  
@EnableCaching
public class RedisCacheConfig  extends CachingConfigurerSupport{
	 @Bean  
	    public JedisConnectionFactory redisConnectionFactory() {  
	        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();  
	        redisConnectionFactory.setHostName("192.168.252.128");  
	        redisConnectionFactory.setPort(6379);  
	        return redisConnectionFactory;  
	    }  
	  
	    @Bean  
	    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {  
	        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();  
	        redisTemplate.setConnectionFactory(cf);  
	        return redisTemplate;  
	    }  
	  
	    @Bean  
	    public CacheManager cacheManager(RedisTemplate<String, String> redisTemplate) {  
	        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);  
	        // Number of seconds before expiration. Defaults to unlimited (0)  
	        cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)  
	        return cacheManager;  
	    }  
}
