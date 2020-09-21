package org.itkk.udf.file.aliyun.oss.api.download;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * DownloadInfo
 */
@ApiModel(description = "文件下载信息")
@Data
public class DownloadInfo {

    /**
     * id
     */
    @ApiModelProperty(value = "下载ID", required = true, dataType = "string")
    private String id;

    /**
     * ossCode
     */
    @ApiModelProperty(value = "oss代码", required = true, dataType = "string")
    private String ossCode;

    /**
     * status
     */
    @ApiModelProperty(value = "下载状态 ( 1 : 待执行 , 2 : 执行中 , 3 : 执行完成 , 4 : 执行错误 )", required = true, dataType = "int")
    private Integer status;

    /**
     * objectKey
     */
    @ApiModelProperty(value = "阿里云OSS的objectKey", required = true, dataType = "string")
    private String objectKey;

    /**
     * processStartDate
     */
    @ApiModelProperty(value = "开始时间", required = true, dataType = "date")
    private Date processStartDate;

    /**
     * processEndDate
     */
    @ApiModelProperty(value = "结束时间", required = true, dataType = "date")
    private Date processEndDate;

    /**
     * errorMsg
     */
    @ApiModelProperty(value = "错误信息", required = true, dataType = "string")
    private String errorMsg;
}
