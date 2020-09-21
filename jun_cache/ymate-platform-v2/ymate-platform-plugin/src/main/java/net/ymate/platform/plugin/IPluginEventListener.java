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
package net.ymate.platform.plugin;

/**
 * 插件生命周期事件监听器接口
 *
 * @author 刘镇 (suninformation@163.com) on 15/3/21 上午10:44
 * @version 1.0
 */
public interface IPluginEventListener {

    /**
     * 插件初始化完毕将调用此方法
     *
     * @param context 插件上下文对象
     * @param plugin  插件实例
     */
    void onInited(IPluginContext context, IPlugin plugin);

    /**
     * 插件启动完毕后将调用此方法
     *
     * @param context 插件上下文对象
     * @param plugin  插件实例
     */
    void onStarted(IPluginContext context, IPlugin plugin);

    /**
     * 插件停止完毕将调用此方法
     *
     * @param context 插件上下文对象
     * @param plugin  插件实例
     */
    void onShutdown(IPluginContext context, IPlugin plugin);

    /**
     * 插件初销毁前将调用此方法
     *
     * @param context 插件上下文对象
     * @param plugin  插件实例
     */
    void onDestroy(IPluginContext context, IPlugin plugin);
}
