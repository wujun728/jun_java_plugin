/**
 * FileInfo.java
 * Created at 2017-05-27
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.core.domain.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述 : FileInfo
 *
 * @author Administrator
 */
@ApiModel(description = "文件信息")
@Data
public class FileInfo implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 文件ID
     */
    @ApiModelProperty(value = "文件ID(相对路径的base64编码)", required = true, dataType = "string")
    private String id;

    /**
     * 描述 : 文件名称
     */
    @ApiModelProperty(value = "文件名称", required = true, dataType = "string")
    private String name;

    /**
     * 描述 : 文件相对路径
     */
    @ApiModelProperty(value = "文件相对路径", required = true, dataType = "string")
    private String relativePath;

    /**
     * 描述 : 上下文类型
     */
    @ApiModelProperty(value = "上下文类型", required = true, dataType = "string")
    private String contentType;

    /**
     * 描述 : 文件长度
     */
    @ApiModelProperty(value = "文件长度", required = true, dataType = "long")
    private Long size;

}
