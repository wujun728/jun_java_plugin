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

import net.ymate.platform.core.event.EventContext;
import net.ymate.platform.core.event.IEvent;

/**
 * 日志记录器事件
 *
 * @author 刘镇 (suninformation@163.com) on 15/7/21 下午1:13
 * @version 1.0
 */
public class LogEvent extends EventContext<ILogger, LogEvent.EVENT> implements IEvent {

    public static final String LOG_LEVEL = "__LogLevel__";

    public static final String LOG_INFO = "__LogInfo__";

    public enum EVENT {
        LOG_WRITE_IN
    }

    public LogEvent(ILogger owner, EVENT eventName) {
        super(owner, LogEvent.class, eventName);
    }

    public ILogger.LogLevel getLogLevel() {
        return (ILogger.LogLevel) getParamExtend(LOG_LEVEL);
    }

    public LogInfo getLogInfo() {
        return (LogInfo) getParamExtend(LOG_INFO);
    }

}
