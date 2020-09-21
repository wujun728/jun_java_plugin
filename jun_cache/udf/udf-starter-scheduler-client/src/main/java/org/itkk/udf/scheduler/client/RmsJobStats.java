/**
 * RmsJobStats.java
 * Created at 2016-09-27
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.client;

/**
 * 描述 : RmsJobStats
 *
 * @author wangkang
 */
public enum RmsJobStats {
    /**
     * 描述 : 正在执行
     */
    EXECUTING(1),
    /**
     * 描述 : 完成
     */
    COMPLETE(2),
    /**
     * 描述 : 错误
     */
    ERROR(-1),
    /**
     * 描述 : 跳过
     */
    SKIP(-2);

    /**
     * <p>
     * Field value: 参数值
     * </p>
     */
    private Integer value;

    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param value 构造函数
     */
    RmsJobStats(Integer value) {
        this.value = value;
    }

    /**
     * <p>
     * Description: 放回int值
     * </p>
     *
     * @return value 放回int值
     */
    public Integer value() {
        return this.value;
    }
}
