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
package net.ymate.platform.log.jcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 刘镇 (suninformation@163.com) on 15/9/28 21:59
 * @version 1.0
 */
public class JCLogFactory extends LogFactory {

    private static Map<String, Log> __LOGGER_CACHE = new ConcurrentHashMap<String, Log>();

    private final ConcurrentMap<String, Object> attributes = new ConcurrentHashMap<String, Object>();

    public Log getInstance(final String name) throws LogConfigurationException {
        Log _logger = __LOGGER_CACHE.get(name);
        if (_logger == null) {
            _logger = new JCLogger(name);
            __LOGGER_CACHE.put(name, _logger);
        }
        return _logger;
    }

    public Object getAttribute(final String name) {
        return attributes.get(name);
    }

    public String[] getAttributeNames() {
        return attributes.keySet().toArray(new String[attributes.size()]);
    }

    public Log getInstance(@SuppressWarnings("rawtypes") final Class clazz) throws LogConfigurationException {
        return getInstance(clazz.getName());
    }

    public void release() {
        __LOGGER_CACHE.clear();
    }

    public void removeAttribute(final String name) {
        attributes.remove(name);
    }

    public void setAttribute(final String name, final Object value) {
        if (value != null) {
            attributes.put(name, value);
        } else {
            removeAttribute(name);
        }
    }
}
