package net.oschina.j2cache.autoconfigure;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import net.oschina.j2cache.cache.support.util.J2CacheSerializer;
import net.oschina.j2cache.redis.RedisUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 对spring redis支持的配置入口
 * 
 * @author Wujun
 *
 */
@Configuration
@AutoConfigureAfter({ RedisAutoConfiguration.class })
@AutoConfigureBefore({ J2CacheAutoConfiguration.class })
public class J2CacheSpringRedisAutoConfiguration {

	private final static int MAX_ATTEMPTS = 3;

	private final static int CONNECT_TIMEOUT = 5000;

	private static final Logger log = LoggerFactory.getLogger(J2CacheSpringRedisAutoConfiguration.class);

	@Bean("j2CahceRedisConnectionFactory")
	@ConditionalOnMissingBean(name = "j2CahceRedisConnectionFactory")
	public JedisConnectionFactory j2CahceRedisConnectionFactory(net.oschina.j2cache.J2CacheConfig j2CacheConfig) {
		Properties l2CacheProperties = j2CacheConfig.getL2CacheProperties();
		String hosts = l2CacheProperties.getProperty("hosts");
		String mode = l2CacheProperties.getProperty("mode");
		String clusterName = l2CacheProperties.getProperty("cluster_name");
		String password = l2CacheProperties.getProperty("password");
		int database = l2CacheProperties.getProperty("database") == null ? 0 : Integer.parseInt(l2CacheProperties.getProperty("database"));
		JedisConnectionFactory connectionFactory = null;
		JedisPoolConfig config = RedisUtils.newPoolConfig(l2CacheProperties, null);
		List<RedisNode> nodes = new ArrayList<>();
		if (hosts != null && !"".equals(hosts)) {
			for (String node : hosts.split(",")) {
				String[] s = node.split(":");
				String host = s[0];
				int port = (s.length > 1) ? Integer.parseInt(s[1]) : 6379;
				RedisNode n = new RedisNode(host, port);
				nodes.add(n);
			}
		} else {
			log.error("j2cache中的redis配置缺少hosts！！");
			throw new IllegalArgumentException();
		}
		switch (mode) {
		case "sentinel":
			RedisSentinelConfiguration sentinel = new RedisSentinelConfiguration();
			sentinel.setMaster(clusterName);
			sentinel.setSentinels(nodes);
			connectionFactory = new JedisConnectionFactory(sentinel, config);
			connectionFactory.setPassword(password);
			connectionFactory.setDatabase(database);
			connectionFactory.setTimeout(CONNECT_TIMEOUT);
			break;
		case "cluster":
			RedisClusterConfiguration cluster = new RedisClusterConfiguration();
			cluster.setClusterNodes(nodes);
			cluster.setMaxRedirects(MAX_ATTEMPTS);
			connectionFactory = new JedisConnectionFactory(cluster, config);
			connectionFactory.setPassword(password);
			connectionFactory.setDatabase(database);
			connectionFactory.setTimeout(CONNECT_TIMEOUT);
			break;
		case "sharded":
			try {
				for (String node : hosts.split(",")) {
					connectionFactory = new JedisConnectionFactory(new JedisShardInfo(new URI(node)));
					connectionFactory.setDatabase(database);
					connectionFactory.setPoolConfig(config);
					connectionFactory.setTimeout(CONNECT_TIMEOUT);
					break;
				}
			} catch (URISyntaxException e) {
				throw new JedisConnectionException(e);
			}
			break;
		default:
			for (RedisNode node : nodes) {
				String host = node.getHost();
				int port = node.getPort();
				connectionFactory = new JedisConnectionFactory(config);
				connectionFactory.setPassword(password);
				connectionFactory.setHostName(host);
				connectionFactory.setPort(port);
				connectionFactory.setDatabase(database);
				connectionFactory.setTimeout(CONNECT_TIMEOUT);
				break;
			}
			if (!"single".equalsIgnoreCase(mode))
				log.warn("Redis mode [" + mode + "] not defined. Using 'single'.");
			break;
		}
		return connectionFactory;

	}

	@Bean("j2CacheRedisTemplate")
	@ConditionalOnBean(name = "j2CahceRedisConnectionFactory")
	public RedisTemplate<String, Serializable> j2CacheRedisTemplate(
			@Qualifier("j2CahceRedisConnectionFactory") JedisConnectionFactory j2CahceRedisConnectionFactory) {
		RedisTemplate<String, Serializable> template = new RedisTemplate<String, Serializable>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setDefaultSerializer(new J2CacheSerializer());
		template.setConnectionFactory(j2CahceRedisConnectionFactory);
		return template;
	}

	@Bean("j2CacheRedisMessageListenerContainer")
	@ConditionalOnBean(name = "j2CahceRedisConnectionFactory")
	RedisMessageListenerContainer container(@Qualifier("j2CahceRedisConnectionFactory") JedisConnectionFactory j2CahceRedisConnectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(j2CahceRedisConnectionFactory);
		return container;
	}

}
