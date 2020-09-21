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

import net.ymate.platform.core.YMP;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.log.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;

import java.io.Serializable;

/**
 * @author 刘镇 (suninformation@163.com) on 15/9/28 21:16
 * @version 1.0
 */
public class JCLogger extends AbstractLogger implements Log, Serializable {

    private transient Log __simplog;

    private ILogger __logger;

    private String __loggerName;

    private boolean __inited;

    public JCLogger(String name) {
        __loggerName = name;
        //
        __tryCheckAndInitLogImpl();
    }

    private Log __tryGetLogSafely() {
        if (__simplog == null) {
            __simplog = new SimpleLog(__loggerName);
        }
        return __simplog;
    }

    private boolean __tryCheckAndInitLogImpl() {
        if (YMP.get() == null || !YMP.get().isInited() || Logs.get() == null || !Logs.get().isInited()) {
            return false;
        } else if (!__inited && YMP.get() != null && YMP.get().isInited() && Logs.get() != null && Logs.get().isInited()) {
            try {
                __logger = Logs.get().getLogger(__loggerName).depth(5);
                __inited = true;
            } catch (Exception e) {
                __tryGetLogSafely().warn("", RuntimeUtils.unwrapThrow(e));
            }
        }
        return __inited;
    }

    @Override
    protected void __doBuildEx(String info, Throwable e, LogLevel level) {
        if (__tryCheckAndInitLogImpl()) {
            __logger.log(info, e, level);
        } else {
            __tryGetLogSafely();
            switch (level) {
                case TRACE:
                    __simplog.trace(info, e);
                    break;
                case DEBUG:
                    __simplog.debug(info, e);
                    break;
                case ALL:
                case INFO:
                    __simplog.info(info, e);
                    break;
                case WARN:
                    __simplog.warn(info, e);
                    break;
                case ERROR:
                    __simplog.error(info, e);
                    break;
                case FATAL:
                    __simplog.fatal(info, e);
            }
        }
    }

    protected void __doLogWrite(LogLevel level, LogInfo content) {
        throw new UnsupportedOperationException();
    }

    public void debug(Object message) {
        if (isDebugEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), null, ILogger.LogLevel.DEBUG);
        }
    }

    public void debug(Object message, Throwable t) {
        if (isDebugEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), t, ILogger.LogLevel.DEBUG);
        }
    }

    public void error(Object message) {
        if (isErrorEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), null, ILogger.LogLevel.ERROR);
        }
    }

    public void error(Object message, Throwable t) {
        if (isErrorEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), t, ILogger.LogLevel.ERROR);
        }
    }

    public void fatal(Object message) {
        if (isFatalEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), null, ILogger.LogLevel.FATAL);
        }
    }

    public void fatal(Object message, Throwable t) {
        if (isFatalEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), t, ILogger.LogLevel.FATAL);
        }
    }

    public void info(Object message) {
        if (isInfoEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), null, ILogger.LogLevel.INFO);
        }
    }

    public void info(Object message, Throwable t) {
        if (isInfoEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), t, ILogger.LogLevel.INFO);
        }
    }

    public ILogger init(ILog owner, String loggerName) throws Exception {
        throw new UnsupportedOperationException();
    }

    public ILogger getLogger(String loggerName) throws Exception {
        throw new UnsupportedOperationException();
    }

    public Object getLoggerImpl() {
        throw new UnsupportedOperationException();
    }

    public String getLoggerName() {
        throw new UnsupportedOperationException();
    }

    public void destroy() {
    }

    public boolean contains(String loggerName) {
        throw new UnsupportedOperationException();
    }

    public LogLevel getLevel() {
        throw new UnsupportedOperationException();
    }

    public void log(String info, LogLevel level) {
        throw new UnsupportedOperationException();
    }

    public void log(Throwable e, LogLevel level) {
        throw new UnsupportedOperationException();
    }

    public void log(String info, Throwable e, LogLevel level) {
        throw new UnsupportedOperationException();
    }

    public void trace(String info) {
        throw new UnsupportedOperationException();
    }

    public void trace(Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void trace(String info, Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void debug(String info) {
        throw new UnsupportedOperationException();
    }

    public void debug(Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void debug(String info, Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void info(String info) {
        throw new UnsupportedOperationException();
    }

    public void info(Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void info(String info, Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void warn(String info) {
        throw new UnsupportedOperationException();
    }

    public void warn(Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void warn(String info, Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void error(String info) {
        throw new UnsupportedOperationException();
    }

    public void error(Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void error(String info, Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void fatal(String info) {
        throw new UnsupportedOperationException();
    }

    public void fatal(Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void fatal(String info, Throwable e) {
        throw new UnsupportedOperationException();
    }

    public boolean isDebugEnabled() {
        if (__tryCheckAndInitLogImpl()) {
            return __logger.isDebugEnabled();
        }
        return __tryGetLogSafely().isDebugEnabled();
    }

    public boolean isErrorEnabled() {
        if (__tryCheckAndInitLogImpl()) {
            return __logger.isErrorEnabled();
        }
        return __tryGetLogSafely().isErrorEnabled();
    }

    public boolean isFatalEnabled() {
        if (__tryCheckAndInitLogImpl()) {
            return __logger.isFatalEnabled();
        }
        return __tryGetLogSafely().isFatalEnabled();
    }

    public boolean isInfoEnabled() {
        if (__tryCheckAndInitLogImpl()) {
            return __logger.isInfoEnabled();
        }
        return __tryGetLogSafely().isInfoEnabled();
    }

    public boolean isTraceEnabled() {
        if (__tryCheckAndInitLogImpl()) {
            return __logger.isTraceEnabled();
        }
        return __tryGetLogSafely().isTraceEnabled();
    }

    public boolean isWarnEnabled() {
        if (__tryCheckAndInitLogImpl()) {
            return __logger.isWarnEnabled();
        }
        return __tryGetLogSafely().isWarnEnabled();
    }

    public void trace(Object message) {
        if (isTraceEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), null, ILogger.LogLevel.TRACE);
        }
    }

    public void trace(Object message, Throwable t) {
        if (isTraceEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), t, ILogger.LogLevel.TRACE);
        }
    }

    public void warn(Object message) {
        if (isWarnEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), null, ILogger.LogLevel.WARN);
        }
    }

    public void warn(Object message, Throwable t) {
        if (isWarnEnabled()) {
            __doBuildEx(message == null ? null : message.toString(), t, ILogger.LogLevel.WARN);
        }
    }
}
