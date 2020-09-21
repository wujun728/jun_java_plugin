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

import net.ymate.platform.core.YMP;

/**
 * 插件工厂接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2011-10-17 下午04:49:03
 * @version 1.0
 */
public interface IPluginFactory {

    /**
     * 插件工厂初始化
     *
     * @param pluginConfig 插件初始化配置
     * @throws Exception 工厂初始化时可能产生的异常
     */
    void init(IPluginConfig pluginConfig) throws Exception;

    /**
     * @return 返回插件工厂是否已初始化
     */
    boolean isInited();

    /**
     * 销毁插件工厂
     *
     * @throws Exception 工厂销毁时可能产生的异常
     */
    void destroy() throws Exception;

    /**
     * 添加被排除的接口
     *
     * @param interfaceClass 预排除的接口
     */
    void addExcludedInterfaceClass(Class<?> interfaceClass);

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回插件工厂配置对象
     */
    IPluginConfig getPluginConfig();

    /**
     * @param id 插件唯一ID
     * @return 通过ID获取插件实例
     */
    IPlugin getPlugin(String id);

    /**
     * @param clazz 插件接口类
     * @param <T>   插件接口类型
     * @return 通过接口类型获取插件实例
     */
    <T> T getPlugin(Class<T> clazz);

    /**
     * @param id 插件ID
     * @return 通过ID获取插件配置信息元数据描述
     */
    PluginMeta getPluginMeta(String id);

    /**
     * @param clazz 插件接口类
     * @return 通过接口类型获取插件配置信息元数据描述
     */
    PluginMeta getPluginMeta(Class<? extends IPlugin> clazz);
}
