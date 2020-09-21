/**
 * AbstractExecutor.java
 * Created at 2017-08-09
 * Created by Administrator
 * Copyright (C) 2016 egridcloud.com, All rights reserved.
 */
package org.itkk.udf.scheduler.client.executor;

import java.util.HashMap;
import java.util.Map;

import org.itkk.udf.scheduler.client.TriggerDataMapKey;
import org.itkk.udf.scheduler.client.TriggerType;
import org.itkk.udf.scheduler.client.domain.RmsJobParam;
import org.itkk.udf.scheduler.client.service.SchClientService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述 : AbstractExecutor
 *
 * @author Administrator
 *
 */
public abstract class AbstractExecutor {

    /**
     * schClientService
     */
    @Autowired
    private SchClientService schClientService;

    /**
     * 执行方法 描述 : 执行方法
     *
     * @param param param
     */
    public void handle(RmsJobParam param) {
        this.handle(param.getId(), param.getJobDataMap());
    }

    /**
     * 
     * 描述 : 调用链 (允许并发,异步调用)
     *
     * @param parentId 父ID
     * @param serviceCode 服务代码
     * @param beanName bean名称
     * @param param 业务参数
     * @return 结果
     */
    protected String chain(String parentId, String serviceCode, String beanName, Map<String, String> param) {
        return this.chain(true, parentId, serviceCode, beanName, true, param);
    }

    /**
     * 
     * 描述 : 调用链
     *
     * @param isConcurrent 是否并发(true:允许并发,false:禁止并发)
     * @param parentId 父ID
     * @param serviceCode 服务代码
     * @param beanName bean名称
     * @param async 是否异步
     * @param param 业务参数
     * @return 结果
     */
    protected String chain(boolean isConcurrent, String parentId, String serviceCode, String beanName, boolean async, Map<String, String> param) {
        //构建jobDataMap
        Map<String, String> jdm = new HashMap<>();
        jdm.put(TriggerDataMapKey.PARENT_TRIGGER_ID.value(), parentId);
        jdm.put(TriggerDataMapKey.TRIGGER_TYPE.value(), TriggerType.CALL_CHAIN.value());
        jdm.put(TriggerDataMapKey.SERVICE_CODE.value(), serviceCode);
        jdm.put(TriggerDataMapKey.BEAN_NAME.value(), beanName);
        jdm.put(TriggerDataMapKey.ASYNC.value(), Boolean.toString(async));
        jdm.putAll(param);
        // 调用 & 返回
        String jobCode = isConcurrent ? "rmsJob" : "rmsJobDisallowConcurrent";
        return this.schClientService.trigger(jobCode, jdm);
    }

    /**
     * 描述 : 执行方法
     *
     * @param id id
     * @param jobDataMap jobDataMap
     */
    protected abstract void handle(String id, Map<String, Object> jobDataMap);

}
