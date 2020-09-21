/**
 * JobController.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.web;

import org.itkk.udf.cache.redis.lock.RedisBasedDistributedLock;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.scheduler.IRmsJobEvent;
import org.itkk.udf.scheduler.client.domain.RmsJobResult;
import org.itkk.udf.scheduler.job.AbstractBaseRmsJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 : JobController
 *
 * @author Administrator
 */
@RestController
public class SchClientCallbackController implements ISchClientCallbackController {

    /**
     * 描述 : rmsJobLog
     */
    @Autowired(required = false)
    private IRmsJobEvent rmsJobLog;

    /**
     * redisBasedDistributedLock
     */
    @Autowired
    private RedisBasedDistributedLock redisBasedDistributedLock;

    @Override
    public RestResponse<String> callback(@RequestBody RmsJobResult result) {
        try {
            if (rmsJobLog != null) {
                rmsJobLog.save(null, result);
            }
            return new RestResponse<>();
        } finally {
            //获得key
            String name = AbstractBaseRmsJob.RMS_JOB_DISALLOW_CONCURRENT_PREFIX + result.getParam().getServiceCode() + "_" + result.getParam().getBeanName();
            //解锁
            redisBasedDistributedLock.unlock(name);
        }
    }

}
