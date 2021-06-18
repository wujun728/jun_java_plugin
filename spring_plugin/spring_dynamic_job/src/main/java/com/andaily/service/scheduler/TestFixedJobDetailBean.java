package com.andaily.service.scheduler;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 非动态(固定的) JOB实现类. 配置有XML中
 *
 * @author Wujun
 */
public class TestFixedJobDetailBean extends QuartzJobBean {

    private static final Logger LOGGER = Logger.getLogger(TestFixedJobDetailBean.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("[Fixed-Job]  TestFixedJobDetailBean Executing....\n");
    }
}