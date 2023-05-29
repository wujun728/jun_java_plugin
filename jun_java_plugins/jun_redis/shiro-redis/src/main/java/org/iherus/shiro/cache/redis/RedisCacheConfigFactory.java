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

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * <p>客户端配置工厂</p>
 * <p>Description:缓存池主要依赖本工厂实现实例化</p>
 * @author Wujun
 * @version 1.0.0
 * @date 2016年4月11日-下午4:05:08
 */
public class RedisCacheConfigFactory implements Serializable {

	/**
	 * Required for serialization support.
	 * 
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 6348565977009073672L;

	private static final String DEFAULT_HOST = "127.0.0.1";

	private static final String DEFAULT_CLIENT_NAME = "shiro-cache";

	private static final int DEFAULT_DATABASE = 2;

	protected String host = DEFAULT_HOST;

	protected int port = Protocol.DEFAULT_PORT;

	protected int timeout = Protocol.DEFAULT_TIMEOUT;

	protected int database = DEFAULT_DATABASE;

	protected String password = null;

	protected String clientName = DEFAULT_CLIENT_NAME;

	/**
	 * JedisPoolConfig
	 */
	protected JedisPoolConfig poolConfig = null;

	/**
	 * Default constructor
	 */
	public RedisCacheConfigFactory() {
		if (poolConfig == null) {
			this.poolConfig = new JedisPoolConfig();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientName == null) ? 0 : clientName.hashCode());
		result = prime * result + database;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((poolConfig == null) ? 0 : poolConfig.hashCode());
		result = prime * result + port;
		result = prime * result + timeout;
		return result;
	}

	@Override
	public String toString() {
		return "RedisConfig [host=" + host + ", port=" + port + ", timeout=" + timeout + ", database=" + database
				+ ", password=" + password + ", clientName=" + clientName + ", poolConfig=" + poolConfig + "]";
	}

}
