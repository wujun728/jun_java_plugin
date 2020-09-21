/**
 * TriggerDataMapKey.java
 * Created at 2016-09-27
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.client;

/**
 * 描述 : TriggerDataMapKey
 *
 * @author wangkang
 */
public enum TriggerDataMapKey {
    /**
     * 描述 : 父触发ID
     */
    PARENT_TRIGGER_ID("parentTriggerId"),
    /**
     * 描述 : 触发ID
     */
    TRIGGER_ID("triggerId"),
    /**
     * 描述 : 触发类型
     */
    TRIGGER_TYPE("triggerType"),
    /**
     * 描述 : 服务代码
     */
    SERVICE_CODE("serviceCode"),
    /**
     * 描述 : bean名称
     */
    BEAN_NAME("beanName"),
    /**
     * 描述 : 是否异步
     */
    ASYNC("async");

    /**
     * <p>
     * Field value: 参数值
     * </p>
     */
    private String value;

    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param value 构造函数
     */
    TriggerDataMapKey(String value) {
        this.value = value;
    }

    /**
     * <p>
     * Description: 放回int值
     * </p>
     *
     * @return value 放回int值
     */
    public String value() {
        return this.value;
    }
}
