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
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.ClassUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 缓存模块配置类
 *
 * @author 刘镇 (suninformation@163.com) on 14/12/25 下午5:58
 * @version 1.0
 */
public class DefaultModuleCfg implements ICacheModuleCfg {

    private ICacheProvider __cacheProvider;

    private ICacheEventListener __cacheEventListener;

    private ICacheScopeProcessor __cacheScopeProcessor;

    private IKeyGenerator<?> __keyGenerator;

    private ISerializer __serializer;

    private String __defaultCacheName;

    private int __defaultCacheTimeout;

    public DefaultModuleCfg(YMP owner) throws Exception {
        Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(ICaches.MODULE_NAME);
        //
        String _providerClassStr = StringUtils.defaultIfBlank(_moduleCfgs.get("provider_class"), "default");
        __cacheProvider = ClassUtils.impl(StringUtils.defaultIfBlank(Caches.PROVIDERS.get(_providerClassStr), _providerClassStr), ICacheProvider.class, this.getClass());
        if (__cacheProvider == null) {
            __cacheProvider = new DefaultCacheProvider();
        }
        //
        __cacheEventListener = ClassUtils.impl(_moduleCfgs.get("event_listener_class"), ICacheEventListener.class, this.getClass());
        //
        __cacheScopeProcessor = ClassUtils.impl(_moduleCfgs.get("scope_processor_class"), ICacheScopeProcessor.class, this.getClass());
        //
        __serializer = ClassUtils.impl(_moduleCfgs.get("serializer_class"), ISerializer.class, this.getClass());
        if (__serializer == null) {
            __serializer = new DefaultSerializer();
        }
        //
        __keyGenerator = ClassUtils.impl(_moduleCfgs.get("key_generator_class"), IKeyGenerator.class, this.getClass());
        if (__keyGenerator == null) {
            __keyGenerator = new DefaultKeyGenerator();
        }
        __keyGenerator.init(__serializer);
        //
        __defaultCacheName = StringUtils.defaultIfBlank(_moduleCfgs.get("default_cache_name"), "default");

        __defaultCacheTimeout = BlurObject.bind(StringUtils.defaultIfBlank(_moduleCfgs.get("default_cache_timeout"), "0")).toIntValue();
        if (__defaultCacheTimeout <= 0) {
            __defaultCacheTimeout = 300;
        }
    }

    public ICacheProvider getCacheProvider() {
        return __cacheProvider;
    }

    public ICacheEventListener getCacheEventListener() {
        return __cacheEventListener;
    }

    public ICacheScopeProcessor getCacheScopeProcessor() {
        return __cacheScopeProcessor;
    }

    public IKeyGenerator<?> getKeyGenerator() {
        return __keyGenerator;
    }

    public ISerializer getSerializer() {
        return __serializer;
    }

    public String getDefaultCacheName() {
        return __defaultCacheName;
    }

    public int getDefaultCacheTimeout() {
        return __defaultCacheTimeout;
    }
}
