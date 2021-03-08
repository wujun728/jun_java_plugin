package com.andaily.service.scheduler.dynamic;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * 对JOb操作的核心类
 *
 * @author Wujun
 */
public final class DynamicSchedulerFactory implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(DynamicSchedulerFactory.class);
    private static Scheduler scheduler;

    public DynamicSchedulerFactory() {
    }


    /**
     * Register a job
     * 添加(注册)一个动态的JOB
     *
     * @param job DynamicJob
     * @return True is register successful
     * @throws SchedulerException
     */
    public static boolean registerJob(DynamicJob job) throws SchedulerException {
        final TriggerKey triggerKey = job.triggerKey();
        if (scheduler.checkExists(triggerKey)) {
            final Trigger trigger = scheduler.getTrigger(triggerKey);
            throw new SchedulerException("Already exist trigger [" + trigger + "] by key [" + triggerKey + "] in Scheduler");
        }

        final CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.cronExpression());
        final CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                .withSchedule(cronScheduleBuilder)
                .build();

        final JobDetail jobDetail = job.jobDetail();
        final Date date = scheduler.scheduleJob(jobDetail, cronTrigger);

        LOG.debug("Register DynamicJob {} on [{}]", job, date);
        return true;
    }

    /*
   * Pause exist job
   * 暂停已经存在的JOB
   * */
    public static boolean pauseJob(DynamicJob existJob) throws SchedulerException {
        final TriggerKey triggerKey = existJob.triggerKey();
        boolean result = false;
        if (scheduler.checkExists(triggerKey)) {
            scheduler.pauseTrigger(triggerKey);
            result = true;
            LOG.debug("Pause exist DynamicJob {}, triggerKey [{}] successful", existJob, triggerKey);
        } else {
            LOG.debug("Failed pause exist DynamicJob {}, because not fount triggerKey [{}]", existJob, triggerKey);
        }
        return result;
    }


    /*
    * Resume exist job
    * 重启一个JOB
    * */
    public static boolean resumeJob(DynamicJob existJob) throws SchedulerException {
        final TriggerKey triggerKey = existJob.triggerKey();
        boolean result = false;
        if (scheduler.checkExists(triggerKey)) {
            final CronTrigger newTrigger = existJob.cronTrigger();
            final Date date = scheduler.rescheduleJob(triggerKey, newTrigger);

            result = true;
            LOG.debug("Resume exist DynamicJob {}, triggerKey [{}] on [{}] successful", existJob, triggerKey, date);
        } else {
            LOG.debug("Failed resume exist DynamicJob {}, because not fount triggerKey [{}]", existJob, triggerKey);
        }
        return result;
    }


    /**
     * Checking the job is existed or not
     * 检查JOB是否存在
     *
     * @param job Job
     * @return True is existed, otherwise false
     * @throws SchedulerException
     */
    public static boolean existedJob(DynamicJob job) throws SchedulerException {
        final TriggerKey triggerKey = job.triggerKey();
        return scheduler.checkExists(triggerKey);
    }


    /**
     * Remove exists job
     * 删除一个已经存在的JOB
     *
     * @param existJob A  DynamicJob which exists in Scheduler
     * @return True is remove successful
     * @throws SchedulerException
     */
    public static boolean removeJob(DynamicJob existJob) throws SchedulerException {
        final TriggerKey triggerKey = existJob.triggerKey();
        boolean result = false;
        if (scheduler.checkExists(triggerKey)) {
            result = scheduler.unscheduleJob(triggerKey);
        }

        LOG.debug("Remove DynamicJob {} result [{}]", existJob, result);
        return result;
    }


    public void setScheduler(Scheduler scheduler) {
        DynamicSchedulerFactory.scheduler = scheduler;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(scheduler, "scheduler is null");
        LOG.info("Initial DynamicSchedulerFactory successful, scheduler instance: {}", scheduler);
    }


}