package com.xbd.quartz.handler;

import com.xbd.quartz.DefaultQuartzJobBean;
import com.xbd.quartz.QuartzTask;
import com.xbd.quartz.QuartzTaskHandler;
import com.xbd.quartz.task.AbstractQuartzTask;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;

public class DefaultQuartzTaskHandler extends QuartzTaskHandler {

    public DefaultQuartzTaskHandler() {
    }

    /**
     * 动态添加任务
     *
     * @see QuartzTask
     *
     * @param quartzTask 定时任务信息
     * @throws SchedulerException
     */
    public void addTask(QuartzTask quartzTask) throws SchedulerException {
        JobDetail jobDetail = JobBuilder
                .newJob(DefaultQuartzJobBean.class)
                .withIdentity(quartzTask.getName(), quartzTask.getGroup())
                .withDescription(quartzTask.getDescription())
                .storeDurably()
                .build();

        CronScheduleBuilder cronScheduleBuilder = initCronScheduleBuilder(quartzTask);

        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey(quartzTask.getName(), quartzTask.getGroup()))
                .withSchedule(cronScheduleBuilder)
                .usingJobData(DefaultQuartzJobBean.JOBDETAIL_KEY_TARGETCLASS, quartzTask.getTargetClass())
                .usingJobData(DefaultQuartzJobBean.JOBDETAIL_KEY_TARGETOBJECT, quartzTask.getTargetObject())
                .usingJobData(DefaultQuartzJobBean.JOBDETAIL_KEY_TARGETMETHOD, StringUtils.isBlank(quartzTask.getTargetMethod()) ? AbstractQuartzTask.TARGETMETHOD_DEFAULT : quartzTask.getTargetMethod())
                .usingJobData(DefaultQuartzJobBean.JOBDETAIL_KEY_TARGETMETHOD_PARAM, quartzTask.getTargetMethodParam());

        if (quartzTask.getStartAt() != null) {
            triggerBuilder.startAt(quartzTask.getStartAt());
        }

        if (quartzTask.isStartNow()) {
            triggerBuilder.startNow();
        }

        CronTrigger cronTrigger = triggerBuilder.build();

        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     * 批量动态添加任务
     *
     * @see QuartzTask
     *
     * @param quartzTasks 定时任务信息集合
     * @throws SchedulerException
     */
    public void addTask(QuartzTask... quartzTasks) throws SchedulerException {
        if (quartzTasks != null) {
            for (QuartzTask quartzTask : quartzTasks) {
                addTask(quartzTask);
            }
        }
    }

    /**
     * 动态更新任务
     *
     * @see QuartzTask
     *
     * @param quartzTask 定时任务信息
     * @throws SchedulerException
     */
    public void updateTask(QuartzTask quartzTask) throws SchedulerException {
        CronTrigger cronTrigger = getTrigger(quartzTask.getName(), quartzTask.getGroup());

        CronScheduleBuilder cronScheduleBuilder = initCronScheduleBuilder(quartzTask);

        TriggerBuilder<CronTrigger> triggerBuilder = cronTrigger
                .getTriggerBuilder()
                .withIdentity(TriggerKey.triggerKey(quartzTask.getName(), quartzTask.getGroup()))
                .withSchedule(cronScheduleBuilder)
                .usingJobData(DefaultQuartzJobBean.JOBDETAIL_KEY_TARGETCLASS, quartzTask.getTargetClass())
                .usingJobData(DefaultQuartzJobBean.JOBDETAIL_KEY_TARGETOBJECT, quartzTask.getTargetObject())
                .usingJobData(DefaultQuartzJobBean.JOBDETAIL_KEY_TARGETMETHOD, StringUtils.isBlank(quartzTask.getTargetMethod()) ? DefaultQuartzJobBean.JOBDETAIL_VALUE_TARGETMETHOD : quartzTask.getTargetMethod())
                .usingJobData(DefaultQuartzJobBean.JOBDETAIL_KEY_TARGETMETHOD_PARAM, quartzTask.getTargetMethodParam());

        if (quartzTask.getStartAt() != null) {
            triggerBuilder.startAt(quartzTask.getStartAt());
        }

        if (quartzTask.isStartNow()) {
            triggerBuilder.startNow();
        }

        cronTrigger = triggerBuilder.build();

        scheduler.rescheduleJob(TriggerKey.triggerKey(quartzTask.getName(), quartzTask.getGroup()), cronTrigger);
    }

    /**
     * 批量动态更新任务
     *
     * @see QuartzTask
     *
     * @param quartzTasks 定时任务信息集合
     * @throws SchedulerException
     */
    public void updateTask(QuartzTask... quartzTasks) throws SchedulerException {
        if (quartzTasks != null) {
            for (QuartzTask quartzTask : quartzTasks) {
                updateTask(quartzTask);
            }
        }
    }

    /**
     * 暂停任务
     *
     * @see QuartzTask
     *
     * @param quartzTask 定时任务信息
     * @throws SchedulerException
     */
    public void pauseTask(QuartzTask quartzTask) throws SchedulerException {
        JobKey jobKey = new JobKey(quartzTask.getName(), quartzTask.getGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 批量暂停任务
     *
     * @see QuartzTask
     *
     * @param quartzTasks 定时任务信息集合
     * @throws SchedulerException
     */
    public void pauseTask(QuartzTask... quartzTasks) throws SchedulerException {
        if (quartzTasks != null) {
            for (QuartzTask quartzTask : quartzTasks) {
                pauseTask(quartzTask);
            }
        }
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseTask() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 继续任务
     *
     * @see QuartzTask
     *
     * @param quartzTask 定时任务信息
     * @throws SchedulerException
     */
    public void resumeTask(QuartzTask quartzTask) throws SchedulerException {
        JobKey jobKey = new JobKey(quartzTask.getName(), quartzTask.getGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 批量继续任务
     *
     * @see QuartzTask
     *
     * @param quartzTasks 定时任务信息集合
     * @throws SchedulerException
     */
    public void resumeTask(QuartzTask... quartzTasks) throws SchedulerException {
        if (quartzTasks != null) {
            for (QuartzTask quartzTask : quartzTasks) {
                resumeTask(quartzTask);
            }
        }
    }

    /**
     * 删除任务
     *
     * @see QuartzTask
     *
     * @param quartzTask 定时任务信息
     * @throws SchedulerException
     */
    public void deleteTask(QuartzTask quartzTask) throws SchedulerException {
        JobKey jobKey = new JobKey(quartzTask.getName(), quartzTask.getGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 批量删除任务
     *
     * @see QuartzTask
     *
     * @param quartzTasks 定时任务信息集合
     * @throws SchedulerException
     */
    public void deleteTask(QuartzTask... quartzTasks) throws SchedulerException {
        if (quartzTasks != null) {
            for (QuartzTask quartzTask : quartzTasks) {
                deleteTask(quartzTask);
            }
        }
    }

}
