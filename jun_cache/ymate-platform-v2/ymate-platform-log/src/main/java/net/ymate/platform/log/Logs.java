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
package net.ymate.platform.log;

import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.log.impl.DefaultLogger;
import net.ymate.platform.log.impl.DefaultModuleCfg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日志记录器模块管理器
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-27 下午03:56:24
 * @version 1.0
 */
@Module
public class Logs implements IModule, ILog {

    public static final Version VERSION = new Version(2, 0, 3, Logs.class.getPackage().getImplementationVersion(), Version.VersionType.Release);

    private static final Log _LOG = LogFactory.getLog(Logs.class);

    private static Map<String, ILogger> __LOGGER_CACHE = new ConcurrentHashMap<String, ILogger>();

    private static volatile ILog __instance;

    private YMP __owner;

    private ILogModuleCfg __moduleCfg;

    private boolean __inited;

    private ILogger __currentLogger;

    /**
     * @return 返回默认日志记录器模块管理器实例对象
     */
    public static ILog get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(Logs.class);
                }
            }
        }
        return __instance;
    }

    public String getName() {
        return ILog.MODULE_NAME;
    }

    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing ymate-platform-log-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new DefaultModuleCfg(__owner);
            // 设置全局变量，便于配置文件内引用
            System.getProperties().put("LOG_OUT_DIR", __moduleCfg.getOutputDir().getPath());
            //
            if (__moduleCfg.getLoggerClass() != null) {
                __currentLogger = ClassUtils.impl(__moduleCfg.getLoggerClass(), ILogger.class);
            }
            if (__currentLogger == null) {
                __currentLogger = new DefaultLogger();
            }
            __LOGGER_CACHE.put(__moduleCfg.getLoggerName(), __currentLogger);
            //
            __currentLogger.init(this, __moduleCfg.getLoggerName());
            __currentLogger.console(__moduleCfg.allowOutputConsole());
            //
            __inited = true;
            // 注册日志记录器事件
            __owner.getEvents().registerEvent(LogEvent.class);
        }
    }

    public boolean isInited() {
        return __inited;
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            for (ILogger _logger : __LOGGER_CACHE.values()) {
                _logger.destroy();
            }
            __currentLogger = null;
            __moduleCfg = null;
            __owner = null;
        }
    }

    public YMP getOwner() {
        return __owner;
    }

    public ILogModuleCfg getModuleCfg() {
        return __moduleCfg;
    }

    public ILogger getLogger() {
        return __currentLogger;
    }

    public ILogger getLogger(String loggerName) throws Exception {
        ILogger _logger = __LOGGER_CACHE.get(loggerName);
        if (_logger == null) {
            _logger = __currentLogger.getLogger(loggerName);
            if (_logger != null) {
                _logger.console(__moduleCfg.allowOutputConsole());
                __LOGGER_CACHE.put(loggerName, _logger);
            }
        }
        return _logger;
    }

    public ILogger getLogger(Class<?> clazz) throws Exception {
        return getLogger(clazz.getName());
    }
}
