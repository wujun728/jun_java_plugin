package com.jun.plugin.spring.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import com.jun.plugin.spring.job.job.HelloJob;

public class TestCron {
    public static void main(String[] args) throws SchedulerException {
        final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        final JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("jobCron", "group1").build();
        final CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger", "triggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 13 15 ? * *")).build();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
        /**
         * 测试上传dev分支
         */
    }
}
