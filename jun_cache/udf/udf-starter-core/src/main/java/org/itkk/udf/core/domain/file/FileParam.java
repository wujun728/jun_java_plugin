/**
 * FileParam.java
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
 * 描述 : FileParam
 *
 * @author Administrator
 */
@ApiModel(description = "文件参数")
@Data
public class FileParam implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 文件ID
     */
    @ApiModelProperty(value = "文件ID(相对路径的base64编码)", required = true, dataType = "string")
    private String id;

}
