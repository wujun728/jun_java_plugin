/**
 * AutoInitCommandLineRunner.java
 * Created at 2017-08-09
 * Created by Administrator
 * Copyright (C) 2016 egridcloud.com, All rights reserved.
 */
package org.itkk.udf.scheduler;

import org.apache.commons.collections4.MapUtils;
import org.itkk.udf.scheduler.meta.CronTriggerMeta;
import org.itkk.udf.scheduler.meta.JobDetailMeta;
import org.itkk.udf.scheduler.meta.SimpleTriggerMeta;
import org.itkk.udf.scheduler.service.JobService;
import org.itkk.udf.scheduler.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * 描述 : AutoInitCommandLineRunner
 *
 * @author Administrator
 */
@Component
public class AutoInitCommandLineRunner implements CommandLineRunner {

    /**
     * 描述 : schedulerProperties
     */
    @Autowired
    private SchedulerProperties schedulerProperties;

    /**
     * jobService
     */
    @Autowired
    private JobService jobService;

    /**
     * triggerService
     */
    @Autowired
    private TriggerService triggerService;

    @Override
    public void run(String... arg0) throws Exception { //NOSONAR
        //自动实例化jobDetail
        Map<String, JobDetailMeta> jobDetailMap = schedulerProperties.getJobDetail();
        if (MapUtils.isNotEmpty(jobDetailMap)) {
            Iterator<String> jobDetailKeys = jobDetailMap.keySet().iterator();
            while (jobDetailKeys.hasNext()) {
                String key = jobDetailKeys.next();
                JobDetailMeta jobDetailMeta = jobDetailMap.get(key);
                if (jobDetailMeta.getAutoInit()) {
                    jobService.save(key, true);
                }
            }
        }
        //自动实例化SimpleTrigger
        Map<String, SimpleTriggerMeta> simpleTriggerMetaMap =
                schedulerProperties.getSimpleTrigger();
        if (MapUtils.isNotEmpty(simpleTriggerMetaMap)) {
            Iterator<String> simpleTriggerMetaKeys = simpleTriggerMetaMap.keySet().iterator();
            while (simpleTriggerMetaKeys.hasNext()) {
                String key = simpleTriggerMetaKeys.next();
                SimpleTriggerMeta simpleTriggerMeta = simpleTriggerMetaMap.get(key);
                if (simpleTriggerMeta.getAutoInit()) {
                    triggerService.saveSimpleTirgger(key, true);
                }
            }
        }
        //自动实例化cronTrigger
        Map<String, CronTriggerMeta> sronTriggerMap = schedulerProperties.getCronTrigger();
        if (MapUtils.isNotEmpty(sronTriggerMap)) {
            Iterator<String> sronTriggerMapKeys = sronTriggerMap.keySet().iterator();
            while (sronTriggerMapKeys.hasNext()) {
                String key = sronTriggerMapKeys.next();
                CronTriggerMeta cronTriggerMeta = sronTriggerMap.get(key);
                if (cronTriggerMeta.getAutoInit()) {
                    triggerService.saveCronTrigger(key, true);
                }
            }
        }
    }

}
