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

import net.ymate.platform.core.util.UUIDUtils;
import net.ymate.platform.persistence.ISessionEvent;
import net.ymate.platform.persistence.redis.IRedisCommandsHolder;
import net.ymate.platform.persistence.redis.IRedisSession;
import redis.clients.jedis.JedisCommands;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/30 下午11:54
 * @version 1.0
 */
public class RedisSession implements IRedisSession {

    private String __id;

    private IRedisCommandsHolder __commandsHolder;

    private ISessionEvent __sessionEvent;

    public <T extends JedisCommands> RedisSession(IRedisCommandsHolder commandsHolder) {
        this.__id = UUIDUtils.UUID();
        this.__commandsHolder = commandsHolder;
    }

    public String getId() {
        return __id;
    }

    public RedisSession setSessionEvent(ISessionEvent event) {
        __sessionEvent = event;
        return this;
    }

    public void close() {
        __commandsHolder.release();
    }

    public IRedisCommandsHolder getCommandHolder() {
        return __commandsHolder;
    }
}
