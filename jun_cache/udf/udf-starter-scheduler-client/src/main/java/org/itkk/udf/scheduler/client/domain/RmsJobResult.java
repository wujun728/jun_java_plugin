/**
 * RmsJobResult.java
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
import java.util.Date;

/**
 * 描述 : RmsJobResult
 *
 * @author Administrator
 */
@ApiModel(description = "通用job返回值")
@Data
@ToString
public class RmsJobResult implements Serializable {

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
     * 描述 : 客户端接收时间
     */
    @ApiModelProperty(value = "客户端接收时间", required = true, dataType = "long")
    private Date clientReceiveTime;

    /**
     * 描述 : 客户端开始时间
     */
    @ApiModelProperty(value = "客户端开始时间", required = true, dataType = "long")
    private Date clientStartExecuteTime;

    /**
     * 描述 : 客户端结束时间
     */
    @ApiModelProperty(value = "客户端结束时间", required = false, dataType = "long")
    private Date clientEndExecuteTime;

    /**
     * 描述 : 状态
     */
    @ApiModelProperty(value = "状态", required = true, dataType = "int")
    private Integer stats;

    /**
     * 描述 : 错误信息
     */
    @ApiModelProperty(value = "错误信息", required = true, dataType = "string")
    private String errorMsg;

    /**
     * 描述 : param
     */
    @ApiModelProperty(value = "参数", required = true, dataType = "string")
    private RmsJobParam param;

}
