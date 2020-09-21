/**
 * Message.java
 * Created at 2016-09-19
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.file.aliyun.oss.api.download;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 描述 : 下载消息
 *
 * @author wangkang
 */
@Data
@ToString
public class DownLoadParam<T> implements Serializable {

    /**
     * 描述 : id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 消息ID
     */
    private String id;

    /**
     * ossCode
     */
    private String ossCode;

    /**
     * 描述 : 消息体
     */
    private T body; //NOSONAR

}
