package com.xbd.quartz;

import org.quartz.*;
import org.springframework.lang.NonNull;

public abstract class QuartzTaskHandler {

    @NonNull
    protected Scheduler scheduler;

    /**
     * 动态添加任务
     *
     * @see QuartzTask
     *
     * @param quartzTask 定时任务信息
     * @throws SchedulerException
     */
    public abstract void addTask(QuartzTask quartzTask) throws SchedulerException;

    /**
     * 批量动态添加任务
     *
     * @see QuartzTask
     *
     * @param quartzTasks 定时任务信息集合
     * @throws SchedulerException
     */
    public abstract void addTask(QuartzTask... quartzTasks) throws SchedulerException;

    /**
     * 动态更新任务
     *
     * @see QuartzTask
     *
     * @param quartzTask 定时任务信息
     * @throws SchedulerException
     */
    public abstract void updateTask(QuartzTask quartzTask) throws SchedulerException;

    /**
     * 批量动态更新任务
     *
     * @see QuartzTask
     *
     * @param quartzTasks 定时任务信息集合
     * @throws SchedulerException
     */
    public abstract void updateTask(QuartzTask... quartzTasks) throws SchedulerException;

    /**
     * 暂停任务
     *
     * @see QuartzTask
     *
     * @param quartzTask 定时任务信息
     * @throws SchedulerException
     */
    public abstract void pauseTask(QuartzTask quartzTask) throws SchedulerException;

    /**
     * 批量暂停任务
     *
     * @see QuartzTask
     *
     * @param quartzTasks 定时任务信息集合
     * @throws SchedulerException
     */
    public abstract void pauseTask(QuartzTask... quartzTasks) throws SchedulerException;

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public abstract void pauseTask() throws SchedulerException;

    /**
     * 继续任务
     *
     * @see QuartzTask
     *
     * @param quartzTask 定时任务信息
     * @throws SchedulerException
     */
    public abstract void resumeTask(QuartzTask quartzTask) throws SchedulerException;

    /**
     * 批量继续任务
     *
     * @see QuartzTask
     *
     * @param quartzTasks 定时任务信息集合
     * @throws SchedulerException
     */
    public abstract void resumeTask(QuartzTask... quartzTasks) throws SchedulerException;

    /**
     * 删除任务
     *
     * @see QuartzTask
     *
     * @param quartzTask 定时任务信息
     * @throws SchedulerException
     */
    public abstract void deleteTask(QuartzTask quartzTask) throws SchedulerException;

    /**
     * 批量删除任务
     *
     * @see QuartzTask
     *
     * @param quartzTasks 定时任务信息集合
     * @throws SchedulerException
     */
    public abstract void deleteTask(QuartzTask... quartzTasks) throws SchedulerException;

    /**
     * 根据定时任务信息组织CronScheduleBuilder
     *
     * @see CronScheduleBuilder
     * @see QuartzTask
     *
     * @param quartzTask 定时任务信息
     * @return
     */
    public CronScheduleBuilder initCronScheduleBuilder(QuartzTask quartzTask) {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartzTask.getCronExpression());

        // 以当前时间为触发频率立刻触发一次执行，然后按照Cron频率依次执行
        if (quartzTask.getMisfireInstruction() == CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW) {
            cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
        } else if (quartzTask.getMisfireInstruction() == CronTrigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY) {
            // 以错过的第一个频率时间立刻开始执行，重做错过的所有频率周期后，当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
            cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
        } else if (quartzTask.getMisfireInstruction() == CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING) {
            // 不触发立即执行，等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
            cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
        }

        return cronScheduleBuilder;
    }

    /**
     * 根据任务名称、任务分组获取定时任务调度器中的该任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public CronTrigger getTrigger(String name, String group) throws SchedulerException {
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(new TriggerKey(name, group));

        return cronTrigger;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
