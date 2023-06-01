package com.jun.plugin.spring.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import com.jun.plugin.spring.job.job.HelloJob;

public class TestJob {
    public static void main(String[] args) throws SchedulerException {
        // 调度器(Scheduler),从工厂中获取实例
        final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //任务实例(JobDetail)
        final JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .usingJobData("message","打印日志")
                .build();
//        System.out.println(jobDetail.getKey().getName());
        //触发器(Trigger)
        final Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "triggerGroup1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .repeatForever().withIntervalInSeconds(5))
                .usingJobData("message","触发器")
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
//        scheduler.getListenerManager().addJobListener();
        scheduler.start();
    }
}
