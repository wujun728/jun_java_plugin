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

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.ymate.platform.cache.ICache;
import net.ymate.platform.cache.ICacheEventListener;
import net.ymate.platform.cache.ICacheProvider;
import net.ymate.platform.cache.ICaches;
import net.ymate.platform.cache.support.EhCacheWraper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 刘镇 (suninformation@163.com) on 14/10/17
 * @version 1.0
 */
public class DefaultCacheProvider implements ICacheProvider {

    private CacheManager __cacheManager;

    private Map<String, ICache> __caches;

    private static final Object __LOCKER = new Object();

    protected ICaches __owner;

    public String getName() {
        return "default";
    }

    private String __safedCacheName(String name) {
        if ("default".equalsIgnoreCase(name)) {
            name = CacheManager.DEFAULT_NAME;
        }
        return name;
    }

    public void init(ICaches owner) {
        __owner = owner;
        __cacheManager = CacheManager.create();
        __caches = new ConcurrentHashMap<String, ICache>();
    }

    public ICache createCache(String name, final ICacheEventListener listener) {
        name = __safedCacheName(name);
        //
        ICache _cache = __caches.get(name);
        if (_cache == null) {
            synchronized (__LOCKER) {
                Ehcache _ehcache = __cacheManager.getEhcache(name);
                //
                if (_ehcache == null) {
                    __cacheManager.addCache(name);
                    _ehcache = __cacheManager.getCache(name);
                }
                _cache = new EhCacheWraper(__owner, _ehcache, listener);
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
        ICache _cache = __caches.get(__safedCacheName(name));
        if (_cache == null && create) {
            _cache = createCache(name, listener);
        }
        return _cache;
    }

    public void destroy() {
        for (ICache _cache : __caches.values()) {
            _cache.destroy();
        }
        __caches.clear();
        __caches = null;
        //
        __cacheManager.shutdown();
        __cacheManager = null;
    }
}
