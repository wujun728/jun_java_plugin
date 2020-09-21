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
package net.ymate.platform.plugin.impl;

import net.ymate.platform.plugin.IPluginContext;
import net.ymate.platform.plugin.IPluginFactory;
import net.ymate.platform.plugin.PluginMeta;

/**
 * 默认插件环境上下文对象实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-10-18 上午10:39:03
 * @version 1.0
 */
public class DefaultPluginContext implements IPluginContext {

    private PluginMeta __meta;

    private IPluginFactory __factory;

    public DefaultPluginContext(IPluginFactory factory, PluginMeta meta) {
        this.__meta = meta;
        this.__factory = factory;
    }

    public PluginMeta getPluginMeta() {
        return __meta;
    }

    public IPluginFactory getPluginFactory() {
        return __factory;
    }
}
