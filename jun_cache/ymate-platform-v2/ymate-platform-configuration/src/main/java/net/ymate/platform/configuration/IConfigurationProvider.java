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
package net.ymate.platform.configuration;

import java.util.List;
import java.util.Map;

/**
 * 配置提供者接口，通过配置提供者来获取配置文件内容
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-27 上午12:23:13
 * @version 1.0
 */
public interface IConfigurationProvider {

    /**
     * 根据配置文件绝对路径加载配置
     *
     * @param cfgFileName 配置文件路径及名称
     * @throws Exception 加载配置文件可能产生的异常
     */
    void load(String cfgFileName) throws Exception;

    /**
     * 重新加载配置文件内容
     *
     * @throws Exception 加载配置文件可能产生的异常
     */
    void reload() throws Exception;

    /**
     * @return 返回当前加载的配置文件路径名称
     */
    String getCfgFileName();

    // ----------

    /**
     * @param key 属性键
     * @return 获得对应的文字值
     */
    String getString(String key);

    String getString(String key, String defaultValue);

    String getString(String category, String key, String defaultValue);

    /**
     * @param key 属性键
     * @return 获得对应的文字值列表，其中匹配以key开头的键串
     */
    List<String> getList(String key);

    List<String> getList(String category, String key);

    /**
     * @param key 键标识
     * @return 获取键值映射
     */
    Map<String, String> getMap(String key);

    Map<String, String> getMap(String category, String key);

    /**
     * @param key 属性键
     * @return 获取键值数组值
     */
    String[] getArray(String key);

    String[] getArray(String key, boolean zeroSize);

    String[] getArray(String category, String key, boolean zeroSize);

    /**
     * @param key 属性键
     * @return 获得对应的数字值
     */
    int getInt(String key);

    int getInt(String key, int defaultValue);

    int getInt(String category, String key, int defaultValue);

    /**
     * @param key 属性键
     * @return 获得对应的布尔值
     */
    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean defaultValue);

    boolean getBoolean(String category, String key, boolean defaultValue);

    /**
     * @param key 属性键
     * @return 获取长整数
     */
    long getLong(String key);

    long getLong(String key, long defaultValue);

    long getLong(String category, String key, long defaultValue);

    /**
     * @param key 属性键
     * @return 获取浮点数
     */
    float getFloat(String key);

    float getFloat(String key, float defaultValue);

    float getFloat(String category, String key, float defaultValue);

    /**
     * @param key 属性键
     * @return 获取双精度浮点数
     */
    double getDouble(String key);

    double getDouble(String key, double defaultValue);

    double getDouble(String category, String key, double defaultValue);

    /**
     * @return 获得配置对象内部加载的配置项映射
     */
    Map<String, String> toMap();

    Map<String, String> toMap(String category);

    /**
     * @return 获取分类的名称集合
     */
    List<String> getCategoryNames();

    /**
     * 判断键key的配置项是否存在
     *
     * @param key 属性键
     * @return 如果存在配置项那么返回true，否则返回false
     */
    boolean contains(String key);

    boolean contains(String category, String key);

}
