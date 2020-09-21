/**
 * Config.java
 * Created at 2016-11-19
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.file.aliyun.oss.api.meta;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述 : AliyunOssProperties
 *
 * @author Administrator
 */
@Data
public class AliyunOssPathMeta implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * bucketName
     */
    private String bucketName;

    /**
     * ossHost
     */
    private String ossHost;

    /**
     * ossInternalHost
     */
    private String ossInternalHost;

    /**
     * cdnHost
     */
    private String cdnHost;

}
