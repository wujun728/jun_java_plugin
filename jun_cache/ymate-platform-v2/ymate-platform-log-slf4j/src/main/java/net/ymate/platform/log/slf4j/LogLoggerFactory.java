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
package net.ymate.platform.log.slf4j;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/4 上午11:28
 * @version 1.0
 */
public class LogLoggerFactory implements ILoggerFactory {

    private static Map<String, Logger> __LOGGER_CACHE = new ConcurrentHashMap<String, Logger>();

    public Logger getLogger(String name) {
        Logger _logger = __LOGGER_CACHE.get(name);
        if (_logger == null) {
            _logger = new LogLogger(name);
            __LOGGER_CACHE.put(name, _logger);
        }
        return _logger;
    }
}
