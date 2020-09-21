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
package net.ymate.platform.configuration.impl;

import net.ymate.platform.configuration.IConfiguration;
import net.ymate.platform.configuration.IConfigurationProvider;

import java.util.List;
import java.util.Map;

/**
 * 默认配置对象接口实现, 方便扩展实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-27 上午01:57:05
 * @version 1.0
 */
public class DefaultConfiguration implements IConfiguration {

    private IConfigurationProvider __provider;

    public String getString(String key) {
        return __provider.getString(key);
    }

    public String getString(String key, String defaultValue) {
        return __provider.getString(key, defaultValue);
    }

    public String getString(String category, String key, String defaultValue) {
        return __provider.getString(category, key, defaultValue);
    }

    public List<String> getList(String key) {
        return __provider.getList(key);
    }

    public List<String> getList(String category, String key) {
        return __provider.getList(category, key);
    }

    public Map<String, String> getMap(String keyHead) {
        return __provider.getMap(keyHead);
    }

    public Map<String, String> getMap(String category, String keyHead) {
        return __provider.getMap(category, keyHead);
    }

    public String[] getArray(String key) {
        return __provider.getArray(key);
    }

    public String[] getArray(String key, boolean zeroSize) {
        return __provider.getArray(key, zeroSize);
    }

    public String[] getArray(String category, String key, boolean zeroSize) {
        return __provider.getArray(category, key, zeroSize);
    }

    public int getInt(String key) {
        return __provider.getInt(key);
    }

    public int getInt(String key, int defaultValue) {
        return __provider.getInt(key, defaultValue);
    }

    public int getInt(String category, String key, int defaultValue) {
        return __provider.getInt(category, key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return __provider.getBoolean(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return __provider.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String category, String key, boolean defaultValue) {
        return __provider.getBoolean(category, key, defaultValue);
    }

    public long getLong(String key) {
        return __provider.getLong(key);
    }

    public long getLong(String key, long defaultValue) {
        return __provider.getLong(key, defaultValue);
    }

    public float getFloat(String key) {
        return __provider.getFloat(key);
    }

    public float getFloat(String key, float defaultValue) {
        return __provider.getFloat(key, defaultValue);
    }

    public double getDouble(String key) {
        return __provider.getDouble(key);
    }

    public double getDouble(String key, double defaultValue) {
        return __provider.getDouble(key, defaultValue);
    }

    public Map<String, String> toMap() {
        return __provider.toMap();
    }

    public Map<String, String> toMap(String category) {
        return __provider.toMap(category);
    }

    public List<String> getCategoryNames() {
        return __provider.getCategoryNames();
    }

    public void initialize(IConfigurationProvider provider) {
        this.__provider = provider;
    }

    public String getTagName() {
        return ".cfg";
    }

    public boolean contains(String key) {
        return __provider.contains(key);
    }

    public boolean contains(String category, String key) {
        return __provider.contains(category, key);
    }

}