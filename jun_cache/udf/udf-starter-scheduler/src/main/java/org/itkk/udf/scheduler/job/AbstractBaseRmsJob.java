/**
 * RmsJob.java
 * Created at 2017-06-04
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.itkk.udf.cache.redis.lock.RedisBasedDistributedLock;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.rms.Rms;
import org.itkk.udf.scheduler.IRmsJobEvent;
import org.itkk.udf.scheduler.client.RmsJobStats;
import org.itkk.udf.scheduler.client.SchException;
import org.itkk.udf.scheduler.client.TriggerDataMapKey;
import org.itkk.udf.scheduler.client.TriggerType;
import org.itkk.udf.scheduler.client.domain.RmsJobParam;
import org.itkk.udf.scheduler.client.domain.RmsJobResult;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 描述 : RmsJob
 *
 * @author Administrator
 */
@Slf4j
public abstract class AbstractBaseRmsJob extends AbstractBaseJob {

    /**
     * RMS_JOB_DISALLOW_CONCURRENT_PREFIX
     */
    public static final String RMS_JOB_DISALLOW_CONCURRENT_PREFIX = "rmsJobDisallowConcurrent_";

    /**
     * redisBasedDistributedLock
     */
    @Autowired
    private RedisBasedDistributedLock redisBasedDistributedLock;

    /**
     * 获得RmsJobParam
     *
     * @param jobExecutionContext jobExecutionContext
     * @return RmsJobParam
     */
    protected RmsJobParam getRmsJobParam(JobExecutionContext jobExecutionContext) {
        //从trigger中获得jobDataMap
        JobDataMap jobDataMap = jobExecutionContext.getTrigger().getJobDataMap();
        //获得必要字段
        String triggerId = jobDataMap.getString(TriggerDataMapKey.TRIGGER_ID.value());
        triggerId = StringUtils.isBlank(triggerId) ? UUID.randomUUID().toString() : triggerId;
        String parentTriggerId = jobDataMap.getString(TriggerDataMapKey.PARENT_TRIGGER_ID.value());
        String triggerType = jobDataMap.getString(TriggerDataMapKey.TRIGGER_TYPE.value());
        triggerType = StringUtils.isBlank(triggerType) ? TriggerType.TRIGGER.value() : triggerType;
        String serviceCode = jobDataMap.getString(TriggerDataMapKey.SERVICE_CODE.value());
        String beanName = jobDataMap.getString(TriggerDataMapKey.BEAN_NAME.value());
        Boolean async = jobDataMap.getBoolean(TriggerDataMapKey.ASYNC.value());
        //封装请求参数
        RmsJobParam rmsJobParam = new RmsJobParam();
        rmsJobParam.setId(triggerId);
        rmsJobParam.setParentId(parentTriggerId);
        rmsJobParam.setTriggerType(triggerType);
        rmsJobParam.setFireInstanceId(jobExecutionContext.getFireInstanceId());
        rmsJobParam.setServiceCode(serviceCode);
        rmsJobParam.setBeanName(beanName);
        rmsJobParam.setAsync(async);
        rmsJobParam.setJobDataMap(jobDataMap.getWrappedMap());
        //返回
        return rmsJobParam;
    }

    /**
     * 禁止并发执行
     *
     * @param rmsJobParam rmsJobParam
     * @throws JobExecutionException JobExecutionException
     */
    protected void disallowConcurrentExecute(RmsJobParam rmsJobParam) throws JobExecutionException {
        //定义锁信息 (锁超时时间为60分钟) (异步任务,解锁在callback中完成,同步任务,解锁在本方法完成)
        String name = AbstractBaseRmsJob.RMS_JOB_DISALLOW_CONCURRENT_PREFIX + rmsJobParam.getServiceCode() + "_" + rmsJobParam.getBeanName();
        final long timeout = 60;
        TimeUnit timeUnit = TimeUnit.MINUTES;
        //尝试锁定,锁定成功则触发,锁定失败则跳过
        if (redisBasedDistributedLock.lock(name, timeout, timeUnit, rmsJobParam)) {
            try {
                this.execute(rmsJobParam);
            } catch (JobExecutionException e) {
                //解锁
                redisBasedDistributedLock.unlock(name);
                //抛出异常
                throw e;
            } finally {
                //同步任务解锁
                if (!rmsJobParam.getAsync()) {
                    redisBasedDistributedLock.unlock(name);
                }
            }
        } else { //跳过执行,并且记录
            RmsJobResult result = new RmsJobResult();
            result.setId(rmsJobParam.getId());
            result.setStats(RmsJobStats.SKIP.value());
            save(rmsJobParam, result);
        }
    }

    /**
     * 执行
     *
     * @param rmsJobParam rmsJobParam
     * @throws JobExecutionException JobExecutionException
     */
    protected void execute(RmsJobParam rmsJobParam) throws JobExecutionException {
        try {
            //校验
            if (StringUtils.isBlank(rmsJobParam.getServiceCode())) {
                throw new SchException("serviceCode can not be empty");
            }
            if (StringUtils.isBlank(rmsJobParam.getBeanName())) {
                throw new SchException("beanName can not be empty");
            }
            //获得RMS
            Rms rms = this.getApplicationContext().getBean(Rms.class);
            //请求
            ResponseEntity<RestResponse<RmsJobResult>> result = rms.call(rmsJobParam.getServiceCode(), rmsJobParam, null, new ParameterizedTypeReference<RestResponse<RmsJobResult>>() {
            }, null);
            //判断http状态
            if (result.getStatusCode() != HttpStatus.OK) {
                //抛出异常
                throw new SchException("http状态:" + result.getStatusCode().toString());
            }
            //记录
            save(rmsJobParam, result.getBody().getResult());
        } catch (Exception e) {
            //定义返回值
            RmsJobResult result = new RmsJobResult();
            result.setId(rmsJobParam.getId());
            result.setStats(RmsJobStats.ERROR.value());
            result.setErrorMsg(ExceptionUtils.getStackTrace(e));
            //记录
            save(rmsJobParam, result);
            //抛出异常
            throw new JobExecutionException(e);
        }
    }

    /**
     * 保存
     *
     * @param param  param
     * @param result result
     */
    private void save(RmsJobParam param, RmsJobResult result) {
        IRmsJobEvent rmsJobEvent = this.getRmsJobEvent();
        if (rmsJobEvent != null) {
            rmsJobEvent.save(param, result);
        }
    }

    /**
     * 获得RmsJobEvent
     *
     * @return IRmsJobEvent
     */
    private IRmsJobEvent getRmsJobEvent() {
        String[] beanNames = this.getApplicationContext().getBeanNamesForType(IRmsJobEvent.class);
        if (ArrayUtils.isNotEmpty(beanNames)) {
            return this.getApplicationContext().getBean(IRmsJobEvent.class);
        }
        return null;
    }

}
