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
package net.ymate.platform.core.event.impl;

import net.ymate.platform.core.event.Events;
import net.ymate.platform.core.event.IEventConfig;
import net.ymate.platform.core.event.IEventProvider;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.ClassUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.Map;

/**
 * 默认事件配置
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/17 下午4:06
 * @version 1.0
 */
public class DefaultEventConfig implements IEventConfig {

    private IEventProvider __eventProvider;

    private Events.MODE __defaultMode;

    private int __threadPoolSize;

    private Map<String, String> __params;

    public DefaultEventConfig() {
        this(null);
    }

    public DefaultEventConfig(Map<String, String> params) {
        if (params != null) {
            __params = params;
        } else {
            __params = Collections.emptyMap();
        }
        //
        __eventProvider = ClassUtils.impl(params != null ? params.get("provider_class") : null, IEventProvider.class, this.getClass());
        if (__eventProvider == null) {
            __eventProvider = new DefaultEventProvider();
        }
        //
        __defaultMode = Events.MODE.valueOf(StringUtils.defaultIfBlank(params != null ? params.get("default_mode") : null, "ASYNC").toUpperCase());
        //
        __threadPoolSize = BlurObject.bind(params != null ? params.get("thread_pool_size") : null).toIntValue();
        if (__threadPoolSize <= 0) {
            __threadPoolSize = Runtime.getRuntime().availableProcessors();
        }
    }

    public IEventProvider getEventProvider() {
        return __eventProvider;
    }

    public Events.MODE getDefaultMode() {
        return __defaultMode;
    }

    public int getThreadPoolSize() {
        return __threadPoolSize;
    }

    public String getParamExtend(String paramName) {
        return __params.get("params." + paramName);
    }
}
