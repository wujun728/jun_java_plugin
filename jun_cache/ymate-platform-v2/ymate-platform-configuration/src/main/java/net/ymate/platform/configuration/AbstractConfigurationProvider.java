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

import net.ymate.platform.configuration.impl.XMLConfigFileParser;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.FileUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/7/31 上午12:39
 * @version 1.0
 */
public abstract class AbstractConfigurationProvider implements IConfigurationProvider {

    /**
     * 配置对象缓存，对于重复的文件加载会使用缓存，减少文件读写频率
     */
    private static final Map<String, IConfigFileParser> __CONFIG_CACHE_MAPS = new ConcurrentHashMap<String, IConfigFileParser>();

    /**
     * 配置对象
     */
    private IConfigFileParser __configFileParser;

    /**
     * 装载配置文件参数
     */
    private String __cfgFileName;

    public void load(String cfgFileName) throws Exception {
        if (StringUtils.isBlank(cfgFileName)) {
            throw new NullArgumentException("cfgFileName");
        }
        __cfgFileName = cfgFileName;
        if ((__configFileParser = __CONFIG_CACHE_MAPS.get(cfgFileName)) == null) {
            __configFileParser = __buildConfigFileParser(FileUtils.toURL(cfgFileName)).load(true);
            __CONFIG_CACHE_MAPS.put(cfgFileName, __configFileParser);
        }
    }

    protected abstract IConfigFileParser __buildConfigFileParser(URL cfgFileName) throws Exception;

    protected IConfigFileParser __getConfigFileParser() {
        return __configFileParser;
    }

    public void reload() throws Exception {
        // 移除缓存项
        __CONFIG_CACHE_MAPS.remove(__cfgFileName);
        // 加载配置
        load(__cfgFileName);
    }

    public String getCfgFileName() {
        return __cfgFileName;
    }

    public String getString(String key) {
        XMLConfigFileParser.XMLProperty _prop = __configFileParser.getDefaultCategory().getProperty(key);
        return _prop == null ? null : _prop.getContent();
    }

    public String getString(String key, String defaultValue) {
        return StringUtils.defaultIfEmpty(getString(key), defaultValue);
    }

    public String getString(String category, String key, String defaultValue) {
        XMLConfigFileParser.XMLCategory _category = __configFileParser.getCategory(category);
        if (_category == null) {
            return null;
        }
        XMLConfigFileParser.XMLProperty _prop = _category.getProperty(key);
        return StringUtils.defaultIfEmpty(_prop == null ? null : _prop.getContent(), defaultValue);
    }

    public List<String> getList(String key) {
        return getList(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key);
    }

    public List<String> getList(String category, String key) {
        List<String> _returnValue = new ArrayList<String>();
        XMLConfigFileParser.XMLProperty _prop = __configFileParser.getCategory(category).getProperty(key);
        if (_prop != null) {
            for (XMLConfigFileParser.XMLAttribute _attr : _prop.getAttributeMap().values()) {
                if (StringUtils.isBlank(_attr.getValue())) {
                    _returnValue.add(_attr.getKey());
                }
            }
        }
        return _returnValue;
    }

    public Map<String, String> getMap(String key) {
        return getMap(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key);
    }

    public Map<String, String> getMap(String category, String key) {
        Map<String, String> _returnValue = new LinkedHashMap<String, String>();
        XMLConfigFileParser.XMLProperty _prop = __configFileParser.getCategory(category).getProperty(key);
        if (_prop != null) {
            for (XMLConfigFileParser.XMLAttribute _attr : _prop.getAttributeMap().values()) {
                if (StringUtils.isNotBlank(_attr.getValue())) {
                    _returnValue.put(_attr.getKey(), _attr.getValue());
                }
            }
        }
        return _returnValue;
    }

    public String[] getArray(String key) {
        List<String> _resultValue = getList(key);
        return _resultValue.toArray(new String[_resultValue.size()]);
    }

    public String[] getArray(String key, boolean zeroSize) {
        return getArray(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key, zeroSize);
    }

    public String[] getArray(String category, String key, boolean zeroSize) {
        List<String> _values = getList(category, key);
        if (_values.isEmpty() && !zeroSize) {
            return null;
        }
        return _values.toArray(new String[_values.size()]);
    }

    public int getInt(String key) {
        return getInt(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return getInt(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key, defaultValue);
    }

    public int getInt(String category, String key, int defaultValue) {
        XMLConfigFileParser.XMLCategory _category = __configFileParser.getCategory(category);
        if (_category != null) {
            XMLConfigFileParser.XMLProperty _prop = _category.getProperty(key);
            if (_prop != null) {
                return new BlurObject(_prop.getContent()).toIntValue();
            }
        }
        return defaultValue;
    }

    public boolean getBoolean(String key) {
        return getBoolean(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key, defaultValue);
    }

    public boolean getBoolean(String category, String key, boolean defaultValue) {
        XMLConfigFileParser.XMLCategory _category = __configFileParser.getCategory(category);
        if (_category != null) {
            XMLConfigFileParser.XMLProperty _prop = _category.getProperty(key);
            if (_prop != null) {
                return new BlurObject(_prop.getContent()).toBooleanValue();
            }
        }
        return defaultValue;
    }

    public long getLong(String key) {
        return getLong(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key, 0l);
    }

    public long getLong(String key, long defaultValue) {
        return getLong(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key, defaultValue);
    }

    public long getLong(String category, String key, long defaultValue) {
        XMLConfigFileParser.XMLCategory _category = __configFileParser.getCategory(category);
        if (_category != null) {
            XMLConfigFileParser.XMLProperty _prop = _category.getProperty(key);
            if (_prop != null) {
                return new BlurObject(_prop.getContent()).toLongValue();
            }
        }
        return defaultValue;
    }

    public float getFloat(String key) {
        return getFloat(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key, 0f);
    }

    public float getFloat(String key, float defaultValue) {
        return getFloat(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key, defaultValue);
    }

    public float getFloat(String category, String key, float defaultValue) {
        XMLConfigFileParser.XMLCategory _category = __configFileParser.getCategory(category);
        if (_category != null) {
            XMLConfigFileParser.XMLProperty _prop = _category.getProperty(key);
            if (_prop != null) {
                return new BlurObject(_prop.getContent()).toFloatValue();
            }
        }
        return defaultValue;
    }

    public double getDouble(String key) {
        return getDouble(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key, 0d);
    }

    public double getDouble(String key, double defaultValue) {
        return getDouble(XMLConfigFileParser.DEFAULT_CATEGORY_NAME, key, defaultValue);
    }

    public double getDouble(String category, String key, double defaultValue) {
        XMLConfigFileParser.XMLCategory _category = __configFileParser.getCategory(category);
        if (_category != null) {
            XMLConfigFileParser.XMLProperty _prop = _category.getProperty(key);
            if (_prop != null) {
                return new BlurObject(_prop.getContent()).toDoubleValue();
            }
        }
        return defaultValue;
    }

    public Map<String, String> toMap() {
        return toMap(XMLConfigFileParser.DEFAULT_CATEGORY_NAME);
    }

    public Map<String, String> toMap(String category) {
        XMLConfigFileParser.XMLCategory _category = __configFileParser.getCategory(category);
        if (_category == null) {
            return Collections.emptyMap();
        }
        Collection<XMLConfigFileParser.XMLProperty> _properties = _category.getPropertyMap().values();
        Map<String, String> _returnValue = new LinkedHashMap<String, String>(_properties.size());
        for (XMLConfigFileParser.XMLProperty _prop : _properties) {
            _returnValue.put(_prop.getName(), _prop.getContent());
            for (XMLConfigFileParser.XMLAttribute _attr : _prop.getAttributeMap().values()) {
                _returnValue.put(_prop.getName().concat(".").concat(_attr.getKey()), _attr.getValue());
            }
        }
        return _returnValue;
    }

    public List<String> getCategoryNames() {
        return new ArrayList<String>(__configFileParser.getCategories().keySet());
    }

    public boolean contains(String key) {
        return __configFileParser.getDefaultCategory().getProperty(key) != null;
    }

    public boolean contains(String category, String key) {
        XMLConfigFileParser.XMLCategory _category = __configFileParser.getCategory(category);
        return _category != null && _category.getProperty(key) != null;
    }
}
