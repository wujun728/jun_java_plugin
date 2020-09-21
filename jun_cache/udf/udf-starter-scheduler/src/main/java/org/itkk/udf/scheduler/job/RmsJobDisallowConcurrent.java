/**
 * RmsJobDisallowConcurrent.java
 * Created at 2017-06-04
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 描述 : RmsJobDisallowConcurrent
 *
 * @author Administrator
 */
public class RmsJobDisallowConcurrent extends AbstractBaseRmsJob {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException { //NOSONAR
        this.disallowConcurrentExecute(this.getRmsJobParam(jobExecutionContext));
    }

}
