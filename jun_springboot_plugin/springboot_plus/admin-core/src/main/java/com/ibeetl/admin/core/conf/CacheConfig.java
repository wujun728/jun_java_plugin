package com.ibeetl.admin.core.conf;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

/**
 * 支持一二级缓存，使得性能逆天快.默认不开启
 * 
 * @author Wujun
 *
 */

//@Configuration
//@ConditionalOnProperty(name="springext.cache.enabled", havingValue="true" ,matchIfMissing=false)
public class CacheConfig {

	// 定义一个redis 的频道，默认叫cache，用于pub/sub
	@Value("${springext.cache.redis.topic:cache}")
	String topicName;

	@Bean
	public TowLevelCacheManager cacheManager(RedisTemplate redisTemplate) {

		RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(redisTemplate.getConnectionFactory());
		SerializationPair pair = SerializationPair
				.fromSerializer(new JdkSerializationRedisSerializer(this.getClass().getClassLoader()));
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);

		TowLevelCacheManager cacheManager = new TowLevelCacheManager(redisTemplate, writer, config);

		return cacheManager;
	}

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic(topicName));
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(final TowLevelCacheManager cacheManager) {
		return new MessageListenerAdapter(new MessageListener() {

			public void onMessage(Message message, byte[] pattern) {
				byte[] bs = message.getChannel();
				try {
					// Sub 一个消息，通知缓存管理器
					String type = new String(bs, "UTF-8");
					cacheManager.receiver(type);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					// 不可能出错，忽略
				}

			}

		});
	}

	class TowLevelCacheManager extends RedisCacheManager {
		RedisTemplate redisTemplate;

		public TowLevelCacheManager(RedisTemplate redisTemplate, RedisCacheWriter cacheWriter,
				RedisCacheConfiguration defaultCacheConfiguration) {
			super(cacheWriter, defaultCacheConfiguration);
			this.redisTemplate = redisTemplate;
		}

		// 使用RedisAndLocalCache代替Spring Boot自带的RedisCache
		@Override
		protected Cache decorateCache(Cache cache) {
			return new RedisAndLocalCache(this, (RedisCache) cache);
		}

		// 通过其他分布式节点，缓存改变
		public void publishMessage(String cacheName) {
			this.redisTemplate.convertAndSend(topicName, cacheName);
		}

		// 接受一个消息清空本地缓存
		public void receiver(String name) {
			RedisAndLocalCache cache = ((RedisAndLocalCache) this.getCache(name));
			if (cache != null) {
				cache.clearLocal();
			}
		}

	}

	class RedisAndLocalCache implements Cache {
		// 本地缓存提供
		ConcurrentHashMap<Object, Object> local = new ConcurrentHashMap<Object, Object>();
		RedisCache redisCache;
		TowLevelCacheManager cacheManager;

		public RedisAndLocalCache(TowLevelCacheManager cacheManager, RedisCache redisCache) {
			this.redisCache = redisCache;
			this.cacheManager = cacheManager;
		}

		@Override
		public String getName() {
			return redisCache.getName();
		}

		@Override
		public Object getNativeCache() {
			return redisCache.getNativeCache();
		}

		@Override
		public ValueWrapper get(Object key) {
			ValueWrapper wrapper = (ValueWrapper) local.get(key);
			if (wrapper != null) {
				return wrapper;
			} else {
				// 二级缓存取
				wrapper = redisCache.get(key);
				if (wrapper != null) {
					local.put(key, wrapper);
				}

				return wrapper;
			}

		}

		@Override
		public <T> T get(Object key, Class<T> type) {

			return redisCache.get(key, type);
		}

		@Override
		public <T> T get(Object key, Callable<T> valueLoader) {
			return redisCache.get(key, valueLoader);
		}

		@Override
		public void put(Object key, Object value) {
			System.out.println(value.getClass().getClassLoader());
			redisCache.put(key, value);
			clearOtherJVM();

		}

		@Override
		public ValueWrapper putIfAbsent(Object key, Object value) {
			ValueWrapper v = redisCache.putIfAbsent(key, value);
			clearOtherJVM();
			return v;

		}

		@Override
		public void evict(Object key) {
			redisCache.evict(key);
			clearOtherJVM();

		}

		@Override
		public void clear() {
			redisCache.clear();

		}

		public void clearLocal() {
			this.local.clear();
		}

		protected void clearOtherJVM() {
			cacheManager.publishMessage(redisCache.getName());
		}

	}

}
