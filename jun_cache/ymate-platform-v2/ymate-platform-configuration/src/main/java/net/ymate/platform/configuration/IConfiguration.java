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
 * 配置对象接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-27 上午12:20:51
 * @version 1.0
 */
public interface IConfiguration {

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
     * @param keyHead 键头标识
     * @return 获取键值映射
     */
    Map<String, String> getMap(String keyHead);

    Map<String, String> getMap(String category, String keyHead);

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

    /**
     * @param key 属性键
     * @return 获取浮点数
     */
    float getFloat(String key);

    float getFloat(String key, float defaultValue);

    /**
     * @param key 属性键
     * @return 获取双精度浮点数
     */
    double getDouble(String key);

    double getDouble(String key, double defaultValue);

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

    /**
     * 初始化配置项，一般用来从provider中获取配置项，是实现自定义的配置项进入内存的位置
     *
     * @param provider 配置提供者
     */
    void initialize(IConfigurationProvider provider);

    /**
     * @return 获得配置文件自定义标签名称，即返回值加在配置文件名与扩展名中间，形成XXXX.YYY.xml形式，其中需要返回.YYY
     */
    String getTagName();

}