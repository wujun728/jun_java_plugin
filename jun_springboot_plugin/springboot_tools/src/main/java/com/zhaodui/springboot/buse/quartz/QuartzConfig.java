package com.zhaodui.springboot.buse.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.JobBuilder.newJob;

@Configuration
public class QuartzConfig {


    /**
     * 创建jobDetail
     * @return
     */
    @Bean
    public JobDetail getJobDetail(){
        //由job类生产jobDetail
        JobDetail jobDetail = newJob(QuartzJob.class) //产生Job实例，job信息存储在jobDetail中
                .usingJobData("key1","val1")//通过jobDataMap为job实例增加属性，然后再传递到调度器中去
                .usingJobData("key2","val2")
                .withIdentity("h1","t1")
                .requestRecovery(true)
                .storeDurably()
                .build();//由jobBuilder生成jobDetail
        return jobDetail;
    }

    /**
     * 获取cronTrigger和设置调度器
     * @return
     */
    @Bean
    public CronTrigger getCronTrigger(){
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                .cronSchedule(" 0 11 11 11 11 ? ");

        return TriggerBuilder.newTrigger().forJob(getJobDetail())
                .withIdentity("testQuartz")
                .withSchedule(cronScheduleBuilder)
                .build();
    }

}

