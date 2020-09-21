/**
 * JobController.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.web;

import org.apache.commons.collections4.MapUtils;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.scheduler.client.TriggerDataMapKey;
import org.itkk.udf.scheduler.client.TriggerType;
import org.itkk.udf.scheduler.service.JobService;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

/**
 * 描述 : JobController
 *
 * @author Administrator
 */
@RestController
public class JobController implements IJobController {

    /**
     * 描述 : jobService
     */
    @Autowired
    private JobService jobService;

    @Override
    public RestResponse<String> save(@PathVariable String jobCode) throws ClassNotFoundException, SchedulerException {
        jobService.save(jobCode, false);
        return new RestResponse<>();
    }

    @Override
    public RestResponse<String> saveCover(@PathVariable String jobCode) throws ClassNotFoundException, SchedulerException {
        jobService.save(jobCode, true);
        return new RestResponse<>();
    }

    @Override
    public RestResponse<String> remove(@PathVariable String jobCode) throws SchedulerException {
        jobService.remove(jobCode);
        return new RestResponse<>();
    }

    @Override
    public RestResponse<String> trigger(@PathVariable String jobCode, @RequestBody Map<String, String> jobDataMap) throws SchedulerException {
        //生成id
        String triggerId = UUID.randomUUID().toString();
        //生成jobDataMap
        JobDataMap jdm;
        //判断是否有jobDataMap
        if (MapUtils.isEmpty(jobDataMap)) {
            jdm = new JobDataMap();
        } else {
            jdm = new JobDataMap(jobDataMap);
        }
        //添加参数
        jdm.put(TriggerDataMapKey.TRIGGER_ID.value(), triggerId);
        if (!jobDataMap.containsKey(TriggerDataMapKey.TRIGGER_TYPE.value())) {
            jdm.put(TriggerDataMapKey.TRIGGER_TYPE.value(), TriggerType.MANUAL.value());
        }
        //触发
        jobService.trigger(jobCode, jdm);
        //返回
        return new RestResponse<>(triggerId);
    }

}
