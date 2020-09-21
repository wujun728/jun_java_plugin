/**
 * TriggerMeta.java
 * Created at 2017-06-02
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.meta;

import lombok.Data;
import org.quartz.Trigger;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 描述 : TriggerMeta.java
 *
 * @author Administrator
 */
@Data
public class TriggerMeta implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 作业代码
     */
    private String jobCode;

    /**
     * 描述 : 名称
     */
    private String name;

    /**
     * 描述 : 组别
     */
    private String group = "default";

    /**
     * 描述 : 描述
     */
    private String description = "暂无描述";

    /**
     * 描述 : 开始时间
     */
    private Date startTime;

    /**
     * 描述 : 结束时间
     */
    private Date endTime;

    /**
     * 描述 : 优先级
     */
    private Integer priority = 0;

    /**
     * 描述 : 日历
     */
    private String calendar;

    /**
     * 描述 : 遗漏对应的策略
     */
    private Integer misfireInstruction = Trigger.MISFIRE_INSTRUCTION_SMART_POLICY;

    /**
     * 是否自动实例化
     */
    private Boolean autoInit = false;

    /**
     * 描述 : 数据
     */
    private Map<String, String> dataMap;

}
