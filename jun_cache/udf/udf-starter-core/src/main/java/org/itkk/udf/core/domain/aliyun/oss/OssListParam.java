package org.itkk.udf.core.domain.aliyun.oss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * OssListParam
 */
@ApiModel(description = "阿里云oss对象信息")
@Data
public class OssListParam implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * code
     */
    @ApiModelProperty(value = "阿里云oss的配置代码", required = true, dataType = "string")
    @NotBlank(message = "阿里云oss的配置代码不能为空")
    private String code;

    /**
     * keyPrefix
     */
    @ApiModelProperty(value = "阿里云oss的的对象key前缀", required = true, dataType = "string")
    @NotBlank(message = "阿里云oss的的对象key前缀不能为空")
    private String keyPrefix;
}
