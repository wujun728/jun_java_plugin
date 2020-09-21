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
package net.ymate.platform.cache;

/**
 * 缓存作用域处理器接口
 *
 * @author 刘镇 (suninformation@163.com) on 16/1/17 下午4:20
 * @version 1.0
 */
public interface ICacheScopeProcessor {

    /**
     * @param caches    缓存
     * @param scope     缓存作用域
     * @param cacheName 缓存名称
     * @param cacheKey  缓存KEY
     * @return 从缓存中获取由cacheName指定的缓存对象
     * @throws CacheException 可能产生的异常
     */
    CacheElement getFromCache(ICaches caches, ICaches.Scope scope, String cacheName, String cacheKey) throws CacheException;

    /**
     * 将对象放入缓存
     *
     * @param caches       缓存
     * @param scope        缓存作用域
     * @param cacheName    缓存名称
     * @param cacheKey     缓存KEY
     * @param cacheElement 缓存对象
     * @throws CacheException 可能产生的异常
     */
    void putInCache(ICaches caches, ICaches.Scope scope, String cacheName, String cacheKey, CacheElement cacheElement) throws CacheException;
}
