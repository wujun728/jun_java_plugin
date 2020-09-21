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
import net.ymate.platform.persistence.redis.RedisDataSourceCfgMeta;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.ShardedJedis;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author 刘镇 (suninformation@163.com) on 15/12/4 上午1:04
 * @version 1.0
 */
public class RedisCommandsHolder implements IRedisCommandsHolder {

    private static final Log _LOG = LogFactory.getLog(RedisCommandsHolder.class);

    private JedisCommands __commands;

    private RedisDataSourceCfgMeta __cfgMeta;

    public RedisCommandsHolder(RedisDataSourceCfgMeta cfgMeta, JedisCommands commands) {
        __cfgMeta = cfgMeta;
        __commands = commands;
    }

    public JedisCommands getCommands() {
        return __commands;
    }

    public RedisDataSourceCfgMeta getDataSourceCfgMeta() {
        return __cfgMeta;
    }

    public Jedis getJedis() {
        if (__commands instanceof Jedis) {
            return (Jedis) __commands;
        }
        return null;
    }

    public ShardedJedis getShardedJedis() {
        if (__commands instanceof ShardedJedis) {
            return (ShardedJedis) __commands;
        }
        return null;
    }

    public void release() {
        if (__commands instanceof Jedis) {
            ((Jedis) __commands).close();
        } else if (__commands instanceof ShardedJedis) {
            ((ShardedJedis) __commands).close();
        } else if (__commands instanceof Closeable) {
            try {
                ((Closeable) __commands).close();
            } catch (IOException e) {
                _LOG.warn("", e);
                __commands = null;
            }
        }
    }
}
