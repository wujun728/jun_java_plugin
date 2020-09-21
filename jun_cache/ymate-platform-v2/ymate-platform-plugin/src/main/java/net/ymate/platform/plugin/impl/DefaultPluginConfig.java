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

import net.ymate.platform.plugin.IPluginConfig;
import net.ymate.platform.plugin.IPluginEventListener;

import java.io.File;
import java.util.List;

/**
 * 默认插件初始化配置接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 15/3/22 下午5:02
 * @version 1.0
 */
public class DefaultPluginConfig implements IPluginConfig {

    private List<String> __packageNames;

    private IPluginEventListener __pluginEventListener;

    private boolean __automatic;

    private boolean __includedClassPath;

    private File __pluginHome;

    public DefaultPluginConfig() {
    }

    public List<String> getAutoscanPackages() {
        return __packageNames;
    }

    public void setAutoscanPackages(List<String> autoscanPackages) {
        this.__packageNames = autoscanPackages;
    }

    public IPluginEventListener getPluginEventListener() {
        return __pluginEventListener;
    }

    public void setPluginEventListener(IPluginEventListener pluginEventListener) {
        this.__pluginEventListener = pluginEventListener;
    }

    public boolean isAutomatic() {
        return __automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.__automatic = automatic;
    }

    public boolean isIncludedClassPath() {
        return __includedClassPath;
    }

    public void setIncludedClassPath(boolean includedClassPath) {
        this.__includedClassPath = includedClassPath;
    }

    public File getPluginHome() {
        return __pluginHome;
    }

    public void setPluginHome(File pluginHome) {
        this.__pluginHome = pluginHome;
    }
}
