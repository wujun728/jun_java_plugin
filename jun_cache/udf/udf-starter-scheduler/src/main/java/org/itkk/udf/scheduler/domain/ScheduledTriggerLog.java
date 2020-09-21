/**
 * TriggerDetailListener.java
 * Created at 2017-06-01
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述 : ScheduledTriggerLog
 *
 * @author Administrator
 */
@ApiModel(description = "计划任务触发日志")
@Data
@ToString
public class ScheduledTriggerLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 日志ID
     */
    @ApiModelProperty(value = "日志ID", required = true, dataType = "string")
    private String logid;

    /**
     * 描述 : 计划执行时间
     */
    @ApiModelProperty(value = "计划执行时间", required = true, dataType = "date")
    private Date scheduledFireTime;

    /**
     * 描述 : 实际执行时间
     */
    @ApiModelProperty(value = "实际执行时间", required = true, dataType = "date")
    private Date fireTime;

    /**
     * 描述 : 实际结束时间
     */
    @ApiModelProperty(value = "实际结束时间", required = true, dataType = "date")
    private Date endTime;

    /**
     * 描述 : 执行时长
     */
    @ApiModelProperty(value = "执行时长", required = true, dataType = "long")
    private Long jobRunTime;

    /**
     * 描述 : 状态
     */
    @ApiModelProperty(value = "状态", required = true, dataType = "String")
    private String status;

    /**
     * 描述 : 结果
     */
    @ApiModelProperty(value = "结果", required = true, dataType = "String")
    private String result;

    /**
     * 描述 : 错误信息
     */
    @ApiModelProperty(value = "错误信息", required = true, dataType = "String")
    private String errorMsg;

    /**
     * 描述 : 触发器名称
     */
    @ApiModelProperty(value = "触发器名称", required = true, dataType = "String")
    private String triggerName;

    /**
     * 描述 : 触发器组别
     */
    @ApiModelProperty(value = "触发器组别", required = true, dataType = "String")
    private String triggerGroup;

    /**
     * 描述 : 作业名称
     */
    @ApiModelProperty(value = "作业名称", required = true, dataType = "String")
    private String jobName;

    /**
     * 描述 : 作业组别
     */
    @ApiModelProperty(value = "作业组别", required = true, dataType = "String")
    private String jobGroup;

    /**
     * 描述 : 作业类型
     */
    @ApiModelProperty(value = "作业类型", required = true, dataType = "String")
    private String jobClass;

    /**
     * 描述 : 线程组名称
     */
    @ApiModelProperty(value = "线程组名称", required = true, dataType = "String")
    private String threadGroupName;

    /**
     * 描述 : 线程ID
     */
    @ApiModelProperty(value = "线程ID", required = true, dataType = "String")
    private String threadId;

    /**
     * 描述 : 线程名称
     */
    @ApiModelProperty(value = "线程名称", required = true, dataType = "String")
    private String threadName;

    /**
     * 描述 : 线程优先级
     */
    @ApiModelProperty(value = "线程优先级", required = true, dataType = "String")
    private String threadPriority;

    /**
     * 描述 : 计划任务ID
     */
    @ApiModelProperty(value = "计划任务ID", required = true, dataType = "String")
    private String scheduledId;

    /**
     * 描述 : 计划任务名称
     */
    @ApiModelProperty(value = "计划任务名称", required = true, dataType = "String")
    private String scheduledName;

    /**
     * 描述 : 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true, dataType = "date")
    private Date createDate;

}
