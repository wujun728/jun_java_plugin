package org.itkk.udf.core.domain.id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Id
 */
@Data
@ToString
@ApiModel(description = "分布式ID信息")
public class Id implements Serializable {
    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 机器ID( 0 - 31 )
     */
    @ApiModelProperty(value = "机器ID( 0 - 31 )", required = true, dataType = "long")
    private long workerId;

    /**
     * 数据中心ID( 0 - 31 )
     */
    @ApiModelProperty(value = "数据中心ID( 0 - 31 )", required = true, dataType = "long")
    private long datacenterId;

    /**
     * 数据中心ID | 机器ID ( 0 - 1023 )
     */
    @ApiModelProperty(value = "数据中心ID | 机器ID ( 0 - 4095 )", required = true, dataType = "long")
    private long dwId;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳", required = true, dataType = "long")
    private long timestamp;

    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号", required = true, dataType = "long")
    private long sequence;
}
