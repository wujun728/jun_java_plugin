/**
 * SchedulerProperties.java
 * Created at 2017-06-01
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler;

import lombok.Data;
import org.itkk.udf.scheduler.meta.CronTriggerMeta;
import org.itkk.udf.scheduler.meta.JobDetailMeta;
import org.itkk.udf.scheduler.meta.SimpleTriggerMeta;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 描述 : SchedulerProperties
 *
 * @author Administrator
 */
@Component
@ConfigurationProperties(prefix = "org.itkk.scheduler.properties")
@Data
public class SchedulerProperties {

    /**
     * 描述 : 作业组别 ( key : 组别代码 , value : 组别描述 )
     */
    private Map<String, String> jobGroup;

    /**
     * 描述 : 触发器组别 ( key : 组别代码 , value : 组别描述 )
     */
    private Map<String, String> triggerGroup;

    /**
     * 描述 : 作业 ( kety : 作业代码[jobCode] )
     */
    private Map<String, JobDetailMeta> jobDetail;

    /**
     * 描述 : 简单触发器 ( key: 触发器代码[simpleTriggerCode] )
     */
    private Map<String, SimpleTriggerMeta> simpleTrigger;

    /**
     * 描述 : cron触发器 (key : 触发器代码[cronTriggerCode])
     */
    private Map<String, CronTriggerMeta> cronTrigger;

}
