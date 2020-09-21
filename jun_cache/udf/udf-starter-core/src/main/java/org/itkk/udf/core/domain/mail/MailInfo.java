/**
 * MailInfo.java
 * Created at 2017-05-28
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.core.domain.mail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 描述 : MailInfo
 *
 * @author Administrator
 */
@ApiModel(description = "邮件信息")
@Data
public class MailInfo implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 接收地址
     */
    @ApiModelProperty(value = "接收地址", required = true, dataType = "string")
    @NotEmpty(message = "接收地址不能为空")
    private String[] to;

    /**
     * 描述 : 主题
     */
    @ApiModelProperty(value = "主题", required = true, dataType = "string")
    @NotEmpty(message = "主题不能为空")
    private String subject;

    /**
     * 描述 : 正文
     */
    @ApiModelProperty(value = "正文", required = true, dataType = "string")
    @NotEmpty(message = "正文不能为空")
    private String text;

    /**
     * 描述 : 是否html正文
     */
    @ApiModelProperty(value = "是否html正文(默认false)", required = true, dataType = "boolean")
    private Boolean isHtmlText = false;

    /**
     * 描述 : 保密抄送地址
     */
    @ApiModelProperty(value = "保密抄送地址", required = false, dataType = "string")
    private String[] bcc;

    /**
     * 描述 : 抄送地址
     */
    @ApiModelProperty(value = "抄送地址", required = false, dataType = "string")
    private String[] cc;

    /**
     * 描述 : 回复地址
     */
    @ApiModelProperty(value = "回复地址", required = false, dataType = "string")
    private String replyTo;

    /**
     * 描述 : 优先级
     */
    @ApiModelProperty(value = "优先级", required = false, dataType = "int")
    private Integer priority;

    /**
     * 描述 : 附件列表
     */
    @ApiModelProperty(value = "附件列表", required = false, dataType = "object")
    private List<AttachmentInfo> attachments;

    /**
     * 描述 : 静态资源列表
     */
    @ApiModelProperty(value = "静态资源列表", required = false, dataType = "object")
    private List<InlineInfo> inlines;

}
