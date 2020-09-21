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
package net.ymate.platform.webmvc.support;

import net.ymate.platform.cache.CacheElement;
import net.ymate.platform.cache.CacheException;
import net.ymate.platform.cache.ICacheScopeProcessor;
import net.ymate.platform.cache.ICaches;
import net.ymate.platform.webmvc.context.WebContext;

/**
 * @author 刘镇 (suninformation@163.com) on 16/1/17 下午6:10
 * @version 1.0
 */
public class WebCacheScopeProcessor implements ICacheScopeProcessor {

    private String __doBuildSessionCacheKey(String cacheKey) {
        String _sessionId = WebContext.getRequest().getSession().getId();
        return _sessionId + "|" + cacheKey;
    }

    public CacheElement getFromCache(ICaches caches, ICaches.Scope scope, String cacheName, String cacheKey) throws CacheException {
        CacheElement _returnValue = null;
        switch (scope) {
            case SESSION:
                _returnValue = (CacheElement) caches.get(scope.name(), __doBuildSessionCacheKey(cacheKey));
                break;
            case APPLICATION:
            default:
                _returnValue = (CacheElement) caches.get(ICaches.Scope.APPLICATION.name(), cacheKey);
        }
        return _returnValue;
    }

    public void putInCache(ICaches caches, ICaches.Scope scope, String cacheName, String cacheKey, CacheElement cacheElement) throws CacheException {
        switch (scope) {
            case SESSION:
                caches.put(scope.name(), __doBuildSessionCacheKey(cacheKey), cacheElement);
                break;
            case APPLICATION:
            default:
                caches.put(ICaches.Scope.APPLICATION.name(), cacheKey, cacheElement);
        }
    }
}
