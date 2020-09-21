package org.itkk.udf.core.domain.mail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述 : AttachmentInfo
 *
 * @author Administrator
 */
@ApiModel(description = "附件信息")
@Data
public class AttachmentInfo implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 附件名称
     */
    @ApiModelProperty(value = "附件名称", required = true, dataType = "string")
    private String name;

    /**
     * 描述 : 附件文件ID
     */
    @ApiModelProperty(value = "附件文件ID", required = true, dataType = "string")
    private String fileId;

}
