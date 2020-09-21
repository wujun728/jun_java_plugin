/**
 * RmsJobParam.java
 * Created at 2017-06-04
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler.client.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述 : RmsJobParam
 *
 * @author Administrator
 */
@ApiModel(description = "通用job参数")
@Data
@ToString
public class RmsJobParam implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 主键ID
     */
    @ApiModelProperty(value = "主键ID", required = true, dataType = "string")
    private String id;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID", required = false, dataType = "string")
    private String parentId;

    /**
     * 描述 : 触发实例ID
     */
    @ApiModelProperty(value = "触发实例ID", required = true, dataType = "string")
    private String fireInstanceId;

    /**
     * 描述 : 触发方式
     */
    @ApiModelProperty(value = "触发方式", required = true, dataType = "string")
    private String triggerType;

    /**
     * 描述 : 服务代码
     */
    @ApiModelProperty(value = "服务代码", required = true, dataType = "string")
    private String serviceCode;

    /**
     * 描述 : bean名称
     */
    @ApiModelProperty(value = "bean名称", required = true, dataType = "string")
    private String beanName;

    /**
     * 描述 : 是否异步
     */
    @ApiModelProperty(value = "是否异步", required = true, dataType = "boolean")
    private Boolean async;

    /**
     * 描述 : 作业参数
     */
    @ApiModelProperty(value = "作业参数", required = true, dataType = "object")
    private Map<String, Object> jobDataMap; //NOSONAR

}
