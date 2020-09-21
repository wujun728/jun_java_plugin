/**
 * TriggerType.java
 * Created at 2016-09-27
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.client;

/**
 * 描述 : TriggerType
 *
 * @author wangkang
 */
public enum TriggerType {
    /**
     * 描述 : 手动触发
     */
    MANUAL("manual"),
    /**
     * 描述 : 触发器触发
     */
    TRIGGER("trigger"),
    /**
     * 描述 : 触发链
     */
    CALL_CHAIN("callChain");

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
    TriggerType(String value) {
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
