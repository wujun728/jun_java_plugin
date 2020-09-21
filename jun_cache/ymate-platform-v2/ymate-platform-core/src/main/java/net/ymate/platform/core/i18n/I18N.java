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
package net.ymate.platform.core.i18n;

import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 国际化资源管理器
 *
 * @author 刘镇 (suninformation@163.com) on 2013-4-14 下午1:36:11
 * @version 1.0
 */
public class I18N {

    private static final Log _LOG = LogFactory.getLog(I18N.class);

    private static Locale __DEFAULT_LOCALE;

    private static final ThreadLocal<Locale> __CURRENT_LOCALE = new ThreadLocal<Locale>();

    private static final Map<Locale, Map<String, Properties>> __RESOURCES_CAHCES = new ConcurrentHashMap<Locale, Map<String, Properties>>();

    private static II18NEventHandler __EVENT_HANDLER;

    private static boolean __IS_INITED;

    /**
     * 初始化
     *
     * @param defaultLocale 默认语言，若为空则采用JVM默认语言
     * @param eventHandler  设置事件监听处理器
     */
    public static void initialize(Locale defaultLocale, II18NEventHandler eventHandler) {
        if (!__IS_INITED) {
            __DEFAULT_LOCALE = defaultLocale == null ? Locale.getDefault() : defaultLocale;
            __EVENT_HANDLER = eventHandler;
            __IS_INITED = true;
        }
    }

    /**
     * 当前线程使用完毕切记一定要执行该方法进行清理
     */
    public static void cleanCurrent() {
        __CURRENT_LOCALE.remove();
    }

    /**
     * @return 判断是否已初始化
     */
    public static boolean isInited() {
        return __IS_INITED;
    }

    /**
     * @return 获取当前本地线程语言，若为空则返回默认
     */
    public static Locale current() {
        Locale _locale = __CURRENT_LOCALE.get();
        if (_locale == null) {
            if (__EVENT_HANDLER != null) {
                _locale = __EVENT_HANDLER.onLocale();
            }
            _locale = _locale == null ? __DEFAULT_LOCALE : _locale;
            __CURRENT_LOCALE.set(_locale);
        }
        return _locale;
    }

    /**
     * @param locale 预设置语言
     * @return 修改当前线程语言设置，不触发事件，并返回修改结果
     */
    public static boolean current(Locale locale) {
        if (locale != null && !current().equals(locale)) {
            __CURRENT_LOCALE.set(locale);
            return true;
        }
        return false;
    }

    /**
     * 修改当前线程语言设置并触发onLocaleChanged事件
     *
     * @param locale 预设置语言
     */
    public static void change(Locale locale) {
        if (current(locale)) {
            if (__EVENT_HANDLER != null) {
                __EVENT_HANDLER.onChanged(locale);
            }
        }
    }

    /**
     * @param resourceName 资源名称
     * @param key          键值
     * @return 加载资源并提取key指定的值
     */
    public static String load(String resourceName, String key) {
        return load(resourceName, key, "");
    }

    /**
     * @param resourceName 资源名称
     * @param key          键值
     * @param defaultValue 默认值
     * @return 加载资源并提取key指定的值
     */
    public static String load(String resourceName, String key, String defaultValue) {
        Locale _local = current();
        Map<String, Properties> _cache = __RESOURCES_CAHCES.get(_local);
        Properties _prop = _cache != null ? _cache.get(resourceName) : null;
        if (_prop == null) {
            if (__EVENT_HANDLER != null) {
                try {
                    List<String> _localeResourceNames = __doGetResourceNames(_local, resourceName);
                    InputStream _inputStream = null;
                    for (String _localeResourceName : _localeResourceNames) {
                        _inputStream = __EVENT_HANDLER.onLoad(_localeResourceName);
                        if (_inputStream != null) {
                            _prop = new Properties();
                            _prop.load(_inputStream);
                            break;
                        }
                    }
                    if (_prop != null && !_prop.isEmpty()) {
                        if (_cache == null) {
                            __RESOURCES_CAHCES.put(_local, new ConcurrentHashMap<String, Properties>());
                        }
                        __RESOURCES_CAHCES.get(_local).put(resourceName, _prop);
                    }
                } catch (IOException e) {
                    _LOG.warn("", RuntimeUtils.unwrapThrow(e));
                }
            }
        }
        String _returnValue = null;
        if (_prop != null) {
            _returnValue = _prop.getProperty(key, defaultValue);
        } else {
            try {
                _returnValue = ResourceBundle.getBundle(resourceName, _local).getString(key);
            } catch (Exception ignored) {
            }
        }
        return StringUtils.defaultIfEmpty(_returnValue, defaultValue);
    }

    /**
     * @param resourceName 资源名称
     * @param key          资源键名
     * @param defaultValue 默认值
     * @param args         参数集合
     * @return 格式化消息字符串与参数绑定
     */
    public static String formatMessage(String resourceName, String key, String defaultValue, Object... args) {
        String _msg = load(resourceName, key, defaultValue);
        if (args != null && args.length > 0) {
            return formatMsg(_msg, args);
        }
        return _msg;
    }

    public static String formatMsg(String message, Object... args) {
        if (args != null && args.length > 0) {
            return MessageFormat.format(message, args);
        }
        return message;
    }

    /**
     * @param locale       指定语言
     * @param resourceName 资源名称
     * @return 拼装资源文件名称集合
     */
    protected static List<String> __doGetResourceNames(Locale locale, String resourceName) {
        List<String> _names = new ArrayList<String>();
        _names.add(resourceName + ".properties");
        String _localeKey = (locale == null) ? "" : locale.toString();
        if (_localeKey.length() > 0) {
            resourceName += ("_" + _localeKey) + ".properties";
            _names.add(0, resourceName);
        }
        return _names;
    }

    /**
     * 销毁
     */
    public static void destroy() {
        if (__IS_INITED) {
            __IS_INITED = false;
            //
            __DEFAULT_LOCALE = null;
            __EVENT_HANDLER = null;
            __RESOURCES_CAHCES.clear();
        }
    }
}
