/**
 * SchedulerService.java
 * Created at 2017-06-02
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.service;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * 描述 : SchedulerService
 *
 * @author Administrator
 */
@Service
public class SchedulerService {

    /**
     * 描述 : SchedulerFactoryBean
     */
    @Autowired
    @Qualifier("clusterQuartzScheduler")
    private SchedulerFactoryBean s;

    /**
     * 描述 : 清理
     *
     * @throws SchedulerException SchedulerException
     */
    public void clear() throws SchedulerException {
        this.s.getScheduler().clear();
    }

}
