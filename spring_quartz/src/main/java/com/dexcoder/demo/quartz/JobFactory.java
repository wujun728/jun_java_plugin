package com.dexcoder.demo.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexcoder.demo.model.ScheduleJob;
import com.dexcoder.demo.vo.ScheduleJobVo;

/**
 * 任务工厂类,非同步
 *
 * User: liyd
 * Date: 14-1-3
 * Time: 上午10:11
 */
@DisallowConcurrentExecution
public class JobFactory implements Job {

    /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(JobFactory.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {

        LOG.info("JobFactory execute");

        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(
            ScheduleJobVo.JOB_PARAM_KEY);

        System.out.println("jobName:" + scheduleJob.getJobName() + "  " + scheduleJob);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
