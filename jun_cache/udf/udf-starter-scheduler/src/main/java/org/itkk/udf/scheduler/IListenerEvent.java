/**
 * ITriggerLog.java
 * Created at 2017-06-01
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler;

import org.itkk.udf.scheduler.domain.ScheduledTriggerLog;

/**
 * 描述 : ITriggerLog
 *
 * @author Administrator
 */
public interface IListenerEvent {

    /**
     * 描述 : 记录日志
     *
     * @param scheduledTriggerLog scheduledTriggerLog
     */
    void save(ScheduledTriggerLog scheduledTriggerLog);

    /**
     * 描述 : 删除记录
     *
     * @param fireInstanceId fireInstanceId
     */
    void delete(String fireInstanceId);

    /**
     * 描述 : 清理日志
     */
    void clearScheduledTriggerLog();

    /**
     * 描述 : 记录日志
     *
     * @param saveLog saveLog
     */
    void save(String saveLog);

    /**
     * 描述 : 清理日志
     */
    void clearScheduledLog();

}
