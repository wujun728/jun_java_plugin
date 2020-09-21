/**
 * JobService.java
 * Created at 2017-06-02
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.itkk.udf.core.utils.ClassUtil;
import org.itkk.udf.scheduler.SchedulerProperties;
import org.itkk.udf.scheduler.client.SchException;
import org.itkk.udf.scheduler.job.AbstractBaseJob;
import org.itkk.udf.scheduler.meta.JobDetailMeta;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述 : JobService
 *
 * @author Administrator
 */
@Service
public class JobService {

    /**
     * 描述 : SchedulerFactoryBean
     */
    @Autowired
    @Qualifier("clusterQuartzScheduler")
    private SchedulerFactoryBean s;

    /**
     * 描述 : schedulerProperties
     */
    @Autowired
    private SchedulerProperties schedulerProperties;

    /**
     * 描述 : 获得作业列表
     *
     * @return 作业列表
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public List<String> jobClassNameList() throws ClassNotFoundException {
        //获得类型列表
        List<Class<AbstractBaseJob>> jobList =
                ClassUtil.getSubClasses(AbstractBaseJob.class, "org.itkk");
        //构造返回值
        List<String> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(jobList)) {
            for (Class<AbstractBaseJob> claz : jobList) {
                result.add(claz.getName());
            }
        }
        //返回
        return result;
    }

    /**
     * 描述 : 作业组
     *
     * @return 作业组
     */
    public Map<String, String> group() {
        return schedulerProperties.getJobGroup();
    }

    /**
     * 描述 : 保存
     *
     * @param jobCode 作业代码
     * @param cover   是否覆盖
     * @throws SchedulerException     SchedulerException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public void save(String jobCode, boolean cover) //NOSONAR
            throws SchedulerException, ClassNotFoundException {
        //获得作业元数据
        JobDetailMeta jobDetailMeta = getJobDetailMeta(jobCode);
        // 获得计划任务管理器
        Scheduler sch = this.s.getScheduler();
        // 设置jobkey
        JobKey jobKey = new JobKey(jobDetailMeta.getName(), jobDetailMeta.getGroup());
        //判断job是否存在
        if (sch.checkExists(jobKey) && !cover) {
            throw new SchException("job key : " + jobDetailMeta.getName() + " / group : "
                    + jobDetailMeta.getGroup() + " already exist!");
        }
        // 获得jobDataMap
        JobDataMap jobDataMap = new JobDataMap();
        if (MapUtils.isNotEmpty(jobDetailMeta.getDataMap())) {
            jobDataMap = new JobDataMap(jobDetailMeta.getDataMap());
        }
        //实例化作业
        JobDetail jobDetail = JobBuilder.newJob(((Class<Job>) Class.forName(jobDetailMeta.getClassName()))) //NOSONAR
                .withIdentity(jobKey).withDescription(jobDetailMeta.getDescription())
                .requestRecovery(jobDetailMeta.getRecovery())
                .storeDurably(jobDetailMeta.getDurability()).setJobData(jobDataMap).build();
        // 添加job
        sch.addJob(jobDetail, true, !jobDetailMeta.getDurability());
    }

    /**
     * 描述 : 移除
     *
     * @param jobCode jobCode
     * @throws SchedulerException SchedulerException
     */
    public void remove(String jobCode) throws SchedulerException {
        //获得作业元数据
        JobDetailMeta jobDetailMeta = getJobDetailMeta(jobCode);
        // 设置jobkey
        JobKey jobKey = getJobKey(jobDetailMeta);
        // 删除
        this.s.getScheduler().deleteJob(jobKey);
    }

    /**
     * 描述 : 触发
     *
     * @param jobCode    作业代码
     * @param jobDataMap 作业数据
     * @throws SchedulerException SchedulerException
     */
    public void trigger(String jobCode, JobDataMap jobDataMap) throws SchedulerException {
        //获得作业元数据
        JobDetailMeta jobDetailMeta = getJobDetailMeta(jobCode);
        // 设置jobkey
        JobKey jobKey = getJobKey(jobDetailMeta);
        // 触发
        this.s.getScheduler().triggerJob(jobKey, jobDataMap);
    }

    /**
     * 描述 : 暂停
     *
     * @param jobCode 作业代码
     * @throws SchedulerException SchedulerException
     */
    public void puse(String jobCode) throws SchedulerException {
        //获得作业元数据
        JobDetailMeta jobDetailMeta = getJobDetailMeta(jobCode);
        // 设置jobkey
        JobKey jobKey = getJobKey(jobDetailMeta);
        // 暂停
        this.s.getScheduler().pauseJob(jobKey);
    }

    /**
     * 描述 : 恢复
     *
     * @param jobCode 作业代码
     * @throws SchedulerException SchedulerException
     */
    public void resume(String jobCode) throws SchedulerException {
        //获得作业元数据
        JobDetailMeta jobDetailMeta = getJobDetailMeta(jobCode);
        // 设置jobkey
        JobKey jobKey = getJobKey(jobDetailMeta);
        // 恢复
        this.s.getScheduler().resumeJob(jobKey);
    }

    /**
     * 描述 : 暂停所有
     *
     * @throws SchedulerException SchedulerException
     */
    public void puseAll() throws SchedulerException {
        // 暂停所有
        this.s.getScheduler().pauseAll();
    }

    /**
     * 描述 : 恢复所有
     *
     * @throws SchedulerException SchedulerException
     */
    public void resumeAll() throws SchedulerException {
        // 暂停所有
        this.s.getScheduler().resumeAll();
    }

    /**
     * 描述 : 返回jobKey
     *
     * @param jobDetailMeta 作业元数据
     * @return jobKey
     * @throws SchedulerException SchedulerException
     */
    protected JobKey getJobKey(JobDetailMeta jobDetailMeta) throws SchedulerException {
        // 获得计划任务管理器
        Scheduler sch = this.s.getScheduler();
        // 设置jobkey
        JobKey jobKey = new JobKey(jobDetailMeta.getName(), jobDetailMeta.getGroup());
        //判断job是否存在
        if (sch.checkExists(jobKey)) {
            return jobKey;
        } else {
            throw new SchException("job key : " + jobDetailMeta.getName() + " / group : "
                    + jobDetailMeta.getGroup() + " not exist!");
        }
    }

    /**
     * 描述 : 返回作业元数据
     *
     * @param jobCode 作业代码
     * @return 作业元数据
     */
    protected JobDetailMeta getJobDetailMeta(String jobCode) {
        //判空
        if (MapUtils.isEmpty(schedulerProperties.getJobDetail())) {
            throw new SchException("JobDetail not defined!");
        }
        //获得作业定义
        JobDetailMeta jobDetailMeta = schedulerProperties.getJobDetail().get(jobCode);
        //判断作业是否存在
        if (jobDetailMeta == null) {
            throw new SchException("jobCode : " + jobCode + " not defined!");
        }
        //获得组别定义
        String groupName = schedulerProperties.getJobGroup().get(jobDetailMeta.getGroup());
        //判断组别是否存在
        if (StringUtils.isBlank(groupName)) {
            throw new SchException("group : " + jobDetailMeta.getGroup() + " not defined!");
        }
        //返回
        return jobDetailMeta;
    }

}
