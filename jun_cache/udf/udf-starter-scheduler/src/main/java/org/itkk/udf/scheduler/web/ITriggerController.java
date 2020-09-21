/**
 * ITriggerController.java
 * Created at 2017-06-11
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.itkk.udf.core.RestResponse;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 描述 : ITriggerController
 *
 * @author Administrator
 */
@Api(value = "触发器服务", consumes = "application/json", produces = "application/json", protocols = "http")
@RequestMapping(value = "/service/scheduler/trigger")
public interface ITriggerController {

    /**
     * 描述 : 保存简单触发器
     *
     * @param triggerCode 触发器代码
     * @return 操作结果
     * @throws SchedulerException SchedulerException
     */
    @ApiOperation(value = "SCHEDULER_TRIGGER_1", notes = "保存简单触发器")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "path", name = "triggerCode", value = "触发器代码", required = true, dataType = "string")
    })
    @RequestMapping(value = "simple/{triggerCode}", method = RequestMethod.POST)
    RestResponse<String> saveSimple(@PathVariable String triggerCode) throws SchedulerException; //NOSONAR

    /**
     * 描述 : 保存简单触发器(覆盖)
     *
     * @param triggerCode 触发器代码
     * @return 操作结果
     * @throws SchedulerException SchedulerException
     */
    @ApiOperation(value = "SCHEDULER_TRIGGER_2", notes = "保存简单触发器(覆盖)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "path", name = "triggerCode", value = "触发器代码", required = true, dataType = "string")
    })
    @RequestMapping(value = "simple/{triggerCode}/cover", method = RequestMethod.PUT)
    RestResponse<String> saveSimpleCover(@PathVariable String triggerCode) throws SchedulerException; //NOSONAR

    /**
     * 描述 : 保存CRON触发器
     *
     * @param triggerCode 触发器代码
     * @return 操作结果
     * @throws SchedulerException SchedulerException
     */
    @ApiOperation(value = "SCHEDULER_TRIGGER_3", notes = "保存CRON触发器")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "path", name = "triggerCode", value = "触发器代码", required = true, dataType = "string")
    })
    @RequestMapping(value = "cron/{triggerCode}", method = RequestMethod.POST)
    RestResponse<String> saveCron(@PathVariable String triggerCode) throws SchedulerException; //NOSONAR

    /**
     * 描述 : 保存CRON触发器(覆盖)
     *
     * @param triggerCode 触发器代码
     * @return 操作结果
     * @throws SchedulerException SchedulerException
     */
    @ApiOperation(value = "SCHEDULER_TRIGGER_4", notes = "保存CRON触发器(覆盖)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "path", name = "triggerCode", value = "触发器代码", required = true, dataType = "string")
    })
    @RequestMapping(value = "cron/{triggerCode}/cover", method = RequestMethod.PUT)
    RestResponse<String> saveCronCover(@PathVariable String triggerCode) throws SchedulerException;  //NOSONAR

    /**
     * 描述 : 删除触发器
     *
     * @param triggerCode 触发器代码
     * @return 操作结果
     * @throws SchedulerException SchedulerException
     */
    @ApiOperation(value = "SCHEDULER_TRIGGER_5", notes = "删除触发器")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "rmsApplicationName", value = "rms应用名称", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsSign", value = "rms认证秘钥", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "header", name = "rmsServiceCode", value = "rms接口编号", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "path", name = "triggerCode", value = "触发器代码", required = true, dataType = "string")
    })
    @RequestMapping(value = "{triggerCode}", method = RequestMethod.DELETE)
    RestResponse<String> remove(@PathVariable String triggerCode) throws SchedulerException; //NOSONAR

}
