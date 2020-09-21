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
 * 缓存配置接口
 *
 * @author 刘镇 (suninformation@163.com) on 14-12-1 上午2:52
 * @version 1.0
 */
public interface ICacheModuleCfg {

    /**
     * @return 缓存提供者，可选参数，默认值为net.ymate.platform.cache.impl.DefaultCacheProvider
     */
    ICacheProvider getCacheProvider();

    /**
     * @return 缓存对象事件监听器，可选参数，默认值为空
     */
    ICacheEventListener getCacheEventListener();

    /**
     * @return 缓存作用域处理器，可选参数，默认值为空
     */
    ICacheScopeProcessor getCacheScopeProcessor();

    /**
     * @return 缓存Key生成器，可选参数，默认值为net.ymate.platform.cache.impl.DefaultKeyGenerator
     */
    IKeyGenerator<?> getKeyGenerator();

    /**
     * @return 对象序列化接口实现，可选参数，默认值为net.ymate.platform.cache.impl.DefaultSerializer
     */
    ISerializer getSerializer();

    /**
     * @return 默认缓存名称，可选参数，默认值为default，对应于Ehcache配置文件中设置name="__DEFAULT__"
     */
    String getDefaultCacheName();

    /**
     * @return 缓存数据超时时间，可选参数，数值必须大于等于0，为0表示默认缓存300秒
     */
    int getDefaultCacheTimeout();
}
