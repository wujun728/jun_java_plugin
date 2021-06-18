package com.andaily.service.impl;

import com.andaily.service.TestService;
import com.andaily.service.scheduler.TestDynamicJob;
import com.andaily.service.scheduler.dynamic.DynamicJob;
import com.andaily.service.scheduler.dynamic.DynamicSchedulerFactory;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Shengzhao Li
 */
@Service("testService")
public class TestServiceImpl implements TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);


    /*
    * 添加一个动态的JOB
    * */
    @Override
    public boolean addDynamicJob() {
        DynamicJob dynamicJob = createDynamicJob();
        dynamicJob.addJobData("mailGuid", UUID.randomUUID().toString());//transfer parameter

        try {
            DynamicSchedulerFactory.registerJob(dynamicJob);
        } catch (SchedulerException e) {
            throw new IllegalStateException(e);
        }

        return true;
    }


    /*
    * 删除一个JOB
    * */
    @Override
    public void removeJob() {
        final DynamicJob dynamicJob = createDynamicJob();
        try {
            final boolean result = DynamicSchedulerFactory.removeJob(dynamicJob);
            LOGGER.info("Remove DynamicJob [{}] result: {}", dynamicJob, result);
        } catch (SchedulerException e) {
            throw new IllegalStateException(e);
        }
    }

    //创建 一个动态的JOB, 测试用
    private DynamicJob createDynamicJob() {
        return new DynamicJob("test-")
                //动态定时任务的 cron,  每20秒执行一次
                .cronExpression("0/20 * * * * ?")
                .target(TestDynamicJob.class);
    }
}