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
package net.ymate.platform.cache.impl;

import net.ymate.platform.cache.*;
import net.ymate.platform.cache.support.RedisCacheWraper;
import net.ymate.platform.persistence.redis.IRedis;
import net.ymate.platform.persistence.redis.Redis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 刘镇 (suninformation@163.com) on 15/12/6 上午4:20
 * @version 1.0
 */
public class RedisCacheProvider implements ICacheProvider {

    private Map<String, ICache> __caches;

    private IRedis __redis;

    private static final Object __LOCKER = new Object();

    protected ICaches __owner;

    public String getName() {
        return "redis";
    }

    public void init(ICaches owner) throws CacheException {
        __owner = owner;
        __caches = new ConcurrentHashMap<String, ICache>();
        __redis = Redis.get(__owner.getOwner());
    }

    public ICache createCache(final String name, final ICacheEventListener listener) throws CacheException {
        ICache _cache = __caches.get(name);
        if (_cache == null) {
            synchronized (__LOCKER) {
                _cache = new RedisCacheWraper(__owner, __redis, name, listener);
                __caches.put(name, _cache);
            }
        }
        return _cache;
    }

    public ICache getCache(String name) {
        return getCache(name, true);
    }

    public ICache getCache(String name, boolean create) {
        return getCache(name, create, __owner.getModuleCfg().getCacheEventListener());
    }

    public ICache getCache(String name, boolean create, ICacheEventListener listener) {
        ICache _cache = __caches.get(name);
        if (_cache == null && create) {
            _cache = createCache(name, listener);
        }
        return _cache;
    }

    public void destroy() throws CacheException {
        for (ICache _cache : __caches.values()) {
            _cache.destroy();
        }
        __caches.clear();
        __caches = null;
    }
}
