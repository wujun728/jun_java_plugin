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

import net.ymate.platform.cache.CacheElement;
import net.ymate.platform.cache.Caches;
import net.ymate.platform.cache.ICacheScopeProcessor;
import net.ymate.platform.cache.ICaches;
import net.ymate.platform.cache.annotation.Cacheable;
import net.ymate.platform.core.beans.annotation.Order;
import net.ymate.platform.core.beans.annotation.Proxy;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyChain;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/3 下午6:20
 * @version 1.0
 */
@Proxy(annotation = Cacheable.class, order = @Order(-666))
public class CacheableProxy implements IProxy {

    private static ConcurrentHashMap<String, ReentrantLock> __LOCK_MAP = new ConcurrentHashMap<String, ReentrantLock>();

    public Object doProxy(IProxyChain proxyChain) throws Throwable {
        ICaches _caches = Caches.get(proxyChain.getProxyFactory().getOwner());
        //
        Cacheable _anno = proxyChain.getTargetMethod().getAnnotation(Cacheable.class);
        if (_anno == null) {
            return proxyChain.doProxyChain();
        }
        //
        Object _cacheKey = StringUtils.trimToNull(_anno.key());
        if (_cacheKey == null) {
            _cacheKey = _caches.getModuleCfg().getKeyGenerator().generateKey(proxyChain.getTargetMethod(), proxyChain.getMethodParams());
        }
        ReentrantLock _locker = __LOCK_MAP.get(_cacheKey.toString());
        if (_locker == null) {
            _locker = new ReentrantLock();
            ReentrantLock _previous = __LOCK_MAP.putIfAbsent(_cacheKey.toString(), _locker);
            if (_previous != null) {
                _locker = _previous;
            }
        }
        _locker.lock();
        CacheElement _result = null;
        try {
            ICacheScopeProcessor _scopeProc = _caches.getModuleCfg().getCacheScopeProcessor();
            if (!_anno.scope().equals(ICaches.Scope.DEFAULT) && _scopeProc != null) {
                _result = _scopeProc.getFromCache(_caches, _anno.scope(), _anno.cacheName(), _cacheKey.toString());
            } else {
                _result = (CacheElement) _caches.get(_anno.cacheName(), _cacheKey);
            }
            boolean _flag = true;
            if (_result != null && !_result.isExpired()) {
                _flag = false;
            }
            if (_flag) {
                Object _cacheTarget = proxyChain.doProxyChain();
                if (_cacheTarget != null) {
                    _result = new CacheElement(_cacheTarget);
                    int _timeout = _anno.timeout() > 0 ? _anno.timeout() : _caches.getModuleCfg().getDefaultCacheTimeout();
                    if (_timeout > 0) {
                        _result.setTimeout(_timeout);
                    }
                    if (!_anno.scope().equals(ICaches.Scope.DEFAULT) && _scopeProc != null) {
                        _scopeProc.putInCache(_caches, _anno.scope(), _anno.cacheName(), _cacheKey.toString(), _result);
                    } else {
                        _caches.put(_anno.cacheName(), _cacheKey, _result);
                    }
                }
            }
        } finally {
            _locker.unlock();
        }
        return _result != null ? _result.getObject() : null;
    }
}
