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
 * 缓存提供者接口
 *
 * @author 刘镇 (suninformation@163.com) on 14/10/17
 * @version 1.0
 */
public interface ICacheProvider {

    /**
     * @return 缓存提供者名称
     */
    String getName();

    /**
     * 初始化
     *
     * @param owner 所属缓存模块
     * @throws CacheException 可能产生的异常
     */
    void init(ICaches owner) throws CacheException;

    /**
     * @param name     缓存名称
     * @param listener 缓存元素过期监听器接口实现
     * @return 创建缓存对象，若已存在则直接返回
     * @throws CacheException 可能产生的异常
     */
    ICache createCache(String name, ICacheEventListener listener) throws CacheException;

    /**
     * @param name 缓存名称
     * @return 获取缓存对象，若不存在则返回null
     */
    ICache getCache(String name);

    /**
     * @param name   缓存名称
     * @param create 是否创建缓存对象
     * @return 获取缓存对象，若不存在则根据create参数决定是否创建缓存对象或返回null
     */
    ICache getCache(String name, boolean create);

    /**
     * @param name     缓存名称
     * @param create   是否创建缓存对象
     * @param listener 缓存元素过期监听器接口实现
     * @return 获取缓存对象，若不存在则根据create参数决定是否创建缓存对象或返回null
     */
    ICache getCache(String name, boolean create, ICacheEventListener listener);

    /**
     * 销毁
     *
     * @throws CacheException 可能产生的异常
     */
    void destroy() throws CacheException;
}
