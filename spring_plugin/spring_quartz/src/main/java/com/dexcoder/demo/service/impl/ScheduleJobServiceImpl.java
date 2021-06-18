package com.dexcoder.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dexcoder.commons.bean.BeanConverter;
import com.dexcoder.dal.JdbcDao;
import com.dexcoder.dal.build.Criteria;
import com.dexcoder.demo.model.ScheduleJob;
import com.dexcoder.demo.service.ScheduleJobService;
import com.dexcoder.demo.utils.ScheduleUtils;
import com.dexcoder.demo.vo.ScheduleJobVo;

/**
 * 定时任务
 *
 * Created by liyd on 12/19/14.
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    /** 调度工厂Bean */
    @Autowired
    private Scheduler scheduler;

    /** 通用dao */
    @Autowired
    private JdbcDao   jdbcDao;

    public void initScheduleJob() {
        List<ScheduleJob> scheduleJobList = jdbcDao.queryList(Criteria.select(ScheduleJob.class));
        if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }
        for (ScheduleJob scheduleJob : scheduleJobList) {

            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(),
                scheduleJob.getJobGroup());

            //不存在，创建一个
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    public Long insert(ScheduleJobVo scheduleJobVo) {
        ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        return jdbcDao.insert(scheduleJob);
    }

    public void update(ScheduleJobVo scheduleJobVo) {
        ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        jdbcDao.update(scheduleJob);
    }

    public void delUpdate(ScheduleJobVo scheduleJobVo) {
        ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
        //先删除
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //再创建
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        //数据库直接更新即可
        jdbcDao.update(scheduleJob);
    }

    public void delete(Long scheduleJobId) {
        ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
        //删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //删除数据
        jdbcDao.delete(ScheduleJob.class, scheduleJobId);
    }

    public void runOnce(Long scheduleJobId) {

        ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
        ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    public void pauseJob(Long scheduleJobId) {

        ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
        ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());

        //演示数据库就不更新了
    }

    public void resumeJob(Long scheduleJobId) {
        ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
        ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());

        //演示数据库就不更新了
    }

    public ScheduleJobVo get(Long scheduleJobId) {
        ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class, scheduleJobId);
        return scheduleJob.getTargetObject(ScheduleJobVo.class);
    }

    public List<ScheduleJobVo> queryList(ScheduleJobVo scheduleJobVo) {

        List<ScheduleJob> scheduleJobs = jdbcDao.queryList(scheduleJobVo.getTargetObject(ScheduleJob.class));

        List<ScheduleJobVo> scheduleJobVoList = BeanConverter.convert(ScheduleJobVo.class, scheduleJobs);
        try {
            for (ScheduleJobVo vo : scheduleJobVoList) {

                JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                if (CollectionUtils.isEmpty(triggers)) {
                    continue;
                }

                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
                Trigger trigger = triggers.iterator().next();
                scheduleJobVo.setJobTrigger(trigger.getKey().getName());

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                vo.setStatus(triggerState.name());

                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    vo.setCronExpression(cronExpression);
                }
            }
        } catch (SchedulerException e) {
            //演示用，就不处理了
        }
        return scheduleJobVoList;
    }

    public List<ScheduleJobVo> queryExecutingJobList() {
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                ScheduleJobVo job = new ScheduleJobVo();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setJobTrigger(trigger.getKey().getName());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
            return jobList;
        } catch (SchedulerException e) {
            //演示用，就不处理了
            return null;
        }

    }
}
