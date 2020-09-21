/**
 * RmsJob.java
 * Created at 2017-06-04
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 : RmsJob
 *
 * @author Administrator
 */
@Slf4j
public class RmsJob extends AbstractBaseRmsJob {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException { //NOSONAR
        this.execute(this.getRmsJobParam(jobExecutionContext));
    }

}
