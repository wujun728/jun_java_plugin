package com.zb.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * job执行前后的监听处理
 * 
 * 作者: zhoubang 
 * 日期：2015年7月27日 上午9:09:40
 */
@Component(value = "appJobExecutionListener")
public class AppJobExecutionListener implements JobExecutionListener {

    private final static Logger logger = LoggerFactory.getLogger(AppJobExecutionListener.class);

    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("Job completed: " + jobExecution.getJobId());
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            logger.info("Job failed: " + jobExecution.getJobId());
        }
    }

    public void beforeJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("Job completed: " + jobExecution.getJobId());
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            logger.info("Job failed: " + jobExecution.getJobId());
        }
    }

    public AppJobExecutionListener() {
    }
}
