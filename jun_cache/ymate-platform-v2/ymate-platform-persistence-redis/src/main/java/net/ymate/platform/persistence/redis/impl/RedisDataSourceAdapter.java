/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.persistence.redis.impl;

import net.ymate.platform.persistence.redis.IRedisCommandsHolder;
import net.ymate.platform.persistence.redis.IRedisDataSourceAdapter;
import net.ymate.platform.persistence.redis.IRedisModuleCfg;
import net.ymate.platform.persistence.redis.RedisDataSourceCfgMeta;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 刘镇 (suninformation@163.com) on 15/12/2 上午2:31
 * @version 1.0
 */
public class RedisDataSourceAdapter implements IRedisDataSourceAdapter {

    private Pool __pool;

    private RedisDataSourceCfgMeta __cfgMeta;

    public void initialize(RedisDataSourceCfgMeta cfgMeta) throws Exception {
        __cfgMeta = cfgMeta;
        //
        if ("default".equalsIgnoreCase(cfgMeta.getConnectionType())) {
            if (cfgMeta.getServers().isEmpty()) {
                __pool = new JedisPool(cfgMeta.getPoolConfig(), "localhost");
            } else {
                IRedisModuleCfg.ServerMeta _server = cfgMeta.getServers().get("default");
                __pool = new JedisPool(cfgMeta.getPoolConfig(),
                        _server.getHost(),
                        _server.getPort(),
                        _server.getTimeout(),
                        _server.getPassword(),
                        _server.getDatabase(),
                        _server.getClientName());
            }
        } else if ("shard".equalsIgnoreCase(cfgMeta.getConnectionType())) {
            if (!cfgMeta.getServers().isEmpty()) {
                List<JedisShardInfo> _shards = new ArrayList<JedisShardInfo>();
                for (IRedisModuleCfg.ServerMeta _server : cfgMeta.getServers().values()) {
                    _shards.add(new JedisShardInfo(_server.getHost(), _server.getName(), _server.getPort(), _server.getTimeout(), _server.getWeight()));
                }
                __pool = new ShardedJedisPool(cfgMeta.getPoolConfig(), _shards);
            }
        } else if ("sentinel".equalsIgnoreCase(cfgMeta.getConnectionType())) {
            if (!cfgMeta.getServers().isEmpty()) {
                Set<String> _sentinel = new HashSet<String>();
                for (IRedisModuleCfg.ServerMeta _server : cfgMeta.getServers().values()) {
                    _sentinel.add(_server.getHost() + ":" + _server.getPort());
                }
                IRedisModuleCfg.ServerMeta _server = cfgMeta.getServers().get(cfgMeta.getMasterServerName());
                __pool = new JedisSentinelPool(cfgMeta.getMasterServerName(),
                        _sentinel, cfgMeta.getPoolConfig(),
                        _server.getTimeout(), _server.getPassword(), _server.getDatabase(), _server.getClientName());
            }
        }
    }

    public IRedisCommandsHolder getCommandsHolder() {
        return new RedisCommandsHolder(__cfgMeta, (JedisCommands) __pool.getResource());
    }

    public RedisDataSourceCfgMeta getDataSourceCfgMeta() {
        return __cfgMeta;
    }

    public void destroy() {
        __pool.destroy();
    }
}
