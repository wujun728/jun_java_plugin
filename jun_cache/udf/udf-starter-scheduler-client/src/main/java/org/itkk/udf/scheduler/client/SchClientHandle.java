/**
 * SchClientService.java
 * Created at 2017-06-12
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.core.exception.SystemRuntimeException;
import org.itkk.udf.rms.Rms;
import org.itkk.udf.scheduler.client.domain.RmsJobParam;
import org.itkk.udf.scheduler.client.domain.RmsJobResult;
import org.itkk.udf.scheduler.client.executor.AbstractExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 描述 : SchClientService
 *
 * @author Administrator
 */
@Component
@Slf4j
public class SchClientHandle implements ApplicationContextAware {

    /**
     * 描述 : spring上下文
     */
    private ApplicationContext applicationContext;

    /**
     * 描述 : rms
     */
    @Autowired
    private Rms rms;

    /**
     * 描述 : 异步执行
     *
     * @param param 参数
     * @param result 结果
     */
    @Async
    public void asyncHandle(RmsJobParam param, RmsJobResult result) {
        try {
            //执行
            this.handle(param);
            result.setClientEndExecuteTime(new Date());
            result.setStats(RmsJobStats.COMPLETE.value());
            //回调
            this.callback(result);
        } catch (Exception e) {
            result.setClientEndExecuteTime(new Date());
            result.setStats(RmsJobStats.ERROR.value());
            result.setErrorMsg(ExceptionUtils.getStackTrace(e));
            //回调
            this.callback(result);
            //抛出异常
            throw new SchException(e);
        }

    }

    /**
     * 描述 : 同步执行
     *
     * @param param 参数
     */
    public void handle(RmsJobParam param) {
        //判断bean是否存在
        if (!applicationContext.containsBean(param.getBeanName())) {
            throw new SchException(param.getBeanName() + " not definition");
        }
        //获得bean
        AbstractExecutor bean = applicationContext.getBean(param.getBeanName(), AbstractExecutor.class);
        //执行
        bean.handle(param);
    }

    /**
     * 描述 : 回调(重处理)
     *
     * @param result result
     */
    @Retryable(maxAttempts = 3, value = Exception.class)
    private void callback(RmsJobResult result) {
        log.info("try to callback");
        final String serviceCode = "SCH_CLIENT_CALLBACK_1";
        rms.call(serviceCode, result, null, new ParameterizedTypeReference<RestResponse<String>>() {
        }, null);
    }

    /**
     * 描述 : 重试失败后执行的方法
     *
     * @param e e
     */
    @Recover
    public void recover(Exception e) {
        throw new SystemRuntimeException(e);
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) {
        applicationContext = arg0;
    }

}
