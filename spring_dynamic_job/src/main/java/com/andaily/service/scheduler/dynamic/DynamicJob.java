package com.andaily.service.scheduler.dynamic;

import org.quartz.*;

/**
 * 定义一 个动态的 JOB
 *
 * @author Shengzhao Li
 */
public class DynamicJob {


    //Job class
    private Class<? extends Job> target;

    //cron expression
    private String cronExpression;

    private String jobGroup = Scheduler.DEFAULT_GROUP;

    //Must unique
    private String jobName;


    private transient TriggerKey triggerKey;
    private transient JobDetail jobDetail;


    //default
    public DynamicJob() {
    }

    public DynamicJob(String jobName) {
        this.jobName = jobName;
    }

    public Class<? extends Job> target() {
        return target;
    }

    public DynamicJob target(Class<? extends Job> target) {
        this.target = target;
        return this;
    }


    public DynamicJob cronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    public String jobGroup() {
        return jobGroup;
    }

    public DynamicJob jobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
        return this;
    }

    public String jobName() {
        return jobName;
    }

    public DynamicJob jobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public TriggerKey triggerKey() {
        if (triggerKey == null) {
            triggerKey = TriggerKey.triggerKey(this.jobName, this.jobGroup);
        }
        return triggerKey;
    }

    public JobDetail jobDetail() {
        if (jobDetail == null) {
            jobDetail = JobBuilder.newJob(target)
                    .withIdentity(this.jobName, this.jobGroup)
                    .build();
        }
        return jobDetail;
    }


    /*
   * Transfer data to  job
   * In job use
   *  context.getMergedJobDataMap().get(key)
   * */
    public DynamicJob addJobData(String key, Object value) {
        final JobDetail detail = jobDetail();
        final JobDataMap jobDataMap = detail.getJobDataMap();
        jobDataMap.put(key, value);
        return this;
    }


    public String cronExpression() {
        return this.cronExpression;
    }


    public CronTrigger cronTrigger() {
        final CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(this.cronExpression);
        return TriggerBuilder.newTrigger().withIdentity(triggerKey())
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    @Override
    public String toString() {
        return "{" +
                "cronExpression='" + cronExpression + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobName='" + jobName + '\'' +
                '}';
    }
}