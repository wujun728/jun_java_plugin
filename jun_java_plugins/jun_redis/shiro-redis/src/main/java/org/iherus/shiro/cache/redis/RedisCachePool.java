/**
 * Copyright (c) 2016-~, Bosco.Liao (bosco_liao@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.iherus.shiro.cache.redis;

import java.io.Serializable;

import org.apache.shiro.cache.CacheException;
import org.iherus.shiro.util.SerializeUtils;
import org.iherus.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>缓存池</p>
 * <p>Description:主要作用于JedisPool的实例化和提供获取当前池内缓存对象功能</p>
 * @author Wujun
 * @version 1.0.0
 * @date 2016年4月11日-下午3:59:53
 */
public class RedisCachePool extends JedisPool implements Serializable {

	/**
	 * Required for serialization support.
	 * 
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = -4990239742530217458L;

	/**
     * Private internal log instance.
     */
	private static final Logger logger = LoggerFactory.getLogger(RedisCachePool.class);

	/**
	 * The current application database.
	 */
	private int databaseIndex;

	/**
	 * Constructs a new RedisCache instance with the given factory.
	 */
	public RedisCachePool(RedisCacheConfigFactory factory) {
		super(factory.getPoolConfig(), factory.host, factory.getPort(), factory.getTimeout(), factory.getPassword(),
				factory.getDatabase(), factory.clientName);
		
		this.databaseIndex = factory.getDatabase();
	}

	/**
	 * Gets cache object from cache database.
	 */
	@SuppressWarnings("rawtypes")
	public RedisCache getCache(final String name) {

		RedisCache cache = null;

		byte[] serializedValue = null;

		if (StringUtils.isNotEmpty(name)) {
			Jedis jedis = null;
			try {
				jedis = this.getResource();
				byte[] key = name.getBytes();
				serializedValue = jedis.get(key);
				if (serializedValue == null) {
					if (logger.isInfoEnabled()) {
						logger.info("Cache with name '{}' does not yet exist.  Creating now.", name);
					}
					cache = new RedisCache(this, name);
					serializedValue = SerializeUtils.serialize(cache); // serialized
					jedis.set(key, serializedValue);
					if (logger.isInfoEnabled()) {
						logger.info("Added Cache named [" + name + "]");
					}
				} else {
					cache = (RedisCache) SerializeUtils.deserialize(serializedValue);
					if (logger.isInfoEnabled()) {
						logger.info("Using existing Cache named [" + cache.getName() + "]");
		            }
				}
				return cache;
			} catch (Exception e) {
				throw new CacheException(e);
			} finally {
				jedis.close();
			}
		} else {
			return null;
		}
	}
	
	public int getDatabaseIndex() {
		return databaseIndex;
	}

	public void setDatabaseIndex(int databaseIndex) {
		this.databaseIndex = databaseIndex;
	}

}
