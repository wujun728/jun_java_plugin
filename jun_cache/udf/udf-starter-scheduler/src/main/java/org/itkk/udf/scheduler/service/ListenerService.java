/**
 * ListenerService.java
 * Created at 2017-06-01
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.itkk.udf.scheduler.IListenerEvent;
import org.itkk.udf.scheduler.SchedulerConfig;
import org.itkk.udf.scheduler.domain.ScheduledTriggerLog;
import org.quartz.*;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 描述 : ListenerService
 *
 * @author Administrator
 */
@Service
public class ListenerService {

    /**
     * 描述 : schedulerConfig
     */
    @Autowired
    private SchedulerConfig schedulerConfig;

    /**
     * 描述 : listenerLog
     */
    @Autowired(required = false)
    private IListenerEvent listenerLog;

    /**
     * <p>
     * Description: 保存未正常触发的记录
     * </p>
     *
     * @param trigger 触发器
     */
    public void saveTriggerMisfired(Trigger trigger) { // 1
        ScheduledTriggerLog tstl = new ScheduledTriggerLog();
        tstl.setLogid(UUID.randomUUID().toString());
        tstl.setScheduledFireTime(null);
        tstl.setFireTime(null);
        tstl.setEndTime(null);
        tstl.setJobRunTime(null);
        tstl.setStatus("misfired");
        tstl.setResult(null);
        tstl.setErrorMsg(null);
        tstl.setTriggerName(trigger.getKey().getName());
        tstl.setTriggerGroup(trigger.getKey().getGroup());
        tstl.setJobName(trigger.getJobKey().getName());
        tstl.setJobGroup(trigger.getJobKey().getGroup());
        tstl.setJobClass(null);
        tstl.setThreadGroupName(Thread.currentThread().getThreadGroup().getName());
        tstl.setThreadId(Long.toString(Thread.currentThread().getId()));
        tstl.setThreadName(Thread.currentThread().getName());
        tstl.setThreadPriority(Long.toString(Thread.currentThread().getPriority()));
        tstl.setScheduledId(null);
        tstl.setScheduledName(null);
        tstl.setCreateDate(new Date());
        if (listenerLog != null) {
            listenerLog.save(tstl);
        }
    }

    /**
     * <p>
     * Description: 准备触发
     * </p>
     *
     * @param context 执行上下文
     * @throws SchedulerException SchedulerException
     */
    public void saveTriggerFired(JobExecutionContext context) throws SchedulerException {
        // 状态和异常信息
        String status = "triggering";
        String exceptionDetail = null;
        // 是否记录执行历史(true记录,false不记录) , 是否详细记录执行历史(true记录,false不记录)
        if (this.schedulerConfig.getLogFlag() && this.schedulerConfig.getLogDetailFlag()) {
            save(context, status, null, exceptionDetail);
        }
    }

    /**
     * <p>
     * Description: 判断是否否决
     * </p>
     *
     * @param context 执行上下文
     * @return 是否否决
     * @throws SchedulerException SchedulerException
     */
    public boolean saveVetoJobExecution(JobExecutionContext context) throws SchedulerException {
        // 是否否决
        boolean vetoed = false;
        // 状态和异常信息
        String status = "vetoed(" + vetoed + ")";
        String exceptionDetail = null;
        // 是否记录执行历史(true记录,false不记录) , 是否详细记录执行历史(true记录,false不记录)
        if (this.schedulerConfig.getLogFlag() && this.schedulerConfig.getLogDetailFlag()) {
            save(context, status, null, exceptionDetail);
        }
        return vetoed;
    }

    /**
     * <p>
     * Description: 准备执行作业
     * </p>
     *
     * @param context 上下文
     * @throws SchedulerException SchedulerException
     */
    public void saveJobToBeExecuted(JobExecutionContext context) throws SchedulerException {
        // 状态和异常信息
        String status = "toBeExecuted";
        String exceptionDetail = null;
        // 是否记录执行历史(true记录,false不记录) , 是否详细记录执行历史(true记录,false不记录)
        if (this.schedulerConfig.getLogFlag() && this.schedulerConfig.getLogDetailFlag()) {
            save(context, status, null, exceptionDetail);
        }
    }

    /**
     * <p>
     * Description: 作业执行被否决
     * </p>
     *
     * @param context 上下文
     * @throws SchedulerException SchedulerException
     */
    public void saveJobExecutionVetoed(JobExecutionContext context) throws SchedulerException {
        // 状态和异常信息
        String status = "executionVetoed";
        String exceptionDetail = null;
        // 写入新的记录
        save(context, status, null, exceptionDetail);
    }

    /**
     * <p>
     * Description: 作业执行完毕
     * </p>
     *
     * @param context      上下文
     * @param jobException 执行异常
     * @throws SchedulerException 异常信息
     */
    public void saveJobWasExecuted(JobExecutionContext context, JobExecutionException jobException)
            throws SchedulerException {
        // 状态和异常信息
        String status = "executed";
        String exceptionDetail = null;
        // 如果作业异常,则放入信息到result中
        if (null != jobException) {
            Map<String, Object> result = new HashMap<>();
            result.put("status", "error");
            result.put("jobException", jobException);
            context.setResult(result);
            // 获得异常信息
            exceptionDetail = ExceptionUtils.getStackTrace(jobException);
        }
        // 是否记录执行历史(true记录,false不记录) , 是否详细记录执行历史(true记录,false不记录)
        if (this.schedulerConfig.getLogFlag() && this.schedulerConfig.getLogDetailFlag()) {
            save(context, status, null, exceptionDetail);
        }
    }

    /**
     * <p>
     * Description: 触发完成
     * </p>
     *
     * @param context                上下文
     * @param triggerInstructionCode 状态
     * @throws SchedulerException SchedulerException
     */
    @SuppressWarnings("unchecked")
    public void saveTriggerComplete(JobExecutionContext context,
                                    CompletedExecutionInstruction triggerInstructionCode) throws SchedulerException {
        // 状态和异常信息
        String status = "complete";
        String exceptionDetail = null;
        // 获得result
        Object o = context.getResult();
        if (null != o && o instanceof Map) {
            Map<String, Object> result = (Map<String, Object>) o;
            status = result.get("status").toString();
            JobExecutionException jobException = (JobExecutionException) result.get("jobException");
            // 获得异常信息
            exceptionDetail = ExceptionUtils.getStackTrace(jobException);
        }
        // 是否记录执行历史(true记录,false不记录)
        if (this.schedulerConfig.getLogFlag()) {
            save(context, status, triggerInstructionCode.toString(), exceptionDetail);
        }
    }

    /**
     * 描述 : build
     *
     * @param context         context
     * @param status          status
     * @param result          result
     * @param exceptionDetail exceptionDetail
     * @throws SchedulerException SchedulerException
     */
    private void save(JobExecutionContext context, String status, String result,
                      String exceptionDetail) throws SchedulerException {
        // 获得计划任务实例
        Scheduler s = context.getScheduler();
        //构造
        ScheduledTriggerLog tstl = new ScheduledTriggerLog();
        tstl.setLogid(context.getFireInstanceId());
        tstl.setScheduledFireTime(context.getScheduledFireTime());
        tstl.setFireTime(context.getFireTime());
        tstl.setEndTime(new Date());
        tstl.setJobRunTime(context.getJobRunTime());
        tstl.setStatus(status);
        tstl.setResult(result);
        tstl.setErrorMsg(exceptionDetail);
        tstl.setTriggerName(context.getTrigger().getKey().getName());
        tstl.setTriggerGroup(context.getTrigger().getKey().getGroup());
        tstl.setJobName(context.getJobDetail().getKey().getName());
        tstl.setJobGroup(context.getJobDetail().getKey().getGroup());
        tstl.setJobClass(context.getJobDetail().getJobClass().getName());
        tstl.setThreadGroupName(Thread.currentThread().getThreadGroup().getName());
        tstl.setThreadId(Long.toString(Thread.currentThread().getId()));
        tstl.setThreadName(Thread.currentThread().getName());
        tstl.setThreadPriority(Long.toString(Thread.currentThread().getPriority()));
        tstl.setScheduledId(s.getSchedulerInstanceId());
        tstl.setScheduledName(s.getSchedulerName());
        tstl.setCreateDate(new Date());
        if (listenerLog != null) {
            listenerLog.delete(context.getFireInstanceId());
            listenerLog.save(tstl);
        }
    }

}
