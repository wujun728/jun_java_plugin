/**
 * ClearScheduledLog.java
 * Created at 2017-06-01
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.job;

import org.itkk.udf.scheduler.IListenerEvent;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * <p>
 * ClassName: ClearScheduledLog
 * </p>
 * <p>
 * Description: 清理计划任务日志
 * </p>
 * <p>
 * Author: wangkang
 * </p>
 * <p>
 * Date: 2014年3月7日
 * </p>
 */
@DisallowConcurrentExecution
public class ClearScheduledLog extends AbstractBaseJob {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        IListenerEvent listenerLog = this.getApplicationContext().getBean(IListenerEvent.class);
        if (listenerLog != null) {
            listenerLog.clearScheduledLog();
        }
    }

}
