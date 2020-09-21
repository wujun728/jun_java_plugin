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
package net.ymate.platform.cache.support;

import net.sf.ehcache.Ehcache;
import net.ymate.platform.cache.*;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.persistence.redis.IRedis;

import java.util.Collection;
import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 15/12/7 上午2:23
 * @version 1.0
 */
public class MultilevelCacheWraper implements ICache, ICacheLocker {

    private ICache __masterCache;
    private ICache __slaveCache;

    private boolean __slaveCacheAutosync;

    public MultilevelCacheWraper(ICaches owner, String cacheName, Ehcache ehcache, IRedis redis, ICacheEventListener listener) {
        __masterCache = new EhCacheWraper(owner, ehcache, listener);
        __slaveCache = new RedisCacheWraper(owner, redis, cacheName, null);
        //
        __slaveCacheAutosync = BlurObject.bind(owner.getOwner().getConfig().getParam("cache.multilevel_slave_autosync")).toBooleanValue();
    }

    @Override
    public Object get(Object key) throws CacheException {
        MultilevelKey _key = MultilevelKey.bind(key);
        if (_key.isMaster()) {
            Object _obj = __masterCache.get(_key.getKey());
            if (__slaveCacheAutosync) {
                if (_obj == null) {
                    _obj = __slaveCache.get(_key.getKey());
                    if (_obj != null) {
                        __masterCache.put(_key.getKey(), _obj);
                    }
                }
            }
            return _obj;
        }
        return __slaveCache.get(_key.getKey());
    }

    @Override
    public void put(Object key, Object value) throws CacheException {
        MultilevelKey _key = MultilevelKey.bind(key);
        if (_key.isMaster()) {
            __masterCache.put(_key.getKey(), value);
            if (__slaveCacheAutosync) {
                __slaveCache.put(_key.getKey(), value);
            }
        } else {
            __slaveCache.put(_key.getKey(), value);
        }
    }

    @Override
    public void update(Object key, Object value) throws CacheException {
        MultilevelKey _key = MultilevelKey.bind(key);
        if (_key.isMaster()) {
            __masterCache.update(_key.getKey(), value);
            if (__slaveCacheAutosync) {
                __slaveCache.update(_key.getKey(), value);
            }
        } else {
            __slaveCache.update(_key.getKey(), value);
        }
    }

    @Override
    public List keys() throws CacheException {
        return __masterCache.keys();
    }

    public List keys(boolean master) throws CacheException {
        if (master) {
            return __masterCache.keys();
        }
        return __slaveCache.keys();
    }

    @Override
    public void remove(Object key) throws CacheException {
        MultilevelKey _key = MultilevelKey.bind(key);
        if (_key.isMaster()) {
            __masterCache.remove(_key.getKey());
            if (__slaveCacheAutosync) {
                __slaveCache.remove(_key.getKey());
            }
        } else {
            __slaveCache.remove(_key.getKey());
        }
    }

    @Override
    public void removeAll(Collection<?> keys) throws CacheException {
        __masterCache.removeAll(keys);
    }

    public void removeAll(boolean master, Collection<?> keys) throws CacheException {
        if (master) {
            __masterCache.removeAll(keys);
            if (__slaveCacheAutosync) {
                __slaveCache.removeAll(keys);
            }
        } else {
            __slaveCache.removeAll(keys);
        }
    }

    @Override
    public void clear() throws CacheException {
        __masterCache.clear();
    }

    public void clear(boolean master) throws CacheException {
        if (master) {
            __masterCache.clear();
            if (__slaveCacheAutosync) {
                __slaveCache.clear();
            }
        } else {
            __slaveCache.clear();
        }
    }

    @Override
    public void destroy() throws CacheException {
        __slaveCache.destroy();
        __masterCache.destroy();
    }

    public ICacheLocker acquireCacheLocker() {
        return this;
    }

    public void readLock(Object key) {
        MultilevelKey _key = MultilevelKey.bind(key);
        __masterCache.acquireCacheLocker().readLock(_key.getKey());
    }

    public void writeLock(Object key) {
        MultilevelKey _key = MultilevelKey.bind(key);
        __masterCache.acquireCacheLocker().writeLock(_key.getKey());
    }

    public boolean tryReadLock(Object key, long timeout) throws CacheException {
        MultilevelKey _key = MultilevelKey.bind(key);
        return __masterCache.acquireCacheLocker().tryReadLock(_key.getKey(), timeout);
    }

    public boolean tryWriteLock(Object key, long timeout) throws CacheException {
        MultilevelKey _key = MultilevelKey.bind(key);
        return __masterCache.acquireCacheLocker().tryWriteLock(_key.getKey(), timeout);
    }

    public void releaseReadLock(Object key) {
        MultilevelKey _key = MultilevelKey.bind(key);
        __masterCache.acquireCacheLocker().releaseReadLock(_key.getKey());
    }

    public void releaseWriteLock(Object key) {
        MultilevelKey _key = MultilevelKey.bind(key);
        __masterCache.acquireCacheLocker().releaseWriteLock(_key.getKey());
    }
}
