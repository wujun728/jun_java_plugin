/**
 * TriggerController.java
 * Created at 2017-05-26
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.web;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.itkk.udf.core.RestResponse;
import org.itkk.udf.scheduler.service.TriggerService;

/**
 * 描述 : TriggerController
 *
 * @author Administrator
 */
@RestController
public class TriggerController implements ITriggerController {

    /**
     * 描述 : triggerService
     */
    @Autowired
    private TriggerService triggerService;

    @Override
    public RestResponse<String> saveSimple(@PathVariable String triggerCode)
            throws SchedulerException {
        triggerService.saveSimpleTirgger(triggerCode, false);
        return new RestResponse<>();
    }

    @Override
    public RestResponse<String> saveSimpleCover(@PathVariable String triggerCode)
            throws SchedulerException {
        triggerService.saveSimpleTirgger(triggerCode, true);
        return new RestResponse<>();
    }

    @Override
    public RestResponse<String> saveCron(@PathVariable String triggerCode) throws SchedulerException {
        triggerService.saveCronTrigger(triggerCode, false);
        return new RestResponse<>();
    }

    @Override
    public RestResponse<String> saveCronCover(@PathVariable String triggerCode)
            throws SchedulerException {
        triggerService.saveCronTrigger(triggerCode, true);
        return new RestResponse<>();
    }

    @Override
    public RestResponse<String> remove(@PathVariable String triggerCode) throws SchedulerException {
        triggerService.remove(triggerCode);
        return new RestResponse<>();
    }

}
