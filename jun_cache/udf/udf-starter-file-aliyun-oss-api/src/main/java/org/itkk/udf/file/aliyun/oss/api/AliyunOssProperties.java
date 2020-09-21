/**
 * Config.java
 * Created at 2016-11-19
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.file.aliyun.oss.api;

import lombok.Data;
import org.itkk.udf.file.aliyun.oss.api.meta.AliyunOssAccessMeta;
import org.itkk.udf.file.aliyun.oss.api.meta.AliyunOssPathMeta;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述 : AliyunOssProperties
 *
 * @author Administrator
 */
@Component
@ConfigurationProperties(prefix = "org.itkk.file.aliyun.oss.properties")
@Data
public class AliyunOssProperties implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : oss认证清单
     */
    private Map<String, AliyunOssAccessMeta> auth;

    /**
     * 描述 : oss路径清单
     */
    private Map<String, AliyunOssPathMeta> path;


}
