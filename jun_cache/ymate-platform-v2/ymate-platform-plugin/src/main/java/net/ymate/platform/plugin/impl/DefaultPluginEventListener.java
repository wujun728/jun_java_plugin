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

import net.ymate.platform.plugin.IPlugin;
import net.ymate.platform.plugin.IPluginContext;
import net.ymate.platform.plugin.IPluginEventListener;

/**
 * 默认插件生命周期事件监听器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 15/3/22 下午4:56
 * @version 1.0
 */
public class DefaultPluginEventListener implements IPluginEventListener {

    public void onInited(IPluginContext context, IPlugin plugin) {
    }

    public void onStarted(IPluginContext context, IPlugin plugin) {
    }

    public void onShutdown(IPluginContext context, IPlugin plugin) {
    }

    public void onDestroy(IPluginContext context, IPlugin plugin) {
    }
}
