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

import net.ymate.platform.core.YMP;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.log.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;
import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

import java.io.Serializable;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/4 上午11:30
 * @version 1.0
 */
public class LogLogger extends AbstractLogger implements LocationAwareLogger, Serializable {

    private transient Log __simplog;

    private ILogger __logger;

    private String __loggerName;

    private boolean __inited;

    public LogLogger(String name) {
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
                __tryGetLogSafely();
                __simplog.warn("", RuntimeUtils.unwrapThrow(e));
            }
        }
        return __inited;
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
        return __loggerName;
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

    public void trace(final String msg) {
        if (isTraceEnabled()) {
            __doBuildEx(msg, null, LogLevel.TRACE);
        }
    }

    public void trace(Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void trace(final String format, final Object arg) {
        if (isTraceEnabled()) {
            __doBuildEx(__getSafeMessages(format, arg), null, LogLevel.TRACE);
        }
    }

    public void trace(final String format, final Object arg1, final Object arg2) {
        if (isTraceEnabled()) {
            __doBuildEx(__getSafeMessages(format, arg1, arg2), null, LogLevel.TRACE);
        }
    }

    public void trace(final String format, final Object... args) {
        if (isTraceEnabled()) {
            __doBuildEx(__getSafeMessages(format, args), null, LogLevel.TRACE);
        }
    }

    public void trace(final String msg, final Throwable t) {
        if (isTraceEnabled()) {
            __doBuildEx(msg, t, LogLevel.TRACE);
        }
    }

    public boolean isTraceEnabled() {
        if (__tryCheckAndInitLogImpl()) {
            return __logger.isTraceEnabled();
        }
        return __tryGetLogSafely().isTraceEnabled();
    }

    public boolean isTraceEnabled(final Marker marker) {
        return isTraceEnabled();
    }

    public void trace(final Marker marker, final String msg) {
        if (isTraceEnabled(marker)) {
            __doBuildEx(msg, null, LogLevel.TRACE);
        }
    }

    public void trace(final Marker marker, final String format, final Object arg) {
        if (isTraceEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, arg), null, LogLevel.TRACE);
        }
    }

    public void trace(final Marker marker, final String format, final Object arg1, final Object arg2) {
        if (isTraceEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, arg1, arg2), null, LogLevel.TRACE);
        }
    }

    public void trace(final Marker marker, final String format, final Object... args) {
        if (isTraceEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, args), null, LogLevel.TRACE);
        }
    }

    public void trace(final Marker marker, final String msg, final Throwable throwable) {
        if (isTraceEnabled(marker)) {
            __doBuildEx(msg, throwable, LogLevel.TRACE);
        }
    }

    public void debug(final String msg) {
        if (isDebugEnabled()) {
            __doBuildEx(msg, null, LogLevel.DEBUG);
        }
    }

    public void debug(Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void debug(final String format, final Object arg) {
        if (isDebugEnabled()) {
            __doBuildEx(__getSafeMessages(format, arg), null, LogLevel.DEBUG);
        }
    }

    public void debug(final String format, final Object arg1, final Object arg2) {
        if (isDebugEnabled()) {
            __doBuildEx(__getSafeMessages(format, arg1, arg2), null, LogLevel.DEBUG);
        }
    }

    public void debug(final String format, final Object... args) {
        if (isDebugEnabled()) {
            __doBuildEx(__getSafeMessages(format, args), null, LogLevel.DEBUG);
        }
    }

    public void debug(final String msg, final Throwable t) {
        if (isDebugEnabled()) {
            __doBuildEx(msg, t, LogLevel.DEBUG);
        }
    }

    public boolean isDebugEnabled() {
        if (__tryCheckAndInitLogImpl()) {
            return __logger.isDebugEnabled();
        }
        return __tryGetLogSafely().isDebugEnabled();
    }

    public boolean isDebugEnabled(final Marker marker) {
        return isDebugEnabled();
    }

    public void debug(final Marker marker, final String msg) {
        if (isDebugEnabled(marker)) {
            __doBuildEx(msg, null, LogLevel.DEBUG);
        }
    }

    public void debug(final Marker marker, final String format, final Object arg) {
        if (isDebugEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, arg), null, LogLevel.DEBUG);
        }
    }

    public void debug(final Marker marker, final String format, final Object arg1, final Object arg2) {
        if (isDebugEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, arg1, arg2), null, LogLevel.DEBUG);
        }
    }

    public void debug(final Marker marker, final String format, final Object... args) {
        if (isDebugEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, args), null, LogLevel.DEBUG);
        }
    }

    public void debug(final Marker marker, final String msg, final Throwable throwable) {
        if (isDebugEnabled(marker)) {
            __doBuildEx(msg, throwable, LogLevel.DEBUG);
        }
    }

    public void info(final String msg) {
        if (isInfoEnabled()) {
            __doBuildEx(msg, null, LogLevel.INFO);
        }
    }

    public void info(Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void info(final String format, final Object arg) {
        if (isInfoEnabled()) {
            __doBuildEx(__getSafeMessages(format, arg), null, LogLevel.INFO);
        }
    }

    public void info(final String format, final Object arg1, final Object arg2) {
        if (isInfoEnabled()) {
            __doBuildEx(__getSafeMessages(format, arg1, arg2), null, LogLevel.INFO);
        }
    }

    public void info(final String format, final Object... args) {
        if (isInfoEnabled()) {
            __doBuildEx(__getSafeMessages(format, args), null, LogLevel.INFO);
        }
    }

    public void info(final String msg, final Throwable t) {
        if (isInfoEnabled()) {
            __doBuildEx(msg, t, LogLevel.INFO);
        }
    }

    public boolean isInfoEnabled() {
        if (__tryCheckAndInitLogImpl()) {
            return __logger.isInfoEnabled();
        }
        return __tryGetLogSafely().isInfoEnabled();
    }

    public boolean isInfoEnabled(final Marker marker) {
        return isInfoEnabled();
    }

    public void info(final Marker marker, final String msg) {
        if (isInfoEnabled(marker)) {
            __doBuildEx(msg, null, LogLevel.INFO);
        }
    }

    public void info(final Marker marker, final String format, final Object arg) {
        if (isInfoEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, arg), null, LogLevel.INFO);
        }
    }

    public void info(final Marker marker, final String format, final Object arg1, final Object arg2) {
        if (isInfoEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, arg1, arg2), null, LogLevel.INFO);
        }
    }

    public void info(final Marker marker, final String format, final Object... args) {
        if (isInfoEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, args), null, LogLevel.INFO);
        }
    }

    public void info(final Marker marker, final String msg, final Throwable throwable) {
        if (isInfoEnabled(marker)) {
            __doBuildEx(msg, throwable, LogLevel.INFO);
        }
    }

    public void warn(final String msg) {
        if (isWarnEnabled()) {
            __doBuildEx(msg, null, LogLevel.WARN);
        }
    }

    public void warn(Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void warn(final String format, final Object arg) {
        if (isWarnEnabled()) {
            __doBuildEx(__getSafeMessages(format, arg), null, LogLevel.WARN);
        }
    }

    public void warn(final String format, final Object arg1, final Object arg2) {
        if (isWarnEnabled()) {
            __doBuildEx(__getSafeMessages(format, arg1, arg2), null, LogLevel.WARN);
        }
    }

    public void warn(final String format, final Object... args) {
        if (isWarnEnabled()) {
            __doBuildEx(__getSafeMessages(format, args), null, LogLevel.WARN);
        }
    }

    public void warn(final String msg, final Throwable t) {
        if (isWarnEnabled()) {
            __doBuildEx(msg, t, LogLevel.WARN);
        }
    }

    public boolean isWarnEnabled() {
        if (__tryCheckAndInitLogImpl()) {
            return __logger.isWarnEnabled();
        }
        return __tryGetLogSafely().isWarnEnabled();
    }

    public boolean isWarnEnabled(final Marker marker) {
        return isWarnEnabled();
    }

    public void warn(final Marker marker, final String msg) {
        if (isWarnEnabled(marker)) {
            __doBuildEx(msg, null, LogLevel.WARN);
        }
    }

    public void warn(final Marker marker, final String format, final Object arg) {
        if (isWarnEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, arg), null, LogLevel.WARN);
        }
    }

    public void warn(final Marker marker, final String format, final Object arg1, final Object arg2) {
        if (isWarnEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, arg1, arg2), null, LogLevel.WARN);
        }
    }

    public void warn(final Marker marker, final String format, final Object... args) {
        if (isWarnEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, args), null, LogLevel.WARN);
        }
    }

    public void warn(final Marker marker, final String msg, final Throwable throwable) {
        if (isWarnEnabled(marker)) {
            __doBuildEx(msg, throwable, LogLevel.WARN);
        }
    }

    public void error(final String msg) {
        if (isErrorEnabled()) {
            __doBuildEx(msg, null, LogLevel.ERROR);
        }
    }

    public void error(Throwable e) {
        throw new UnsupportedOperationException();
    }

    public void error(final String format, final Object arg) {
        if (isErrorEnabled()) {
            __doBuildEx(__getSafeMessages(format, arg), null, LogLevel.ERROR);
        }
    }

    public void error(final String format, final Object arg1, final Object arg2) {
        if (isErrorEnabled()) {
            __doBuildEx(__getSafeMessages(format, arg1, arg2), null, LogLevel.ERROR);
        }
    }

    public void error(final String format, final Object... args) {
        if (isErrorEnabled()) {
            __doBuildEx(__getSafeMessages(format, args), null, LogLevel.ERROR);
        }
    }

    public void error(final String msg, final Throwable t) {
        if (isErrorEnabled()) {
            __doBuildEx(msg, t, LogLevel.ERROR);
        }
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

    public boolean isErrorEnabled() {
        if (__tryCheckAndInitLogImpl()) {
            return __logger.isErrorEnabled();
        }
        return __tryGetLogSafely().isErrorEnabled();
    }

    public boolean isFatalEnabled() {
        throw new UnsupportedOperationException();
    }

    public boolean isErrorEnabled(final Marker marker) {
        return isErrorEnabled();
    }

    public void error(final Marker marker, final String msg) {
        if (isErrorEnabled(marker)) {
            __doBuildEx(msg, null, LogLevel.ERROR);
        }
    }

    public void error(final Marker marker, final String format, final Object arg) {
        if (isErrorEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, arg), null, LogLevel.ERROR);
        }
    }

    public void error(final Marker marker, final String format, final Object arg1, final Object arg2) {
        if (isErrorEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, arg1, arg2), null, LogLevel.ERROR);
        }
    }

    public void error(final Marker marker, final String format, final Object... args) {
        if (isErrorEnabled(marker)) {
            __doBuildEx(__getSafeMessages(format, args), null, LogLevel.ERROR);
        }
    }

    public void error(final Marker marker, final String msg, final Throwable throwable) {
        if (isErrorEnabled(marker)) {
            __doBuildEx(msg, throwable, LogLevel.ERROR);
        }
    }

    public void log(final Marker marker, final String fqcn, final int level, final String message, final Object[] params, Throwable throwable) {
        LogLevel _level = null;
        switch (level) {
            case TRACE_INT:
                _level = LogLevel.TRACE;
                break;
            case DEBUG_INT:
                _level = LogLevel.DEBUG;
                break;
            case INFO_INT:
                _level = LogLevel.INFO;
                break;
            case WARN_INT:
                _level = LogLevel.WARN;
                break;
            case ERROR_INT:
                _level = LogLevel.ERROR;
        }
        __doBuildEx(__getSafeMessages(message, params), throwable, _level);
    }

    public String getName() {
        return __loggerName;
    }

    private static String __getSafeMessages(String msg, Object... args) {
        if (StringUtils.isNotBlank(msg)) {
            return ParameterizedMessageFactory.INSTANCE.newMessage(msg, args).getFormattedMessage();
        }
        return msg;
    }

    @Override
    protected void __doBuildEx(String info, Throwable e, LogLevel level) {
        if (__tryCheckAndInitLogImpl()) {
            __logger.log(info, e, level);
        } else {
            switch (level) {
                case TRACE:
                    __tryGetLogSafely().trace(info, e);
                    break;
                case DEBUG:
                    __tryGetLogSafely().debug(info, e);
                    break;
                case ALL:
                case INFO:
                    __tryGetLogSafely().info(info, e);
                    break;
                case WARN:
                    __tryGetLogSafely().warn(info, e);
                    break;
                case ERROR:
                    __tryGetLogSafely().error(info, e);
                    break;
                case FATAL:
                    __tryGetLogSafely().fatal(info, e);
            }
        }
    }

    protected void __doLogWrite(LogLevel level, LogInfo content) {
        throw new UnsupportedOperationException();
    }
}
