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

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * @author 刘镇 (suninformation@163.com) on 16/6/12 上午1:27
 * @version 1.0
 */
public class LogInfo implements Serializable {

    private String logName;

    private String levelName;

    private long threadId;

    private String callerInfo;

    private String logContent;

    private String stackInfo;

    private String createTime;

    public LogInfo(String logName,
                   String levelName,
                   long threadId,
                   String callerInfo,
                   String logContent,
                   String stackInfo,
                   String createTime) {
        this.logName = logName;
        this.levelName = levelName;
        this.threadId = threadId;
        this.callerInfo = callerInfo;
        this.logContent = logContent;
        this.stackInfo = stackInfo;
        this.createTime = createTime;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String getCallerInfo() {
        return callerInfo;
    }

    public void setCallerInfo(String callerInfo) {
        this.callerInfo = callerInfo;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getStackInfo() {
        return stackInfo;
    }

    public void setStackInfo(String stackInfo) {
        this.stackInfo = stackInfo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder _exSB = new StringBuilder(createTime)
                .append(levelName)
                .append('[')
                .append(threadId)
                .append(':')
                .append(callerInfo)
                .append(']')
                .append(' ').append(StringUtils.trimToEmpty(logContent));
        if (StringUtils.isNotBlank(stackInfo)) {
            _exSB.append("- ").append(stackInfo);
        }
        //
        return _exSB.toString();
    }
}
